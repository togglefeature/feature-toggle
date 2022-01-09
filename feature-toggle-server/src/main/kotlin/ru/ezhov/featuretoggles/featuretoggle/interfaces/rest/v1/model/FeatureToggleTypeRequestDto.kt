package ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model

enum class FeatureToggleTypeRequestDto {
    RELEASE,
    EXPERIMENT,
    OPERATIONAL,
    PERMISSION,
}