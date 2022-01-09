package ru.ezhov.featuretoggles.conditionengine.infrastructure

import arrow.core.getOrHandle
import org.junit.jupiter.api.Test
import ru.ezhov.featuretoggles.conditionengine.domain.model.Condition
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineParameters
import ru.ezhov.featuretoggles.conditionengine.domain.model.InputConditionParameter
import ru.ezhov.featuretoggles.conditionengine.domain.model.InputConditionParameters

internal class JaninoExpressionConditionEngineTest {
    @Test
    fun test1() {
        for (i in 1..15) {
            val engine = JaninoExpressionConditionEngine()
            val result = engine.isConditionFulfilled(
                    parameters = ConditionEngineParameters(
                            inputParameters = InputConditionParameters(listOf(
                                    InputConditionParameter(
                                            name = "test",
                                            value = "12",
                                    )
                            )),
                            condition = Condition(body = "Integer.valueOf(test) > 12"),
                    )).getOrHandle { ex -> throw ex }
            println(result)
        }
    }
}