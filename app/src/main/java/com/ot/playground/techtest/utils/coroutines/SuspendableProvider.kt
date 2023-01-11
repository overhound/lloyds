package com.ot.playground.techtest.utils.coroutines

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

interface SuspendableProvider<T> {
    suspend fun get(): T
    val isCompleted: Boolean
}

fun <T> suspendableLazy(provider: suspend () -> T) = object : SuspendableProvider<T> {
    private val computed = GlobalScope.async(start = CoroutineStart.LAZY) { provider() }
    override val isCompleted: Boolean
        get() = computed.isCompleted

    override suspend fun get() = computed.await()
}