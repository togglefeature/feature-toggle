package ru.ezhov.featuretoggles.conditionengine.infrastructure

import arrow.core.Either
import mu.KotlinLogging
import org.codehaus.janino.ExpressionEvaluator
import org.springframework.stereotype.Component
import ru.ezhov.featuretoggles.conditionengine.domain.ConditionEngine
import ru.ezhov.featuretoggles.conditionengine.domain.ConditionEngineException
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineDescription
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineLanguage
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineParameters
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineType

private val logger = KotlinLogging.logger {}

@Component
class JaninoExpressionConditionEngine : ConditionEngine {
    override fun type(): ConditionEngineType = ConditionEngineType.EXPRESSION

    override fun language(): ConditionEngineLanguage = ConditionEngineLanguage.JAVA

    override fun description(): ConditionEngineDescription = ConditionEngineDescription("Тестовое описание выражения")

    override fun isConditionFulfilled(parameters: ConditionEngineParameters): Either<ConditionEngineException, Boolean> {
        val start = System.currentTimeMillis()
        // Now here's where the story begins...
        val ee = ExpressionEvaluator()
        // The expression will have two "int" parameters: "a" and "b".

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

        ee.setParameters(
                namesParameters,
                types
        )
        // And the expression (i.e. "result") type is also "boolean".
        ee.setExpressionType(Boolean::class.java)
        // And now we "cook" (scan, parse, compile and load) the fabulous expression.
        parameters.condition?.body?.let { body ->
            ee.cook(body)
        }

        // Eventually we evaluate the expression - and that goes super-fast.
        val values = parameters.inputParameters?.parameters?.map { it.value }.orEmpty().toTypedArray()
        val result = ee.evaluate(*values) as Boolean

        val end = System.currentTimeMillis()

        logger.debug {
            "Condition with parameters $parameters was executed in ${end - start} ms"
        }

        return Either.Right(result)
    }
}