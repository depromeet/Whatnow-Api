package com.depromeet.whatnow.example.dto

import io.swagger.v3.oas.models.examples.Example

class ExampleHolder {
    var holder: Example? = null
    var name: String? = null
    var code: Int? = 0

    // getName
    inner class Builder {
        fun holder(holder: Example?) = apply { this@ExampleHolder.holder = holder }
        fun name(name: String?) = apply { this@ExampleHolder.name = name }
        fun code(code: Int?) = apply { this@ExampleHolder.code = code }
        fun build() = this@ExampleHolder
    }
    companion object {
        fun builder() = ExampleHolder().Builder()
    }
}