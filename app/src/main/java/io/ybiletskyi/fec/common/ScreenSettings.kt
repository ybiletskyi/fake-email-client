package io.ybiletskyi.fec.common

sealed class ScreenSettings {
    object Default: ScreenSettings()
    object Details: ScreenSettings()
}