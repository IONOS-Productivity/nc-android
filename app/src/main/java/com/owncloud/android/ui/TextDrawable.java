/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-FileCopyrightText: 2019-2021 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-FileCopyrightText: 2015-2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2016-2018 Andy Scherzinger <info@andy-scherzinger.de>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.owncloud.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import com.ionos.annotation.IonosCustomization;
import com.nextcloud.client.account.User;
import com.nextcloud.client.account.UserAccountManager;
import com.owncloud.android.R;

import java.util.Locale;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

/**
 * A Drawable object that draws text (1 character) on top of a circular/filled background.
 */
@IonosCustomization("Custom colors and text style")
public class TextDrawable extends Drawable {
    /**
     * the text to be rendered.
     */
    private String text;

    /**
     * the text paint to be rendered.
     */
    private Paint textPaint;

    /**
     * the background to be rendered.
     */
    private Paint background;

    /**
     * the radius of the circular background to be rendered.
     */
    private float radius;

    private boolean bigText = false;

    /**
     * Create a TextDrawable with the given radius.
     *
     * @param text   the text to be rendered
     * @param backgroundColor circle background color
     * @param textColor text color
     * @param radius circle radius
     */
    public TextDrawable(String text, @ColorInt int backgroundColor, @ColorInt int textColor, float radius) {
        this.radius = radius;
        this.text = text;

        background = new Paint();
        background.setStyle(Paint.Style.FILL);
        background.setAntiAlias(true);
        background.setColor(backgroundColor);

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(radius);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);

        setBounds(0, 0, (int) radius * 2, (int) radius * 2);
    }

    /**
     * creates an avatar in form of a TextDrawable with the first letter of the account name in a circle with the given
     * radius.
     *
     * @param user        user account
     * @param radiusInDp  the circle's radius
     * @return the avatar as a TextDrawable
     */
    @NonNull
    public static TextDrawable createAvatar(Context context, User user, float radiusInDp) {
        String username = UserAccountManager.getDisplayName(user);
        return createNamedAvatar(context, username, radiusInDp);
    }

    /**
     * creates an avatar in form of a TextDrawable with the first letter of the account name in a circle with the given
     * radius.
     *
     * @param userId     userId to use
     * @param radiusInDp the circle's radius
     * @return the avatar as a TextDrawable
     */
    @NonNull
    public static TextDrawable createAvatarByUserId(Context context, String userId, float radiusInDp) {
        return createNamedAvatar(context, userId, radiusInDp);
    }

    /**
     * creates an avatar in form of a TextDrawable with the first letter of a name in a circle with the
     * given radius.
     *
     * @param name       the name
     * @param radiusInDp the circle's radius
     * @return the avatar as a TextDrawable
     */
    @NonNull
    public static TextDrawable createNamedAvatar(Context context, String name, float radiusInDp) {
        int backgroundColor = context.getColor(R.color.avatar_background_color);
        int textColor = context.getColor(R.color.avatar_text_color);
        return new TextDrawable(extractCharsFromDisplayName(name), backgroundColor, textColor, radiusInDp);
    }

    @VisibleForTesting
    public static String extractCharsFromDisplayName(@NonNull final String displayName) {
        final String trimmed = displayName.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        String[] nameParts = trimmed.split("\\s+");

        StringBuilder firstTwoLetters = new StringBuilder();
        for (int i = 0; i < Math.min(2, nameParts.length); i++) {
            firstTwoLetters.append(nameParts[i].substring(0, 1).toUpperCase(Locale.getDefault()));
        }

        return firstTwoLetters.toString();
    }

    /**
     * Draw in its bounds (set via setBounds) respecting optional effects such as alpha (set via setAlpha) and color
     * filter (set via setColorFilter) a circular background with a user's first character.
     *
     * @param canvas The canvas to draw into
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(radius, radius, radius, background);

        if (bigText) {
            textPaint.setTextSize(1.8f * radius);
        }

        canvas.drawText(text, radius, radius - ((textPaint.descent() + textPaint.ascent()) / 2), textPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        textPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        textPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
