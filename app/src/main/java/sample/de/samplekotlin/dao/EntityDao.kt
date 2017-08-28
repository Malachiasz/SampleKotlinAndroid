package sample.de.samplekotlin.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * Created by dkalinowski on 02.08.17.
 */
@Dao
interface EntityDao {
    @Query("SELECT * FROM entities")
    fun getAll(): LiveData<List<Entity>>

    @Query("SELECT * FROM entities")
    fun getAll2(): List<Entity>

    @Query("SELECT * FROM entities WHERE id = :id")
    fun getEntity(id : Long): LiveData<Entity>

    @Insert
    fun insertAll(vararg entities : Entity)

    @Delete
    fun delete(entity: Entity)

    @Query("DELETE FROM entities WHERE id = :id")
    fun deleteWithId(id : Long)

    @Query("SELECT COUNT(*) FROM entities")
    fun getNumberOfItemsAsObservable() : LiveData<Int>

    @Query("SELECT COUNT(*) FROM entities")
    fun getNumberOfItems() : Int

    @Query("SELECT * FROM entities WHERE color = :color")
    fun getEntityWithGivenColor(color : String): LiveData<List<Entity>>
}