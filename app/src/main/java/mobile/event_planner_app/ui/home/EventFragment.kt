package mobile.event_planner_app.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mobile.event_planner_app.Event
import mobile.event_planner_app.R



class EventFragment : Fragment() {

    private lateinit var Events: ArrayList<Event>
    private lateinit var rcList: RecyclerView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        database = Firebase.database.reference

        Events = arrayListOf()



/*
        for (i in 0..10){
            items.add("item $i")


        }*/


        database.child("Events").get().addOnSuccessListener {
            if (it.value != null){
                val itemsFromDB = it.value as HashMap<String, Any>
                Events.clear()
                itemsFromDB.map {(key,value) ->


                    val itemFromDb = value as HashMap<String,Any>

                    val description = itemFromDb["description"].toString()
                    val image = itemFromDb["image"].toString()
                    val date = itemFromDb["date"].toString()
                    val name = itemFromDb["name"].toString()
                    val event = Event(description, image, date, name)
                    Events.add(event)
                }
                rcList.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_list, container, false)




        rcList = view.findViewById(R.id.list)
        rcList.layoutManager = LinearLayoutManager(context)
        rcList.adapter = MyListRecyclerViewAdapter(Events)


        return view
    }

}