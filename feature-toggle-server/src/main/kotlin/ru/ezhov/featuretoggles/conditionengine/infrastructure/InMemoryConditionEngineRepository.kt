package ru.ezhov.featuretoggles.conditionengine.infrastructure

import arrow.core.Either
import org.springframework.stereotype.Repository
import ru.ezhov.featuretoggles.conditionengine.domain.ConditionEngine
import ru.ezhov.featuretoggles.conditionengine.domain.ConditionEngineRepository
import ru.ezhov.featuretoggles.conditionengine.domain.ConditionEngineRepositoryException
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineLanguage
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineType

@Repository
class InMemoryConditionEngineRepository(
        private val engines: List<ConditionEngine>
) : ConditionEngineRepository {
    override fun all(): Either<ConditionEngineRepositoryException, List<ConditionEngine>> = Either.Right(engines)

    override fun by(type: ConditionEngineType, language: ConditionEngineLanguage):
            Either<ConditionEngineRepositoryException, ConditionEngine?> =
            Either.Right(engines.firstOrNull { it.type() == type && it.language() == language })
}