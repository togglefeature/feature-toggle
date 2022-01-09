package ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model

import java.time.LocalDateTime

class FeatureToggleInfoRequestDto(
        val name: String,
        val enabled: Boolean,
        val startDate: LocalDateTime?,
        val endDate: LocalDateTime?,
        val description: String,
        val type: FeatureToggleTypeRequestDto,
        val condition: ConditionEngineConfigurationRequestDto?,
)