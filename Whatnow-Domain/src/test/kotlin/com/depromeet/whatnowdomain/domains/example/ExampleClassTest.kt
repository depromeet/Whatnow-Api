package com.depromeet.whatnowdomain.domains.example

import com.depromeet.whatnow.domains.example.ExampleClass
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExampleClassTest {
    private val exampleClass = ExampleClass()

    @Test
    fun shouldReturn1() {
        Assertions.assertEquals(1, exampleClass.example())
    }
}
