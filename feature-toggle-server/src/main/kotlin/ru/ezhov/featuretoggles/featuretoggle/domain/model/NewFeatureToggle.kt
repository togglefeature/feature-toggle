package ru.ezhov.featuretoggles.featuretoggle.domain.model

import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineConfiguration
import java.time.LocalDateTime
import java.util.UUID

class NewFeatureToggle(
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val enabled: Boolean,
        val startDate: LocalDateTime?,
        val endDate: LocalDateTime?,
        val description: FeatureToggleDescription,
        val type: FeatureToggleType,
        val condition: ConditionEngineConfiguration?,
)