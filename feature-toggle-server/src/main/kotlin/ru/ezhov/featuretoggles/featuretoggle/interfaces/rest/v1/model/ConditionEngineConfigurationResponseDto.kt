package ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model

class ConditionEngineConfigurationResponseDto(
        val type: ConditionEngineTypeResponseDto,
        val language: ConditionEngineLanguageResponseDto,
        val parameters: InputConditionParametersConfigurationResponseDto?,
        val condition: ConditionConfigurationResponseDto?,
)