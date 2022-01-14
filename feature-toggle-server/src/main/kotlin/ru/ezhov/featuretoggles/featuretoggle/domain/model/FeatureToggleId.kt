package ru.ezhov.featuretoggles.featuretoggle.domain.model

import arrow.core.Either
import java.util.*

@JvmInline
value class FeatureToggleId(val value: String) {
    companion object {
        fun of(value: String): Either<FeatureToggleValidationError.Id, FeatureToggleId> =
            if (value.isBlank()) {
                Either.Left(FeatureToggleValidationError.Id("ID feature toggle mast not be empty"))
            } else {
                Either.Right(FeatureToggleId(value))
            }

        fun create(): FeatureToggleId = FeatureToggleId(value = UUID.randomUUID().toString())
    }
}