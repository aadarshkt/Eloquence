package com.aadarshkt.eloquence.datasource

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class WordEntity(

    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "sentence") val sentence: String
){
    constructor(name: String, sentence: String) : this(0L, name, sentence)
}
