package ru.ezhov.featuretoggles

import org.codehaus.janino.ExpressionEvaluator
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test


class JaninoTest {
    @Test
    @Disabled
    fun test() {
        for (i in 1..10) {
            val start = System.currentTimeMillis()


            // Now here's where the story begins...
            val ee = ExpressionEvaluator()

            // The expression will have two "int" parameters: "a" and "b".

            // The expression will have two "int" parameters: "a" and "b".
            ee.setParameters(arrayOf("a", "b"), arrayOf<Class<*>?>(String::class.java, String::class.java))

            // And the expression (i.e. "result") type is also "int".
            ee.setExpressionType(Boolean::class.java)

            // And now we "cook" (scan, parse, compile and load) the fabulous expression.

            // And now we "cook" (scan, parse, compile and load) the fabulous expression.
            ee.cook(
                    "Integer.valueOf(a) > Integer.valueOf(b + c);"
            )

            // Eventually we evaluate the expression - and that goes super-fast.

            // Eventually we evaluate the expression - and that goes super-fast.
            val result = ee.evaluate(i.toString(), (i + 1).toString()) as Boolean

            val end = System.currentTimeMillis()

            println("${end - start} / $result")
        }
    }

    @Test
    fun test1() {
        for (i in 1..15) {
            val start = System.currentTimeMillis()


            // Now here's where the story begins...
            val ee = ExpressionEvaluator()

            // The expression will have two "int" parameters: "a" and "b".

            // The expression will have two "int" parameters: "a" and "b".
            ee.setParameters(arrayOf("a"), arrayOf<Class<*>?>(String::class.java))

            // And the expression (i.e. "result") type is also "int".
            ee.setExpressionType(Boolean::class.java)

            // And now we "cook" (scan, parse, compile and load) the fabulous expression.

            // And now we "cook" (scan, parse, compile and load) the fabulous expression.
            ee.cook(
                    "Integer.valueOf(a) > 11"
            )

            // Eventually we evaluate the expression - and that goes super-fast.

            // Eventually we evaluate the expression - and that goes super-fast.
            val result = ee.evaluate(i.toString()) as Boolean

            val end = System.currentTimeMillis()

            println("${end - start} / $result")
        }
    }
}