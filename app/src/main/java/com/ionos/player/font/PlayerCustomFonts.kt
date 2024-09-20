package com.ionos.player.font

import android.graphics.Typeface
import com.strato.hidrive.stylized_view.ICustomFonts

class PlayerCustomFonts : ICustomFonts {

    override fun getBoldTypeface(): Typeface {
        return Typeface.DEFAULT_BOLD
    }

    override fun getRegularTypeface(): Typeface {
        return Typeface.DEFAULT
    }

    override fun getLightTypeface(): Typeface {
        return Typeface.DEFAULT
    }

    override fun getMediumTypeface(): Typeface {
        return Typeface.DEFAULT
    }

    override fun getMainBoldFont(): Typeface {
        return Typeface.DEFAULT_BOLD
    }

    override fun getMainRegularFont(): Typeface {
        return Typeface.DEFAULT
    }

}