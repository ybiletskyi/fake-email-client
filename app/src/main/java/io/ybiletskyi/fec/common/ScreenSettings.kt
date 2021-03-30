package io.ybiletskyi.fec.common

sealed class ScreenSettings {
    data class Default(val title: String): ScreenSettings()
    object Details: ScreenSettings()
}