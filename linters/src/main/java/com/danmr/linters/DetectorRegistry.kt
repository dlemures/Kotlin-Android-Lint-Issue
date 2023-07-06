package com.danmr.linters

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API

class DetectorRegistry : IssueRegistry() {
    override val issues = listOf(IntegerAsGenericInVariableDetector.ISSUE)
    override val api: Int = CURRENT_API
}