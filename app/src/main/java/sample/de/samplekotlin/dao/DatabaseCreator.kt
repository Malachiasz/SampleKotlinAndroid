package sample.de.samplekotlin.dao

import android.arch.persistence.room.Room
import android.content.Context

/**
 * Created by dkalinowski on 02.08.17.
 */
object DatabaseCreator {

    lateinit var db : Database;

    fun createDb(context: Context) : Database{
        db = Room.databaseBuilder(context.applicationContext, Database::class.java, "sample.db").build();
        return db;
    }
}