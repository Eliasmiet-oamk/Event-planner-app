package mobile.event_planner_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
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
    private lateinit var adapter: MyListRecyclerViewAdapter

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
//        rcList.adapter = MyItemRecyclerViewAdapter(items)
        adapter = MyListRecyclerViewAdapter(Events)
        rcList.adapter = adapter

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu,menu)
        var searchItem = menu.findItem(R.id.action_search)
        var searchView = searchItem.actionView as SearchView

        searchView.queryHint ="Search for an event.."
        searchView.isIconifiedByDefault = false

        val magId: Int = resources.getIdentifier("android:id/search_mag_icon", null, null)
        val magImage: ImageView = searchView!!.findViewById(magId)
        val searchViewGroup: ViewGroup = magImage.getParent() as ViewGroup
        searchViewGroup.removeView(magImage)

        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return true
            }
            //
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)

        val actionExpandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                adapter.filter.filter("")
                return true
            }

            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }
        }

        searchItem.setOnActionExpandListener(actionExpandListener)

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