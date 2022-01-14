package com.aadarshkt.eloquence.datasource

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class WordEntity(

    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "meaning") val meaning: String
) {
    constructor(name: String, sentence: String) : this(0L, name, sentence)
}
