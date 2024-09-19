package com.ionos.player.message

import com.viseven.develop.player.interfaces.PlayerExceptionMessageProvider
import javax.inject.Inject

class NCPlayerExceptionMessageProvider @Inject constructor(): PlayerExceptionMessageProvider {
    override fun playerInitializeExceptionMessage(): String {
        TODO("Not yet implemented")
    }

    override fun failRequestAudioFocusExceptionMessage(): String {
        TODO("Not yet implemented")
    }

    override fun audioFocusLostExceptionMessage(): String {
        TODO("Not yet implemented")
    }

    override fun fakeExceptionMessage(): String {
        TODO("Not yet implemented")
    }

    override fun sourceNotFoundExceptionMessage(): String {
        TODO("Not yet implemented")
    }

    override fun unknownExceptionMessage(): String {
        TODO("Not yet implemented")
    }
}