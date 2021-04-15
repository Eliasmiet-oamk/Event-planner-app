package mobile.event_planner_app.ui.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import mobile.event_planner_app.Event
import mobile.event_planner_app.R




class MyListRecyclerViewAdapter(
    private val values: ArrayList<Event>
) : RecyclerView.Adapter<MyListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = values[position]
        holder.nameView.text = event.name
        holder.dateView.text = event.date
        holder.descriptionView.text = event.description

        Picasso.get().load(event.image).into(holder.imageView)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val descriptionView: TextView = view.findViewById(R.id.description)
        val imageView: ImageView = view.findViewById((R.id.image))
        val dateView: TextView = view.findViewById(R.id.date)
        val nameView: TextView = view.findViewById((R.id.name))


    }
}