package com.danmr.kotlinandroidlint

class MyClass {
    fun foo(param: String) {
        val bar: Class<Int> = Int::class.java
        val foo = Int::class.java
        println("$bar - $foo - $param")
    }
}
