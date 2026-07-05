package com.spendoo.logging

interface CrashLogger {
    fun recordException(throwable: Throwable)
}
