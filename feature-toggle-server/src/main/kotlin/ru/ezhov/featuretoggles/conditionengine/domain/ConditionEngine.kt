package ru.ezhov.featuretoggles.conditionengine.domain

import arrow.core.Either
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineConfiguration
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineDescription
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineLanguage
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineParameters
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineType

interface ConditionEngine {
    fun type(): ConditionEngineType

    fun language(): ConditionEngineLanguage

    fun description(): ConditionEngineDescription

    fun isConditionFulfilled(parameters: ConditionEngineParameters): Either<ConditionEngineException, Boolean>
}

