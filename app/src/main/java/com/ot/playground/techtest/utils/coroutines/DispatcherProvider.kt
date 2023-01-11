package com.ot.playground.techtest.utils.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
    fun computation(): CoroutineDispatcher
}