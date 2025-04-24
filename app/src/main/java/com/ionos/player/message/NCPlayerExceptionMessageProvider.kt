package com.ionos.player.message

import com.ionos.player.player.interfaces.PlayerExceptionMessageProvider
import javax.inject.Inject

class NCPlayerExceptionMessageProvider @Inject constructor(): PlayerExceptionMessageProvider {

    override fun sourceNotFoundExceptionMessage(): String {
        TODO("Not yet implemented")
    }

    override fun unknownExceptionMessage(): String {
        TODO("Not yet implemented")
    }
}