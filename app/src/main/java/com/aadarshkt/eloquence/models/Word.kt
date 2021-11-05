package com.aadarshkt.eloquence.models

data class Word(
    val id: Long,
    val name: String,
    val sentence: String,
) {
    constructor(name: String, sentence: String) : this(0L, name, sentence)
}
