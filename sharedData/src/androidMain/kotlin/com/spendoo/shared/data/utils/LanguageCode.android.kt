package com.spendoo.shared.data.utils

import java.util.Locale

actual val languageCode: String
    get() = Locale.getDefault().language
