/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2026 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */
package com.owncloud.android.authentication;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class IonosLoginDomainResolverTest {

    @Test
    public void resolveByCountryForSupportedMarkets() {
        Assert.assertEquals(
            "https://storage.ionos.it/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("it-IT"))
        );
        Assert.assertEquals(
            "https://storage.ionos.de/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("de-DE"))
        );
        Assert.assertEquals(
            "https://storage.ionos.fr/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("fr-FR"))
        );
        Assert.assertEquals(
            "https://storage.ionos.es/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("es-ES"))
        );
        Assert.assertEquals(
            "https://storage.ionos.co.uk/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.UK)
        );
    }

    @Test
    public void resolveByLanguageWhenCountryMissing() {
        Assert.assertEquals(
            "https://storage.ionos.de/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.GERMAN)
        );
        Assert.assertEquals(
            "https://storage.ionos.co.uk/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.ENGLISH)
        );
    }

    @Test
    public void fallbackForUnsupportedLocale() {
        Assert.assertEquals(
            "https://storage.ionos.co.uk/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("pl-PL"))
        );
        Assert.assertEquals(
            "https://storage.ionos.co.uk/index.php/login/v2",
            IonosLoginDomainResolver.resolveLoginV2Url(Locale.forLanguageTag("ja-JP"))
        );
    }
}

