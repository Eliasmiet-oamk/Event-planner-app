package mobile.event_planner_app.ui.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.squareup.picasso.Picasso
import mobile.event_planner_app.Event
import mobile.event_planner_app.R
import java.util.*
import kotlin.collections.ArrayList


class MyListRecyclerViewAdapter(
    private val fullList: ArrayList<Event>
) : RecyclerView.Adapter<MyListRecyclerViewAdapter.ViewHolder>(), Filterable {

    var filteredList = ArrayList<Event>()

    init {
        filteredList = fullList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = fullList[position]
        holder.nameView.text = event.name
        holder.dateView.text = event.date
        holder.descriptionView.text = event.description
        holder.card.tag = event.key
        Picasso.get().load(event.image).into(holder.imageView)
    }

    override fun getItemCount(): Int = fullList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val descriptionView: TextView = view.findViewById(R.id.description)
        val imageView: ImageView = view.findViewById((R.id.image))
        val dateView: TextView = view.findViewById(R.id.date)
        val nameView: TextView = view.findViewById((R.id.name))
        val card: CardView = view.findViewById(R.id.card)

    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                var searchString = p0.toString()
                if(searchString.isEmpty()) {
                    filteredList = fullList
                } else {
                    var results = ArrayList<Event>()

                    for (row in fullList) {
                        if(row.name.toLowerCase(Locale.ROOT).contains(searchString)) {
                            results.add(row)
                        }

                    }
                    filteredList = results
                }
                var filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredList = p1?.values as ArrayList<Event>
            }
        }
    }
}