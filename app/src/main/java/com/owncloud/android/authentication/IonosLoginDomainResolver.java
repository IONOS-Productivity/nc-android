/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2026 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */
package com.owncloud.android.authentication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.os.ConfigurationCompat;

import java.util.Locale;
import java.util.Map;

public final class IonosLoginDomainResolver {
    private static final String LOGIN_IT = "https://storage.ionos.it/login/v2";
    private static final String LOGIN_DE = "https://storage.ionos.de/login/v2";
    private static final String LOGIN_FR = "https://storage.ionos.fr/login/v2";
    private static final String LOGIN_ES = "https://storage.ionos.es/login/v2";
    private static final String LOGIN_UK = "https://storage.ionos.co.uk/login/v2";
    private static final String LOGIN_FALLBACK = LOGIN_UK;

    private static final Map<String, String> COUNTRY_TO_LOGIN = Map.of(
        "IT", LOGIN_IT,
        "DE", LOGIN_DE,
        "FR", LOGIN_FR,
        "ES", LOGIN_ES,
        "GB", LOGIN_UK
    );

    private static final Map<String, String> LANGUAGE_TO_LOGIN = Map.of(
        "it", LOGIN_IT,
        "de", LOGIN_DE,
        "fr", LOGIN_FR,
        "es", LOGIN_ES,
        "en", LOGIN_UK
    );

    private IonosLoginDomainResolver() {
        // Utility class.
    }

    @NonNull
    public static String resolveLoginV2Url(Context context) {
        return resolveLoginV2Url(getDeviceLocale(context));
    }

    @NonNull
    public static String resolveLoginV2Url(@NonNull Locale locale) {
        String country = locale.getCountry().toUpperCase(Locale.ROOT);
        String byCountry = COUNTRY_TO_LOGIN.get(country);
        if (byCountry != null) {
            return byCountry;
        }

        String language = locale.getLanguage().toLowerCase(Locale.ROOT);
        String byLanguage = LANGUAGE_TO_LOGIN.get(language);
        return byLanguage != null ? byLanguage : LOGIN_FALLBACK;
    }

    private static Locale getDeviceLocale(Context context) {
        Locale locale = ConfigurationCompat.getLocales(context.getResources().getConfiguration()).get(0);
        return locale != null ? locale : Locale.getDefault();
    }
}

