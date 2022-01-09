package ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model

data class FeatureToggleTypesResponseDto(
        val types: List<FeatureToggleTypeResponseDto> = FeatureToggleTypeResponseDto.values().toList()
)

enum class FeatureToggleTypeResponseDto {
    RELEASE,
    EXPERIMENT,
    OPERATIONAL,
    PERMISSION,
}