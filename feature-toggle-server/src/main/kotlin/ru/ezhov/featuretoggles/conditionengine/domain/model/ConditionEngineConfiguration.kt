package ru.ezhov.featuretoggles.conditionengine.domain.model

class ConditionEngineConfiguration(
        val type: ConditionEngineType,
        val language: ConditionEngineLanguage,
        val parameters: InputConditionParametersConfiguration?,
        val condition: ConditionConfiguration?,
)