package com.example.deliveryapp.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.deliveryapp.R;
import com.example.deliveryapp.databinding.ActivityHomeNavigationBinding;

public class ShopCategory extends RecyclerView.Adapter<ShopCategory.MyViewHolder> {
    private LayoutInflater inflater;
    List<String> list;
    List<Integer> drawables;
    ActivityHomeNavigationBinding activityHomeNavigationBinding;
    private final Context mContext;
    int selectedPosition = 0;

    public ShopCategory(Context ctx, List<String> list, List<Integer> drawables, ActivityHomeNavigationBinding activityHomeNavigationBinding) {
        inflater = LayoutInflater.from(ctx);
        this.mContext = ctx;
        this.drawables = drawables;
        this.activityHomeNavigationBinding = activityHomeNavigationBinding;
        this.list = list;
    }

    @NonNull
    @Override
    public ShopCategory.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_order_category, parent, false);
        ShopCategory.MyViewHolder holder = new ShopCategory.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ShopCategory.MyViewHolder holder, int position) {
        holder.category.setText(list.get(position));
        holder.category.setCompoundDrawablesWithIntrinsicBounds(0, drawables.get(position), 0, 0);

        if (selectedPosition == position) {
            holder.category.setTextColor(ContextCompat.getColor(mContext, R.color.teal_200));
//            holder.category.setBackgroundResource(R.drawable.selected_shop);

            for (Drawable drawable : holder.category.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(holder.category.getContext(), R.color.teal_200), PorterDuff.Mode.SRC_IN));
                }
            }

            if (position > 0)
            holder.selectCategory();
        } else {
            holder.category.setTextColor(ContextCompat.getColor(mContext, R.color.darkGrey));
//            holder.category.setBackgroundResource(0);

            for (Drawable drawable : holder.category.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(holder.category.getContext(), R.color.black), PorterDuff.Mode.SRC_IN));
                }
            }
        }
        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView category;

        public MyViewHolder(View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.tv_category);
        }

        public void selectCategory() {
           /* activityHomeNavigationBinding.homeLayout.rvResult.setVisibility(View.VISIBLE);
            activityHomeNavigationBinding.homeLayout.tvNoService.setVisibility(View.GONE);
            activityHomeNavigationBinding.homeLayout.btSelectLocation.setVisibility(View.GONE);
            activityHomeNavigationBinding.homeLayout.resultDivider.setVisibility(View.VISIBLE);

            StoreResult storeResult = new StoreResult(mContext, list);
            activityHomeNavigationBinding.homeLayout.rvResult.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            activityHomeNavigationBinding.homeLayout.rvResult.setAdapter(storeResult);*/
        }
    }
}
