package com.stash.detekt.rules

import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CompanionObjectPositionTest {

    val testObject = CompanionObjectPosition()

    @Test
    fun `success if companion object is the first element in a class`() {
        val code: String = """
                 class CompanionObject {

                     companion object {
                         const val someValue = "companionObjectSomeValue"
                     }

                     fun someFun() {}
                 }
        """.trimIndent()

        val findings = testObject.lint(code)
        assertTrue(findings.isEmpty())
    }

    @Test
    fun `failure if companion object is not the first element in a class`() {
        val code: String = """
                 class CompanionObject {
                     fun someFun() {}

                     companion object {
                         const val someValue = "companionObjectSomeValue"
                     }
                 }
        """.trimIndent()

        val findings = testObject.lint(code)
        assertEquals(1, findings.size)
    }

    @Test
    fun `success if companion object is the first element in an enum class`() {
        val code: String = """
                  enum class CompanionObject {
                      SomeType;

                      companion object {
                          const val SOME_CONSTANT = "some value"
                      }

                      fun someFun() {}
                  }
        """.trimIndent()

        val findings = testObject.lint(code)
        assertTrue(findings.isEmpty())
    }

    @Test
    fun `failure if companion object is not the first element in an enum class`() {
        val code: String = """
                  enum class CompanionObject {
                      SomeType;

                      fun someFun() {}

                      companion object {
                          const val SOME_CONSTANT = "some value"
                      }
                  }
        """.trimIndent()

        val findings = testObject.lint(code)
        assertEquals(1, findings.size)
    }
}
