package ru.ezhov.featuretoggles.featuretoggle.interfaces.rest.v1.model

data class ConditionEngineRequestDto(
        val type: ConditionEngineTypeRequestDto,
        val language: ConditionEngineLanguageRequestDto,
        val description: ConditionEngineDescriptionRequestDto,
)