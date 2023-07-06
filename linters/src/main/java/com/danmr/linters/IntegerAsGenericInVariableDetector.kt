package com.danmr.linters

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiType
import com.intellij.psi.PsiVariable
import org.jetbrains.uast.UDeclaration
import org.jetbrains.uast.UElement

@Suppress("UnstableApiUsage")
class IntegerAsGenericInVariableDetector : Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> =
        listOf(UDeclaration::class.java)

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitDeclaration(node: UDeclaration) {
                // This is a oversimplified implementation that would only work for the provided
                // test.
                if (node is PsiVariable) {
                    val type = node.type
                    // Next line will evaluate to false for the second test in
                    // IntegerAsGenericInVariableDetectorTest when using Kotlin >= 1.8.
                    // type for bar will be PsiType<ErrorType> instead of PsiType<Class<Integer>>
                    if (type is PsiClassType) {
                        val generics = type.parameters
                        val containsIntTypeGenerics =
                            generics.any { it == PsiType.INT.getBoxedType(node) }
                        if (containsIntTypeGenerics) {
                            val scope: UElement = node
                            context.report(ISSUE, scope, context.getLocation(scope), ISSUE.id)
                        }
                    }
                }
            }
        }
    }

    companion object {
        internal val ISSUE =
            Issue.create(
                "IntegerAsGenericInVariable",
                "Integer used as generic in a variable declaration",
                "We do not want integers to be used as generics... just fake scenario :)",
                Category.PRODUCTIVITY,
                7,
                Severity.ERROR,
                Implementation(
                    IntegerAsGenericInVariableDetector::class.java,
                    Scope.JAVA_FILE_SCOPE
                )
            )
    }
}
