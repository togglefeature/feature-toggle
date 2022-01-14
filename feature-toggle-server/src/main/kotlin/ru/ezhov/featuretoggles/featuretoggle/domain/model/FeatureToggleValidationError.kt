package ru.ezhov.featuretoggles.featuretoggle.domain.model

sealed class FeatureToggleValidationError {
    class Id(val message: String) : FeatureToggleValidationError()
    class New(val message: String) : FeatureToggleValidationError()
}