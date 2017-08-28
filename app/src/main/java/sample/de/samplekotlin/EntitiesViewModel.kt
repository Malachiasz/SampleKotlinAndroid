package sample.de.samplekotlin

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import sample.de.samplekotlin.dao.DatabaseCreator
import sample.de.samplekotlin.dao.Entity
import sample.de.samplekotlin.dao.EntityDao
import java.util.*

/**
 * Created by dkalinowski on 17.08.17.
 */
class EntitiesViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    val applicationContext: Context = application.applicationContext;
    val entities: LiveData<List<Entity>>

    val entityDao: EntityDao
    var random: Random = Random()

    init {
        entityDao = DatabaseCreator.createDb(applicationContext).entityDao();
        entities = entityDao.getAll()
        DbSyncAdapter.execute(entityDao);
    }

    companion object DbSyncAdapter : AsyncTask<EntityDao, Unit, Unit>() {

        override fun doInBackground(vararg entityDao: EntityDao?): Unit {
            val listOfEntities: MutableList<Entity> = ArrayList();
            val element1 = Entity()
            element1.name = "name1"
            listOfEntities.add(element1)

            val element2 = Entity()
            element2.name = "name2"
            listOfEntities.add(element2)

            entityDao.get(0)!!.insertAll(*listOfEntities.toTypedArray<Entity>())
        }
    }

    fun addItem() {
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg p0: Unit?) {
                val element1 = Entity()
                element1.name = "Added" +  random.nextInt()
                entityDao.insertAll(element1)
            }

        }.execute()


    }

    fun getNumberOfItems() : LiveData<Int> {
        return entityDao.getNumberOfItemsAsObservable()
    }

    fun deleteRandomRow() {
        object : AsyncTask<Unit, Unit, Int>() {
            override fun doInBackground(vararg p0: Unit?) : Int {
                val numberOfItems = entityDao.getNumberOfItems()
                val idToDelete = random.nextInt(numberOfItems)
                entityDao.deleteWithId(idToDelete.toLong())
                return idToDelete;
            }

            override fun onPostExecute(idToDelete: Int?) {
                Toast.makeText(applicationContext, "deleted: " + idToDelete, Toast.LENGTH_SHORT).show()
            }
        }.execute()
    }
}