package com.example.guessthecolor

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [UserMaterial :: class], version =1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun UserDAO() : UserDAO

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase{

            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}