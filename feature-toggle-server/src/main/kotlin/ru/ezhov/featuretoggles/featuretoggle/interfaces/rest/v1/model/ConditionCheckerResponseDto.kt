package ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model

data class ConditionCheckerResponseDto(
        val result: Boolean,
        val time: String,
)