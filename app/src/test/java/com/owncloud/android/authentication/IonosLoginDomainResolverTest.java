/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2026 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */
package com.owncloud.android.authentication;

import com.ionos.branding.BrandConfig;
import com.ionos.branding.BrandConfigProvider;
import com.ionos.branding.LoginUrls;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;

/**
 * Covers the locale resolution algorithm and the brand configuration files it is fed with, so that a
 * malformed or incomplete config injected by the Brander tool fails the build instead of the app.
 */
public class IonosLoginDomainResolverTest {

    private static final String IONOS_CONFIG = "src/brandIonos/assets/branding/config.json";
    private static final String STRATO_CONFIG = "src/brandStrato/assets/branding/config.json";

    private static LoginUrls loginUrlsOf(String path) throws IOException {
        String raw = new String(Files.readAllBytes(new File(path).toPath()), StandardCharsets.UTF_8);
        BrandConfig config = BrandConfigProvider.Companion.parse(raw);
        return config.getLoginUrls();
    }

    @Test
    public void resolveByCountryForSupportedMarkets() throws IOException {
        LoginUrls urls = loginUrlsOf(IONOS_CONFIG);

        Assert.assertEquals(
            "https://storage.ionos.it/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("it-IT"), urls)
        );
        Assert.assertEquals(
            "https://storage.ionos.de/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("de-DE"), urls)
        );
        Assert.assertEquals(
            "https://storage.ionos.fr/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("fr-FR"), urls)
        );
        Assert.assertEquals(
            "https://storage.ionos.es/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("es-ES"), urls)
        );
        Assert.assertEquals(
            "https://storage.ionos.co.uk/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.UK, urls)
        );
    }

    @Test
    public void resolveByCountryForNorthAmericanMarkets() throws IOException {
        LoginUrls urls = loginUrlsOf(IONOS_CONFIG);

        for (String tag : new String[]{"en-US", "es-US"}) {
            Assert.assertEquals(
                "https://storage.ionos.com/index.php/login/v2",
                IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag(tag), urls)
            );
        }
        for (String tag : new String[]{"en-CA", "fr-CA"}) {
            Assert.assertEquals(
                "https://storage.ionos.ca/index.php/login/v2",
                IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag(tag), urls)
            );
        }
        Assert.assertEquals(
            "https://storage.ionos.mx/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("es-MX"), urls)
        );
    }

    @Test
    public void countryTakesPrecedenceOverLanguage() throws IOException {
        LoginUrls urls = loginUrlsOf(IONOS_CONFIG);

        Assert.assertEquals(
            "https://storage.ionos.de/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("en-DE"), urls)
        );
    }

    @Test
    public void resolveByLanguageWhenCountryMissing() throws IOException {
        LoginUrls urls = loginUrlsOf(IONOS_CONFIG);

        Assert.assertEquals(
            "https://storage.ionos.de/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.GERMAN, urls)
        );
        Assert.assertEquals(
            "https://storage.ionos.co.uk/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.ENGLISH, urls)
        );
    }

    @Test
    public void fallbackForUnsupportedLocale() throws IOException {
        LoginUrls urls = loginUrlsOf(IONOS_CONFIG);

        Assert.assertEquals(
            "https://storage.ionos.co.uk/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("pl-PL"), urls)
        );
        Assert.assertEquals(
            "https://storage.ionos.co.uk/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("ja-JP"), urls)
        );
    }

    @Test
    public void stratoConfigIsResolvedThroughEveryLookupStep() throws IOException {
        LoginUrls urls = loginUrlsOf(STRATO_CONFIG);

        Assert.assertEquals(
            urls.getByCountry().get("DE"),
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("de-DE"), urls)
        );
        Assert.assertEquals(
            urls.getByLanguage().get("de"),
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.GERMAN, urls)
        );
        Assert.assertEquals(
            urls.getDefault(),
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("ja-JP"), urls)
        );
    }

    /** Both brand files have to stay structurally in sync, they are maintained separately. */
    @Test
    public void everyBrandConfigCoversAllSupportedLanguages() throws IOException {
        for (String path : new String[]{IONOS_CONFIG, STRATO_CONFIG}) {
            LoginUrls urls = loginUrlsOf(path);

            Assert.assertFalse(path + " has no default login url", urls.getDefault().isEmpty());
            for (String language : new String[]{"de", "en", "es", "fr", "it"}) {
                Assert.assertTrue(
                    path + " has no login url for language " + language,
                    urls.getByLanguage().containsKey(language)
                );
            }
        }
    }
}
