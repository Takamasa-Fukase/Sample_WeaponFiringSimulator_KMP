package org.example.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

fun createMainScope(): CoroutineScope =
    CoroutineScope(SupervisorJob() + Dispatchers.Main)

fun cancel(scope: CoroutineScope?) {
    scope?.cancel()
}