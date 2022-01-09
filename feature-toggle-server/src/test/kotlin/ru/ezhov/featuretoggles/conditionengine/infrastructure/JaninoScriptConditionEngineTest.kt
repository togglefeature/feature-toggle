package ru.ezhov.featuretoggles.conditionengine.infrastructure

import arrow.core.getOrHandle
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ru.ezhov.featuretoggles.conditionengine.domain.model.Condition
import ru.ezhov.featuretoggles.conditionengine.domain.model.ConditionEngineParameters
import ru.ezhov.featuretoggles.conditionengine.domain.model.InputConditionParameter
import ru.ezhov.featuretoggles.conditionengine.domain.model.InputConditionParameters

internal class JaninoScriptConditionEngineTest {
    @Test
    @Disabled
    fun test1() {
        for (i in 1..15) {
            val engine = JaninoScriptConditionEngine()
            val result = engine.isConditionFulfilled(
                    parameters = ConditionEngineParameters(
                            inputParameters = InputConditionParameters(listOf(
                                    InputConditionParameter(
                                            name = "test",
                                            value = "12,13,45654,3456",
                                    )
                            )),
                            condition = Condition(body = """
                                import java.util.Arrays;
                                import java.util.List;
                               
                                static boolean calculate(String test){
                                    List<String> list = Arrays.asList(test.split(","));
                                    return list.stream().mapToInt(v -> Integer.valueOf(v)).sum() > 12;
                                }
                                
                                return calculate(test);
                            """.trimIndent()),
                    )).getOrHandle { ex -> throw ex }
            println(result)
        }
    }

    @Test
    fun test2() {
        for (i in 1..15) {
            val engine = JaninoScriptConditionEngine()
            val result = engine.isConditionFulfilled(
                    parameters = ConditionEngineParameters(
                            inputParameters = InputConditionParameters(listOf(
                                    InputConditionParameter(
                                            name = "test",
                                            value = "12,13,45654,3456",
                                    )
                            )),
                            condition = Condition(body = """
                                static Boolean calculate(String test){
                                    System.out.println(test);
                                    return true;
                                }
                                
                                return calculate(test);
                            """.trimIndent()),
                    )).getOrHandle { ex -> throw ex }
            println(result)
        }
    }

    @Test
    @Disabled
    fun test3() {
        for (i in 1..15) {
            val engine = JaninoScriptConditionEngine()
            val result = engine.isConditionFulfilled(
                    parameters = ConditionEngineParameters(
                            inputParameters = InputConditionParameters(listOf(
                                    InputConditionParameter(
                                            name = "test",
                                            value = "12,13,45654,3456",
                                    )
                            )),
                            condition = Condition(body = """
                                import java.util.Arrays;
                                import java.util.List;
                               
                                static boolean calculate(String test){
                                    List<String> list = Arrays.asList(test.split(","));
                                    System.out.println(test);
                                    return list.stream().map((String v) -> {return calculateTrue();}).findFirst().get();
                                }
                                
                                static boolean calculateTrue(){
                                    return true;
                                }
                                
                                return calculate(test);
                            """.trimIndent()),
                    )).getOrHandle { ex -> throw ex }
            println(result)
        }
    }

    @Test
    fun test4() {
        for (i in 1..15) {
            val engine = JaninoScriptConditionEngine()
            val result = engine.isConditionFulfilled(
                    parameters = ConditionEngineParameters(
                            inputParameters = InputConditionParameters(listOf(
                                    InputConditionParameter(
                                            name = "test",
                                            value = "12,13,45654,3456",
                                    ),
                                    InputConditionParameter(
                                            name = "value",
                                            value = "1000000",
                                    )
                            )),
                            condition = Condition(body = """
                                import java.util.Arrays;
                                import java.util.List;
                               
                                static boolean calculate(String test, String value){
                                    List<String> list = Arrays.asList(test.split(","));
                                    int sum = 0;
                                    for (String v : list){
                                        sum += Integer.parseInt(v);
                                    }
                                    return sum > Integer.parseInt(value);
                                }
                                
                                return calculate(test, value);
                            """.trimIndent()),
                    )).getOrHandle { ex -> throw ex }
            println(result)
        }
    }
}