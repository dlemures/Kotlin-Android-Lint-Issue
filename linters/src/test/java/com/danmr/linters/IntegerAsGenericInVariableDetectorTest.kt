package com.danmr.linters

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Severity
import org.intellij.lang.annotations.Language

@Suppress("UnstableApiUsage")
class IntegerAsGenericInVariableDetectorTest : LintDetectorTest() {

    override fun getDetector(): Detector {
        return IntegerAsGenericInVariableDetector()
    }

    override fun getIssues(): MutableList<Issue> {
        return mutableListOf(IntegerAsGenericInVariableDetector.ISSUE)
    }

    fun `test integer used as generic in variable declaration explicitly fails`() {
        @Language("KOTLIN")
        val source =
            """
          package com.danmr
          
          fun foo() {
            val bar: Class<Int> = Int::class.java
          }
        """
                .trimIndent()

        lint()
            .files(kotlin(source))
            .run()
            .expectCount(1, Severity.ERROR)
            .expectMatches(".*" + IntegerAsGenericInVariableDetector.ISSUE.id + ".*")
    }

    fun `test integer used as generic in variable declaration implicitly fails`() {
        @Language("KOTLIN")
        val source =
            """
          package com.danmr
          
          fun foo() {
            // When Kotlin >= 1.8, the type of bar won't be resolvable using lint
            // Instead of PsiType<Class<Integer>>, it will be PsiType<ErrorType>
            val bar = Int::class.java
          }
        """
                .trimIndent()

        lint()
            .files(kotlin(source))
            .run()
            .expectCount(1, Severity.ERROR)
            .expectMatches(".*" + IntegerAsGenericInVariableDetector.ISSUE.id + ".*")
    }
}