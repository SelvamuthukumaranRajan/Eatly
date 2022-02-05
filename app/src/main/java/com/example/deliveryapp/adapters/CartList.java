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
import com.example.deliveryapp.SQLiteDatabase.CartListModel;
import com.example.deliveryapp.SQLiteDatabase.DatabaseHandler;
import com.example.deliveryapp.databinding.ActivityCartBinding;

public class CartList extends RecyclerView.Adapter<CartList.MyViewHolder> {

    private final LayoutInflater inflater;
    List<CartListModel> list;
    private final Context mContext;
    double finalPrice,price;
    ActivityCartBinding activityCartBinding;

    public CartList(Context ctx, List<CartListModel> list,int price,int finalPrice,ActivityCartBinding activityCartBinding) {
        inflater = LayoutInflater.from(ctx);
        this.mContext = ctx;
        this.list = list;
        this.price = price;
        this.finalPrice = finalPrice;
        this.activityCartBinding = activityCartBinding;
    }

    @NonNull
    @Override
    public CartList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_cart, parent, false);
        CartList.MyViewHolder holder = new CartList.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartList.MyViewHolder holder, int position) {
        holder.txtListChild.setText(list.get(position).getName());
        holder.txtListValue.setText(String.format("₹ %s", list.get(position).getPrice().toString()));
        holder.count.setText(String.valueOf(list.get(position).getQuantity()));

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.addMore(position);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtListChild;
        public TextView txtListValue;
        public TextView count;
        public ImageView minus;
        public ImageView add;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtListChild = itemView.findViewById(R.id.tvName);
            txtListValue = itemView.findViewById(R.id.tvPrice);
            count = itemView.findViewById(R.id.tvCount);
            minus = itemView.findViewById(R.id.ivMinus);
            add = itemView.findViewById(R.id.ivAdd);
        }

        public void addMore(int position) {
            int countValue = Integer.parseInt(count.getText().toString());
            countValue++;
            count.setText(String.valueOf(countValue));

            DatabaseHandler db = new DatabaseHandler(mContext);
            db.updateCartItems(new CartListModel(list.get(position).getID(), txtListChild.getText().toString(), Double.parseDouble(txtListValue.getText().toString().substring(2)), Integer.parseInt(count.getText().toString())));

            price+=list.get(position).getPrice();
            finalPrice+=list.get(position).getPrice();
            activityCartBinding.tvItemPrice.setText(String.format("₹ %s", price));
            activityCartBinding.tvFinalPrice.setText(String.format("₹ %s", finalPrice));
        }

        public void remove(int position) {
            int countValue = Integer.parseInt(count.getText().toString());
            countValue--;
            price-=list.get(position).getPrice();
            finalPrice-=list.get(position).getPrice();
            activityCartBinding.tvItemPrice.setText(String.format("₹ %s", price));
            activityCartBinding.tvFinalPrice.setText(String.format("₹ %s", finalPrice));
            if (countValue == 0) {
                DatabaseHandler db = new DatabaseHandler(mContext);
                db.deleteCartItems(new CartListModel(list.get(position).getID(), txtListChild.getText().toString(), Double.parseDouble(txtListValue.getText().toString().substring(2)), Integer.parseInt(count.getText().toString())));
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,list.size());

            } else {
                count.setText(String.valueOf(countValue));

                DatabaseHandler db = new DatabaseHandler(mContext);
                db.updateCartItems(new CartListModel(list.get(position).getID(), txtListChild.getText().toString(), Double.parseDouble(txtListValue.getText().toString().substring(2)), Integer.parseInt(count.getText().toString())));

            }
        }
    }
}
