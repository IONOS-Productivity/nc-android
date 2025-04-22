/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.player.interfaces;

/**
 * User: zuzik
 * Date: 6/12/16
 */
public interface ParamAction<T> {
	void execute(T value);
}
