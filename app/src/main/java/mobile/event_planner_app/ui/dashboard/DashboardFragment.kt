package mobile.event_planner_app.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mobile.event_planner_app.Create
import mobile.event_planner_app.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference
import mobile.event_planner_app.Event


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var database: DatabaseReference

    private lateinit var edDescription: EditText
    private lateinit var edName: EditText
    private lateinit var edDate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)



        database = Firebase.database.reference
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_create, container, false)
        edDescription = view.findViewById(R.id.createDescription)
        edDate = view.findViewById(R.id.createDate)
        edName = view.findViewById(R.id.createName)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_save -> {
            val default_image = "https://firebasestorage.googleapis.com/v0/b/event-planner-app-4cc71.appspot.com/o/monitor.jpg?alt=media&token=1ce434d8-a4b8-4201-8841-a5b1f039c362"
            val name = edName.text.toString()
            val description = edDescription.text.toString()
            val date = edDate.text.toString()
            val key = database.child("Events").push().key.toString()
            val event = Event(
                    key,
                    name,
                    default_image,
                    date,
                    description
            )
            database.child("Events").child(key).setValue(event)

            true
        } else -> {
            super.onOptionsItemSelected(item)
        }
    }


}



