package com.example.guessthecolor


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class UserMaterial(
    @PrimaryKey(autoGenerate = true) val userID : Int?,
    @ColumnInfo(name = "Score") var userScore : Int?,
    @ColumnInfo(name = "colour") val color : String?
    )

