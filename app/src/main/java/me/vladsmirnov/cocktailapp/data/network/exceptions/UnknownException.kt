package me.vladsmirnov.cocktailapp.data.network.exceptions

class UnknownException : Exception() {

    override val message: String?
        get() = "Unknown error"
}