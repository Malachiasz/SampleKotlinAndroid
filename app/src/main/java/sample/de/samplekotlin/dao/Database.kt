package sample.de.samplekotlin.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by dkalinowski on 02.08.17.
 */
@Database(entities = arrayOf(Entity::class), version = 2)
abstract class Database : RoomDatabase() {
    abstract fun entityDao(): EntityDao
}