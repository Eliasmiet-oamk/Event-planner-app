package mobile.event_planner_app


import android.media.Image
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.core.view.View
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mobile.event_planner_app.Event
import mobile.event_planner_app.R


class Create : AppCompatActivity(){
    private lateinit var database: DatabaseReference


    private lateinit var edDescription: EditText
    private lateinit var edName: EditText
    private lateinit var edDate: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        edDescription = findViewById(R.id.createDescription)
        edDate = findViewById(R.id.createDate)
        edName = findViewById(R.id.createName)




        database = Firebase.database.reference

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun  onOptionsItemSelected(item: MenuItem) = when(item.itemId){
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
      onBackPressed()
      true
  } else -> {
   super.onOptionsItemSelected(item)
        }
    }

}






