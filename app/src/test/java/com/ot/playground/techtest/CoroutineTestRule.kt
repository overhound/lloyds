package com.ot.playground.techtest

import com.ot.playground.techtest.utils.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

@ExperimentalCoroutinesApi
class CoroutineTestRule(
    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : BeforeAllCallback, AfterAllCallback, TestCoroutineScope by TestCoroutineScope(dispatcher) {

    val testDispatcherProvider = object : DispatcherProvider {
        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
        override fun computation(): CoroutineDispatcher = dispatcher
    }

    fun runBlockingTest(block: () -> Unit) {
        dispatcher.runBlockingTest {
            block()
        }
    }

    override fun beforeAll(context: ExtensionContext?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun afterAll(context: ExtensionContext?) {
        Dispatchers.resetMain()
        cleanupTestCoroutines()
    }
}