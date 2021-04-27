package mobile.event_planner_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {
    private lateinit var tvKey: TextView
    private lateinit var tvName: TextView
    private lateinit var tvImage: ImageView
    private lateinit var tvDescription: TextView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvKey = findViewById(R.id.key)
        tvName = findViewById(R.id.name)
        tvImage = findViewById(R.id.image)
        tvDescription = findViewById(R.id.description)

        val key = intent.getStringExtra("key").toString()


        database = Firebase.database.reference
        database.child("Events").child(key).get().addOnSuccessListener {
            if(it.value != null ){
                val event = it.value as HashMap<String, Any>
                val name = event.get("name")
                val description = event.get("description")
                val image = event.get("image")
                tvName.text = name.toString()
                tvDescription.text = description.toString()
                Picasso.get().load(image.toString()).into(tvImage)
            }
        }.addOnFailureListener{

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

}