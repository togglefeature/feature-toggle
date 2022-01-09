package ru.ezhov.featuretoggles.conditionengine.infrastructure

import arrow.core.Either
import mu.KotlinLogging
import org.codehaus.janino.ScriptEvaluator
import org.springframework.stereotype.Component
import ru.ezhov.featuretoggles.conditionengine.domain.ConditionEngine
import ru.ezhov.featuretoggles.conditionengine.domain.ConditionEngineException
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineDescription
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineLanguage
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineParameters
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineType

private val logger = KotlinLogging.logger {}

@Component
class JaninoScriptConditionEngine : ConditionEngine {
    override fun type(): ConditionEngineType = ConditionEngineType.SCRIPT

    override fun language(): ConditionEngineLanguage = ConditionEngineLanguage.JAVA

    override fun description(): ConditionEngineDescription = ConditionEngineDescription("Тестовое описание скрипта")

    override fun isConditionFulfilled(parameters: ConditionEngineParameters): Either<ConditionEngineException, Boolean> {
        val start = System.currentTimeMillis()
        val namesParameters =
                parameters
                        .inputParameters
                        ?.parameters
                        ?.map { it.name }
                        .orEmpty()
                        .toTypedArray()
        val types = namesParameters
                .map { String::class.java }
                .toTypedArray()
        return parameters.condition?.body?.let { body ->
            val se = ScriptEvaluator(
                    body,
                    Boolean::class.java,
                    namesParameters,
                    types
            )
            val values = parameters.inputParameters?.parameters?.map { it.value }.orEmpty().toTypedArray()
            val result = se.evaluate(values) as Boolean

            val end = System.currentTimeMillis()

            logger.debug {
                "Condition with parameters $parameters was executed in ${end - start} ms"
            }

            return Either.Right(result)
        }
                ?: Either.Left(ConditionEngineException("Body must not be null"))
    }
}