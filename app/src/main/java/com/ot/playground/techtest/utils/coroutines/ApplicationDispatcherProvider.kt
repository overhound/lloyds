package com.ot.playground.techtest.utils.coroutines

import kotlinx.coroutines.Dispatchers

class ApplicationDispatcherProvider : DispatcherProvider {
    override fun io() = Dispatchers.IO

    override fun ui() = Dispatchers.Main

    override fun computation() = Dispatchers.Default
}