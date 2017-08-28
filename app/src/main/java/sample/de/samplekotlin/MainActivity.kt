package sample.de.samplekotlin

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import sample.de.samplekotlin.dao.Entity


class MainActivity : LifecycleActivity() {

    lateinit var listView: ListView
    lateinit var entitiesViewModel: EntitiesViewModel
    lateinit var adapter: SimpleAdapter

    lateinit var buttonDelete : Button
    lateinit var buttonAdd: Button

    val buttonLabel : Button by lazy {
        findViewById<Button>(R.id.button4)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        buttonDelete = findViewById(R.id.button2)
        buttonAdd = findViewById(R.id.button3)

        buttonDelete.setOnClickListener({view -> entitiesViewModel.deleteRandomRow() })

        buttonAdd.setOnClickListener({view -> entitiesViewModel.addItem() })

        entitiesViewModel = ViewModelProviders.of(this).get(EntitiesViewModel::class.java)

        entitiesViewModel.getNumberOfItems().observe(this, Observer<Int> {
            numberOfItems ->
            buttonLabel.text = numberOfItems.toString()
        })

        adapter = SimpleAdapter(applicationContext, ArrayList<Entity>())
        listView.adapter = adapter


        entitiesViewModel.entities.observe(this, Observer<List<Entity>> { list -> adapter.addItems(list!!) })
    }

    class SimpleAdapter(var context: Context, var list: List<Entity>) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val listItemView: View
            if (convertView == null) {
                val inflater: LayoutInflater = LayoutInflater.from(context)
                listItemView = inflater.inflate(R.layout.list_row, parent, false)
            } else {
                listItemView = convertView
            }

            val view: TextView = listItemView.findViewById(R.id.textView)
            val item: Entity? = list.get(position)
            view.text = item?.name
            return listItemView
        }

        override fun getItem(p0: Int): Any {
            return list.get(p0)
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }

        fun addItems(entities: List<Entity>) {
            list = entities
            notifyDataSetChanged()
        }
    }
}
