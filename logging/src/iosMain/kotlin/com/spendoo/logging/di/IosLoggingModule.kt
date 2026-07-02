package com.spendoo.logging.di

import com.spendoo.logging.IosCrashLogger
import com.spendoo.logging.CrashLogger
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformLoggingModule: Module = module {
    single<CrashLogger> { IosCrashLogger() }
}
