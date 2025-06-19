/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.util;

public class Cast {

    public static <T> T castOrError(Object objectToCast, Class<T> implementationInterface) throws ClassCastException {
        return implementationInterface.cast(objectToCast);
    }
}
