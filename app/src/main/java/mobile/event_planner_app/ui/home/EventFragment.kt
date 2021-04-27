package mobile.event_planner_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mobile.event_planner_app.Create
import mobile.event_planner_app.Event
import mobile.event_planner_app.R



class EventFragment : Fragment() {

    private lateinit var Events: ArrayList<Event>
    private lateinit var rcList: RecyclerView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)


        database = Firebase.database.reference

        Events = arrayListOf()


/*
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
                    val event = Event(key,description, image, date, name)
                    Events.add(event)
                }
                rcList.adapter?.notifyDataSetChanged()
            }
        }

 */
        val eventsListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null){
                    val itemsFromDB = snapshot.value as HashMap<String, Any>
                    Events.clear()
                    itemsFromDB.map {(key,value) ->


                        val itemFromDb = value as HashMap<String,Any>

                        val description = itemFromDb["description"].toString()
                        val image = itemFromDb["image"].toString()
                        val date = itemFromDb["date"].toString()
                        val name = itemFromDb["name"].toString()
                        val event = Event(key,description, image, date, name)
                        Events.add(event)
                    }
                    rcList.adapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.child("Events").addValueEventListener(eventsListener)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_list, container, false)




        rcList = view.findViewById(R.id.list)
        rcList.layoutManager = LinearLayoutManager(context)
        rcList.adapter = MyListRecyclerViewAdapter(Events)


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_add -> {
            startActivity(Intent(activity,Create::class.java ))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }


    }

}