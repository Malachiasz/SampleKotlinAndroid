package sample.de.samplekotlin.dao

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by dkalinowski on 02.08.17.
 */
@Entity(tableName = "entities")
class Entity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var name: String = ""

    var url: String = ""

    var color: String = "blank"
}