package com.example.guessthecolor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {
        //@Update
        //suspend fun updateScore(score : Int)

        //@Query("SELECT colour FROM UserMaterial ORDER BY RANDOM() LIMIT 1")
        //suspend fun getColour(color : String)

        @Query("SELECT * FROM user_table")
        fun getData() : List<UserMaterial>

        @Insert
        fun insertData( obj : UserMaterial)

        @Query("UPDATE user_table SET Score=:score")
        fun updateScore(score : Int)

}