package ru.ezhov.featuretoggles.featuretoggle.domain.model

import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineConfiguration
import java.time.LocalDateTime

class FeatureToggle private constructor(
    val id: FeatureToggleId,
    val name: String,
    val enabled: Boolean,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val description: FeatureToggleDescription,
    val type: FeatureToggleType,
    val condition: ConditionEngineConfiguration?,
) {
    companion object {
        fun from(new: NewFeatureToggle) = FeatureToggle(
            id = new.id,
            name = new.name,
            enabled = new.enabled,
            startDate = new.startDate,
            endDate = new.endDate,
            description = new.description,
            type = new.type,
            condition = new.condition,
        )
    }

    fun update(info: FeatureToggleInfo): FeatureToggle = FeatureToggle(
        id = this.id,
        name = info.name,
        enabled = info.enabled,
        startDate = info.startDate,
        endDate = info.endDate,
        description = info.description,
        type = info.type,
        condition = info.condition,
    )

    fun changeState() =
        FeatureToggle(
            id = this.id,
            name = this.name,
            enabled = !this.enabled,
            startDate = this.startDate,
            endDate = this.endDate,
            description = this.description,
            type = this.type,
            condition = this.condition,
        )

    fun isActive() = enabled && (endDate == null || endDate.isAfter(LocalDateTime.now()))
}