/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayer.interfaces;

import java.io.Serializable;

/**
 * Created by yaz on 1/23/17.
 */

public interface MultiplePlaybackErrorStrategy<SourceInfo, Mode> extends Serializable {
	boolean switchToNextSource(Throwable error, MultiplePlaybackState<SourceInfo, Mode> state);
}
