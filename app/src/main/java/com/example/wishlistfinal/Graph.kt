package com.example.wishlistfinal

import android.content.Context
import androidx.room.Room

object Graph {

    lateinit var dataBase: WishDataBase

    val wishRepository by lazy {
        WishRepository(wishDao = dataBase.wishDao())
    }

    //It will initialize our database
    fun provide(context: Context){
        dataBase = Room.databaseBuilder(context,WishDataBase::class.java,"wishlist.dp").build()
    }

}