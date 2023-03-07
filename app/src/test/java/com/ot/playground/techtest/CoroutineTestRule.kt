package com.ot.playground.techtest

import com.ot.playground.techtest.utils.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.rules.TestWatcher
import org.koin.core.context.stopKoin

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTestRule(
    val dispatcher: TestDispatcher = StandardTestDispatcher(TestCoroutineScheduler())
) : TestWatcher(), BeforeAllCallback, AfterAllCallback {
    private val testScope = TestScope(dispatcher)
    val testDispatcherProvider = object : DispatcherProvider {
        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
        override fun default(): CoroutineDispatcher = dispatcher
    }

    fun runBlockingTest(block: () -> Unit) {
        testScope.runTest {
            block()
        }
    }

    fun coRunBlockingTest(block: suspend () -> Unit) {
        testScope.runTest(dispatchTimeoutMs = 3000) {
            block()
        }
    }

    override fun beforeAll(context: ExtensionContext?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun afterAll(context: ExtensionContext?) {
        Dispatchers.resetMain()
        dispatcher.scheduler.cancel()
        stopKoin()
    }
}