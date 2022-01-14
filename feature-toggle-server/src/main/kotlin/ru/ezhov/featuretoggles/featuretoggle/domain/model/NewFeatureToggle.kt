package ru.ezhov.featuretoggles.featuretoggle.domain.model

import arrow.core.Either
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineConfiguration
import java.time.LocalDateTime

@Suppress("DataClassPrivateConstructor")
data class NewFeatureToggle private constructor(
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
        fun of(
            name: String,
            enabled: Boolean,
            startDate: LocalDateTime? = null,
            endDate: LocalDateTime? = null,
            description: FeatureToggleDescription,
            type: FeatureToggleType,
            condition: ConditionEngineConfiguration? = null
        ): Either<FeatureToggleValidationError.New, NewFeatureToggle> =
            Either.Right(
                NewFeatureToggle(
                    id = FeatureToggleId.create(),
                    name = name,
                    enabled = enabled,
                    startDate = startDate,
                    endDate = endDate,
                    description = description,
                    type = type,
                    condition = condition,
                )
            )
    }
}