/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2026 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */
package com.owncloud.android.authentication;

import android.content.Context;

import com.ionos.branding.BrandConfigProvider;
import com.ionos.branding.LoginUrls;

import androidx.annotation.NonNull;
import androidx.core.os.ConfigurationCompat;

import java.util.Locale;

/**
 * Resolves the web login URL for the current device locale from the brand configuration
 * (see {@link com.ionos.branding.BrandConfig}).
 */
public final class IonosLoginDomainResolver {

    private IonosLoginDomainResolver() {
        // Utility class.
    }

    @NonNull
    public static String resolveLoginV2Url(Context context, @NonNull BrandConfigProvider brandConfigProvider) {
        return resolveLoginV2Url(getDeviceLocale(context), brandConfigProvider.getConfig().getLoginUrls());
    }

    @NonNull
    public static String resolveLoginV2Url(@NonNull Locale locale, @NonNull LoginUrls loginUrls) {
        String country = locale.getCountry().toUpperCase(Locale.ROOT);
        String byCountry = loginUrls.getByCountry().get(country);
        if (byCountry != null) {
            return byCountry;
        }

        String language = locale.getLanguage().toLowerCase(Locale.ROOT);
        String byLanguage = loginUrls.getByLanguage().get(language);
        return byLanguage != null ? byLanguage : loginUrls.getDefault();
    }

    private static Locale getDeviceLocale(Context context) {
        Locale locale = ConfigurationCompat.getLocales(context.getResources().getConfiguration()).get(0);
        return locale != null ? locale : Locale.getDefault();
    }
}
