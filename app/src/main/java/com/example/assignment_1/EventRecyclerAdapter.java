package com.example.assignment_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.EventViewHolder> {

    ArrayList<Event> data = new ArrayList<Event>();

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public EventRecyclerAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_layout, parent, false);
        EventRecyclerAdapter.EventViewHolder viewHolder = new EventRecyclerAdapter.EventViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerAdapter.EventViewHolder holder, int position) {
        holder.tvEventID.setText(data.get(position).getEVENT_ID());
        holder.tvEventName.setText(data.get(position).getEventName());
        holder.tvTicketsAvailable.setText(Integer.toString(data.get(position).getTicketsAvailable()));
        holder.tvCategoryID.setText(data.get(position).getCategoryID());
        if (data.get(position).isActive())
        {
            holder.tvEventIsActive.setText("Active");
        }else{
            holder.tvEventIsActive.setText("Not Active");
        }
    }

    @Override
    public int getItemCount() {
        if (this.data != null) { // if data is not null
            return this.data.size(); // then return the size of ArrayList
        }

        // else return zero if data is null
        return 0;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvEventID;
        TextView tvEventName;
        TextView tvTicketsAvailable ;

        TextView tvCategoryID;
        TextView tvEventIsActive ;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEventID = itemView.findViewById(R.id.event_id1);
            tvEventName = itemView.findViewById(R.id.event_name1);
            tvTicketsAvailable = itemView.findViewById(R.id.event_tickets);
            tvCategoryID = itemView.findViewById(R.id.event_category_id1);
            tvEventIsActive = itemView.findViewById(R.id.event_active1);
        }

    }

}