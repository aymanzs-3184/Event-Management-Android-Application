package com.example.assignment_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {

    ArrayList<Category> data = new ArrayList<Category>();

    public void setData(ArrayList<Category> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_layout, parent, false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.tvCategoryID.setText(data.get(position).getCATEGORY_ID());
        holder.tvCategoryName.setText(data.get(position).getCategoryName());
        holder.tvCategoryEventCount.setText(Integer.toString(data.get(position).getEventCount()));
        if (data.get(position).isActive())
        {
            holder.tvCategoryIsActive.setText("Yes");
        }else{
            holder.tvCategoryIsActive.setText("No");
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

    public class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvCategoryID;
        TextView tvCategoryName;
        TextView tvCategoryEventCount ;
        TextView tvCategoryIsActive ;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryID = itemView.findViewById(R.id.event_id1);
            tvCategoryName = itemView.findViewById(R.id.event_name1);
            tvCategoryEventCount = itemView.findViewById(R.id.event_category_id1);
            tvCategoryIsActive = itemView.findViewById(R.id.event_active1);
        }

    }

}
