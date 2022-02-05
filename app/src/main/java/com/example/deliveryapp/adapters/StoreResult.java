package com.example.deliveryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.deliveryapp.R;
import com.example.deliveryapp.ShopDetailsList;

import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class StoreResult extends RecyclerView.Adapter<StoreResult.MyViewHolder> {
    private LayoutInflater inflater;
    List<String> list;
    private final Context mContext;

    public StoreResult(Context ctx, List<String> list) {
        inflater = LayoutInflater.from(ctx);
        this.mContext = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_result, parent, false);
        StoreResult.MyViewHolder holder = new StoreResult.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.openShopDetails();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView dish;
        TextView name;
        TextView location;
        TextView availability;

        public MyViewHolder(View itemView) {
            super(itemView);
            dish = (ImageView) itemView.findViewById(R.id.ivDish);
            name = (TextView) itemView.findViewById(R.id.tvName);
            location = (TextView) itemView.findViewById(R.id.tvPlace);
            availability = (TextView) itemView.findViewById(R.id.tvAvailability);
        }

        public void openShopDetails()
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation(mContext, ShopDetailsList.class);
                }
            });
        }
    }

}
