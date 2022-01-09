package ru.ezhov.featuretoggles.conditionengine.domain

import arrow.core.Either
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineLanguage
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineType

interface ConditionEngineRepository {
    fun all(): Either<ConditionEngineRepositoryException, List<ConditionEngine>>

    fun by(type: ConditionEngineType, language: ConditionEngineLanguage): Either<ConditionEngineRepositoryException, ConditionEngine?>
}