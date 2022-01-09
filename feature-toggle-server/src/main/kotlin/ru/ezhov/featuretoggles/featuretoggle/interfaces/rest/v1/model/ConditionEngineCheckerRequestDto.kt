package ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model

class ConditionEngineCheckerRequestDto(
        val type: ConditionEngineTypeRequestDto,
        val language: ConditionEngineLanguageRequestDto,
        val parameters: InputConditionParametersCheckerRequestDto?,
        val condition: ConditionRequestRequestDto?,
)