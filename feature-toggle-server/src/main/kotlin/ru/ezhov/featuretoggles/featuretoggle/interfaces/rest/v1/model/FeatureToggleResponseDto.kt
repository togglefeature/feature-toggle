package ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model

import java.time.LocalDateTime

class FeatureToggleResponseDto(
        val id: String,
        val name: String,
        val enabled: Boolean,
        val description: String,
        val startDate: LocalDateTime?,
        val endDate: LocalDateTime?,
        val type: FeatureToggleTypeResponseDto,
        val condition: ConditionEngineConfigurationResponseDto?,
)