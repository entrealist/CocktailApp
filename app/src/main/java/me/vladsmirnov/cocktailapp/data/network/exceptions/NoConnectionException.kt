package me.vladsmirnov.cocktailapp.data.network.exceptions

import java.io.IOException

class NoConnectionException : IOException() {

    override val message: String?
        get() = "No connection"
}