/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.license.oath.security.algorithm;

import java.security.spec.AlgorithmParameterSpec;


public interface AlgorithmParameterSpecFactory {
	AlgorithmParameterSpec create(byte[] initializationVector);
}
