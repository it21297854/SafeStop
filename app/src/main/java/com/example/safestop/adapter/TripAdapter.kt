package com.example.safestop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.safestop.R
import com.example.safestop.model.Trip

class TripAdapter : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {
    private var trips: List<Trip> = ArrayList()
    private var onItemClickListener: OnItemClickListener? = null

    inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val textviewCost: TextView = itemView.findViewById(R.id.text_view_Cost)
        val buttonEdit: Button = itemView.findViewById(R.id.button_edit)
        val buttonDelete: Button = itemView.findViewById(R.id.button_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_trip, parent, false)
        return TripViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val currentTrip = trips[position]

        holder.textViewTitle.text = "Time: " + currentTrip.selectedTime
        holder.textviewCost.text = "Total Cost: " + currentTrip.cost.toString()
        holder.buttonEdit.setOnClickListener {
            onItemClickListener?.onEditClick(position, currentTrip)
        }
        holder.buttonDelete.setOnClickListener {
            onItemClickListener?.onDeleteClick(position, currentTrip)
        }
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    interface OnItemClickListener {
        fun onEditClick(position: Int, trip: Trip)
        fun onDeleteClick(position: Int, trip: Trip)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    fun submitList(newList: List<Trip>) {
        trips = newList
        notifyDataSetChanged()
    }
}

