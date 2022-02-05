package com.example.deliveryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.deliveryapp.Cart;
import com.example.deliveryapp.R;
import com.example.deliveryapp.SQLiteDatabase.CartListModel;
import com.example.deliveryapp.SQLiteDatabase.DatabaseHandler;
import com.example.deliveryapp.databinding.ActivityShopDetailsListBinding;

import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class ShopDetailsAdapter extends RecyclerView.Adapter<ShopDetailsAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    List<DishModel> dishModelList;
    private final Context mContext;
    ActivityShopDetailsListBinding activityShopDetailsListBinding;
    int countInt = 0, priceInt = 0;
    List<Integer> countList;

    public ShopDetailsAdapter(Context ctx, List<DishModel> dishModelList, List<Integer> countList, ActivityShopDetailsListBinding activityShopDetailsListBinding) {
        inflater = LayoutInflater.from(ctx);
        this.mContext = ctx;
        this.activityShopDetailsListBinding = activityShopDetailsListBinding;
        this.dishModelList = dishModelList;
        this.countList = countList;
    }

    @NonNull
    @Override
    public ShopDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_shop_details, parent, false);
        ShopDetailsAdapter.MyViewHolder holder = new ShopDetailsAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopDetailsAdapter.MyViewHolder holder, int position) {
        DishModel dishModel = dishModelList.get(position);
        if (dishModel.getPrice().equals("")) {
            holder.header.setVisibility(View.VISIBLE);
            holder.content.setVisibility(View.GONE);
            holder.txtHeader.setText(dishModel.getName());
        } else {
            holder.header.setVisibility(View.GONE);
            holder.content.setVisibility(View.VISIBLE);
            holder.txtListChild.setText(dishModel.getName());
            holder.txtListValue.setText("₹ "+dishModel.getPrice());

            if ( countList.get(position) > 0)
            {
                holder.addLayout.setVisibility(View.GONE);
                holder.counterLayout.setVisibility(View.VISIBLE);
                holder.count.setText(String.valueOf(countList.get(position)));
            }
            else
            {
                holder.addLayout.setVisibility(View.VISIBLE);
                holder.counterLayout.setVisibility(View.GONE);
            }

            holder.btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    priceInt += Integer.parseInt(dishModel.getPrice());
                    holder.addToCart(position);
                }
            });

            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    priceInt += Integer.parseInt(dishModel.getPrice());
                    holder.addMore(position);
                }
            });

            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    priceInt -= Integer.parseInt(dishModel.getPrice());
                    holder.removeItemsFromCart(position);
                }
            });

        }
        if (countInt == 0)
            activityShopDetailsListBinding.cartLayout.setVisibility(View.GONE);
        activityShopDetailsListBinding.btCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*DatabaseHandler db = new DatabaseHandler(mContext);
                List<CartListModel> contacts = db.getAllCartItems();

                for (CartListModel cn : contacts) {
                    String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Price: " +
                            cn.getPrice() + " ,Quantity: " + cn.getQuantity();
                    // Writing Contacts to log
                    Log.d("***", log);
                }*/
                Navigation(mContext, Cart.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtHeader;
        public TextView txtListChild;
        public TextView txtListValue;
        public TextView count;
        public ImageView minus;
        public ImageView add;
        public Button btAdd;
        public LinearLayout counterLayout;
        public LinearLayout addLayout;
        public LinearLayout header;
        public LinearLayout content;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtHeader = (TextView) itemView.findViewById(R.id.txt_header);
            txtListChild = (TextView) itemView.findViewById(R.id.tvName);
            txtListValue = (TextView) itemView.findViewById(R.id.tvPrice);
            count = (TextView) itemView.findViewById(R.id.tvCount);
            minus = (ImageView) itemView.findViewById(R.id.ivMinus);
            add = (ImageView) itemView.findViewById(R.id.ivAdd);
            btAdd = (Button) itemView.findViewById(R.id.btAdd);
            addLayout = (LinearLayout) itemView.findViewById(R.id.btAddLayout);
            counterLayout = (LinearLayout) itemView.findViewById(R.id.btCounterLayout);
            header = (LinearLayout) itemView.findViewById(R.id.layout_heading);
            content = (LinearLayout) itemView.findViewById(R.id.layout_content);
        }

        public void addToCart(int position) {
            addLayout.setVisibility(View.GONE);
            counterLayout.setVisibility(View.VISIBLE);
            activityShopDetailsListBinding.cartLayout.setVisibility(View.VISIBLE);
            setMargins(activityShopDetailsListBinding.rvCategoryResult, 0, 0, 0, 180);
            activityShopDetailsListBinding.tvCount.setText(++countInt + " item");
            activityShopDetailsListBinding.tvPrice.setText("₹ " + priceInt);

            countList.set(position,1);
            count.setText(String.valueOf(countList.get(position)));
            notifyItemChanged(position);

            DatabaseHandler db = new DatabaseHandler(mContext);
            db.addToCart(new CartListModel(position, dishModelList.get(position).getName(), Double.parseDouble(dishModelList.get(position).getPrice()), Integer.parseInt(count.getText().toString())));
        }

        public void addMore(int position) {
            int countValue = Integer.parseInt(count.getText().toString());
            countValue++;
            activityShopDetailsListBinding.cartLayout.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(countValue));
            activityShopDetailsListBinding.tvCount.setText(++countInt + " item");
            activityShopDetailsListBinding.tvPrice.setText("₹ " + priceInt);

            countList.set(position,countValue);
            count.setText(String.valueOf(countList.get(position)));
            notifyItemChanged(position);

            DatabaseHandler db = new DatabaseHandler(mContext);
            db.updateCartItems(new CartListModel(position, dishModelList.get(position).getName(), Double.parseDouble(dishModelList.get(position).getPrice()), Integer.parseInt(count.getText().toString())));
        }

        public void removeItemsFromCart(int position) {
            int countValue = Integer.parseInt(count.getText().toString());
            countValue--;
            if (countValue == 0) {
                addLayout.setVisibility(View.VISIBLE);
                counterLayout.setVisibility(View.GONE);
                countInt--;
                activityShopDetailsListBinding.tvPrice.setText("₹ " + priceInt);
                if (countInt == 0) {
                    activityShopDetailsListBinding.cartLayout.setVisibility(View.GONE);
                    setMargins(activityShopDetailsListBinding.rvCategoryResult, 0, 0, 0, 0);
                } else {
                    activityShopDetailsListBinding.tvCount.setText(countInt + " item");
                }

                countList.set(position,countValue);
                count.setText(String.valueOf(countList.get(position)));
                notifyItemChanged(position);

                DatabaseHandler db = new DatabaseHandler(mContext);
                db.deleteCartItems(new CartListModel(position, dishModelList.get(position).getName(), Double.parseDouble(dishModelList.get(position).getPrice()), Integer.parseInt(count.getText().toString())));
            } else {
                count.setText(String.valueOf(countValue));
                activityShopDetailsListBinding.tvCount.setText(--countInt + " item");
                activityShopDetailsListBinding.tvPrice.setText("₹ " + priceInt);

                countList.set(position,countValue);
                count.setText(String.valueOf(countList.get(position)));
                notifyItemChanged(position);

                DatabaseHandler db = new DatabaseHandler(mContext);
                db.updateCartItems(new CartListModel(position, dishModelList.get(position).getName(), Double.parseDouble(dishModelList.get(position).getPrice()), Integer.parseInt(count.getText().toString())));
            }
        }
    }

    public void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}

