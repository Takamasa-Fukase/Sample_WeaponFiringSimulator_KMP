package org.example.project.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class IosMainScope {
    companion object {
        fun createIosMainScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }
}