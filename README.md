# Kotlin-Android-Lint-Issue
Sample app showing how type resolution for Android lint tests not working from Kotlin 1.8.X / 1.7.X

## Steps
1. Open project with Android Studio / IJ
2. Run tests inside IntegerAsGenericInVariableDetectorTest.kt
3. Both should pass, but from Kotlin 1.8.X, the second test fails
   - Reason: The inferred type for `val bar = Int::class.java` is not resolved.
