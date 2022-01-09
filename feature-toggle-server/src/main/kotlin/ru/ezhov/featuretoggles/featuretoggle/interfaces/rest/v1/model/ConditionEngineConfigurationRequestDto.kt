package ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model

class ConditionEngineConfigurationRequestDto(
        val type: ConditionEngineTypeRequestDto,
        val language: ConditionEngineLanguageRequestDto,
        val parameters: InputConditionParametersConfigurationRequestDto?,
        val condition: ConditionConfigurationRequestDto?,
)