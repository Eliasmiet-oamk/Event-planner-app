package mobile.event_planner_app

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var currentUser: FirebaseUser? = null
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun login(view: View) {
        emailField = findViewById(R.id.username)
        passwordField = findViewById(R.id.password)
        auth.signInWithEmailAndPassword( emailField.text.toString(), passwordField.text.toString() )
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    currentUser = auth.currentUser
                    val toast = Toast.makeText(this, "Authentication successful.", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER,0,0)
                    toast.show()
                    findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_home)
                }else{
                    val toast = Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER,0,0)
                    toast.show()
                }
            }
        closeKeyboard()
    }
    private fun closeKeyboard(){
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }
    }

    fun register(view: View) {
        emailField = findViewById(R.id.username)
        passwordField = findViewById(R.id.password)
        auth.createUserWithEmailAndPassword( emailField.text.toString(), passwordField.text.toString() )
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    currentUser = auth.currentUser
                    val toast = Toast.makeText(this, "user creation successful.", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER,0,0)
                    toast.show()
                    findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_home)
                }else{
                    val toast = Toast.makeText(this, "user creation failed.", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER,0,0)
                    toast.show()
                }
            }
        closeKeyboard()
    }

}