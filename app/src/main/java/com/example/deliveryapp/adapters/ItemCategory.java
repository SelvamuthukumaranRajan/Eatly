package com.example.deliveryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.deliveryapp.R;

public class ItemCategory extends RecyclerView.Adapter<ItemCategory.MyViewHolder> {
    private LayoutInflater inflater;
    List<String> list;
    private final Context mContext;
    int selectedPosition = 0;

    public ItemCategory(Context ctx, List<String> list) {
        inflater = LayoutInflater.from(ctx);
        this.mContext = ctx;
        this.list = list;
    }

    @Override
    public ItemCategory.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_category, parent, false);
        ItemCategory.MyViewHolder holder = new ItemCategory.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ItemCategory.MyViewHolder holder, int position) {
        holder.category.setText(list.get(position));
        if(selectedPosition==position)
        {
            holder.category.setTextColor(ContextCompat.getColor(mContext, R.color.teal_200));
            holder.selectIndicator.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.category.setTextColor(ContextCompat.getColor(mContext, R.color.darkGrey));
            holder.selectIndicator.setVisibility(View.GONE);
        }
        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.selectCategory(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView category;
        View selectIndicator;

        public MyViewHolder(View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.tvCategory);
            selectIndicator = (View) itemView.findViewById(R.id.viewSelected);
        }
        public void selectCategory(int position)
        {
            selectedPosition = position;
            notifyDataSetChanged();
        }
    }
}
