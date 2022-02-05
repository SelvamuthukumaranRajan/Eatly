/*

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import ayalma.ir.expandablerecyclerview.ExpandableRecyclerView;

public class ExpandableRecyclerAdapter extends ExpandableRecyclerView.Adapter<ExpandableRecyclerAdapter.ChildViewHolder,ExpandableRecyclerView.SimpleGroupViewHolder,String,String>
{
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild1;
    private HashMap<String, List<String>> _listDataChild2;
    public ExpandableRecyclerAdapter (Context context,List<String> _listDataHeader,HashMap<String, List<String>> _listDataChild1,HashMap<String, List<String>> _listDataChild2)
    {
        this._context = context;
        this._listDataHeader = _listDataHeader;
        this._listDataChild1 = _listDataChild1;
        this._listDataChild2 = _listDataChild2;
    }
        @Override
        public int getGroupItemCount() {
            return _listDataHeader.size()-1;
        }

        @Override
        public int getChildItemCount(int i) {
            return _listDataChild1.size()-1;
        }

        @Override
        public String getGroupItem(int i) {
            return _listDataHeader.get(i);
        }

        @Override
        public String getChildItem(int group, int position) {
            return _listDataChild1.get(group).get(position)+"-"+_listDataChild2.get(group).get(position);
        }

        @Override
        protected ExpandableRecyclerView.SimpleGroupViewHolder onCreateGroupViewHolder(ViewGroup parent)
        {
            return new ExpandableRecyclerView.SimpleGroupViewHolder(parent.getContext());
        }

        @Override
        protected ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType)
        {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandablelist_child,parent,false);
            return new ChildViewHolder(rootView);
        }

        @Override
        public void onBindGroupViewHolder(ExpandableRecyclerView.SimpleGroupViewHolder holder, int group) {
            super.onBindGroupViewHolder(holder, group);
            holder.setText(getGroupItem(group));

        }

        @Override
        public void onBindChildViewHolder(ChildViewHolder holder, int group, int position)
        {
            super.onBindChildViewHolder(holder, group, position);
            String[] child = getChildItem(group,position).split("-");
            holder.name.setText(child[0]);
            holder.value.setText(child[1]);
        }

        @Override
        public int getChildItemViewType(int i, int i1) {
            return 1;
        }

        public class ChildViewHolder extends RecyclerView.ViewHolder
        {
            private TextView name;
            private TextView value;
            public ChildViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.txt_header);
                value = (TextView) itemView.findViewById(R.id.txt_values);
            }
        }

}
*/
package com.example.deliveryapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import java.util.List;

import com.example.deliveryapp.Cart;
import com.example.deliveryapp.R;
import com.example.deliveryapp.SQLiteDatabase.CartListModel;
import com.example.deliveryapp.SQLiteDatabase.DatabaseHandler;
import com.example.deliveryapp.databinding.ActivityShopDetailsBinding;

import static com.example.deliveryapp.commonutils.Utils.Navigation;

public class SubCategoryExpandableRecyclerAdapter extends ExpandableRecyclerAdapter<SubCategoryExpandableRecyclerAdapter.MyParentViewHolder, SubCategoryExpandableRecyclerAdapter.MyChildViewHolder> {
    private final LayoutInflater mInflater;
    ActivityShopDetailsBinding activityShopDetailsBinding;
    Context ctx;
    List<Integer> showCountLayout;
    int countInt = 0,priceInt = 0;
    String categoryType;

    public SubCategoryExpandableRecyclerAdapter(Context context, List<ParentListItem> itemList, List<Integer> showCountLayout, ActivityShopDetailsBinding activityShopDetailsBinding){
        super(itemList);
        mInflater = LayoutInflater.from(context);
        this.ctx = context;
        this.showCountLayout = showCountLayout;
        this.activityShopDetailsBinding = activityShopDetailsBinding;
    }


    @Override
    public MyParentViewHolder onCreateParentViewHolder (ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.expandablelist_head, viewGroup, false);
        return new MyParentViewHolder(view);
    }

    @Override
    public MyChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.expandablelist_child, viewGroup, false);
        return new MyChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(MyParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        SubcategoryParentListItem subcategoryParentListItem = (SubcategoryParentListItem) parentListItem;
        parentViewHolder.lblListHeader.setText(subcategoryParentListItem.mTitle);
    }

    @Override
    public void onBindChildViewHolder(MyChildViewHolder childViewHolder, int position, Object childListItem) {

        SubcategoryChildListItem subcategoryChildListItem = (SubcategoryChildListItem) childListItem;
        childViewHolder.txtListChild.setText(subcategoryChildListItem.mTitle);
        childViewHolder.txtListValue.setText("₹ "+subcategoryChildListItem.mValue);

        if ( showCountLayout.get(position) > 0 )
        {
            childViewHolder.addLayout.setVisibility(View.GONE);
            childViewHolder.counterLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            childViewHolder.addLayout.setVisibility(View.VISIBLE);
            childViewHolder.counterLayout.setVisibility(View.GONE);
            childViewHolder.count.setText(String.valueOf(showCountLayout.get(position)));
        }

        childViewHolder.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceInt += Integer.parseInt(childViewHolder.txtListValue.getText().toString().substring(2));
                childViewHolder.add(position);
            }
        });

        childViewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceInt += Integer.parseInt(childViewHolder.txtListValue.getText().toString().substring(2));
                childViewHolder.addMore(position);
                Log.d("***",showCountLayout.toString());
            }
        });

        childViewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceInt -= Integer.parseInt(childViewHolder.txtListValue.getText().toString().substring(2));
                childViewHolder.remove(position);
            }
        });

        if (countInt == 0)
            activityShopDetailsBinding.cartLayout.setVisibility(View.GONE);

        activityShopDetailsBinding.btCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(ctx);
                List<CartListModel> contacts = db.getAllCartItems();

                for (CartListModel cn : contacts) {
                    String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Price: " +
                            cn.getPrice() +" ,Quantity: " + cn.getQuantity();
                    // Writing Contacts to log
                    Log.d("***", log);
                }
                Navigation(ctx, Cart.class);
            }
        });
    }

    public class MyParentViewHolder extends ParentViewHolder{

        public TextView lblListHeader;

        public MyParentViewHolder(View itemView) {
            super(itemView);
            lblListHeader = (TextView) itemView.findViewById(R.id.txt_header);

            ImageView mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.arrow_expand_imageview);
            mArrowExpandImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isExpanded()) {
                        collapseView();
                        mArrowExpandImageView.setImageResource(R.drawable.ic_right_arrow);
                    } else {
                        expandView();
                        categoryType = lblListHeader.getText().toString();
                        mArrowExpandImageView.setImageResource(R.drawable.ic_down_arrow);
                    }
                }
            });
        }

        @Override
        public boolean shouldItemViewClickToggleExpansion() {
            return false;
        }

    }
    public class MyChildViewHolder extends ChildViewHolder {

        public TextView txtListChild;
        public TextView txtListValue;
        public TextView count;
        public ImageView minus;
        public ImageView add;
        public Button btAdd;
        public LinearLayout counterLayout;
        public LinearLayout addLayout;

        public MyChildViewHolder(View itemView) {
            super(itemView);
            txtListChild = (TextView) itemView.findViewById(R.id.tvName);
            txtListValue = (TextView) itemView.findViewById(R.id.tvPrice);
            count = (TextView) itemView.findViewById(R.id.tvCount);
            minus = (ImageView) itemView.findViewById(R.id.ivMinus);
            add = (ImageView) itemView.findViewById(R.id.ivAdd);
            btAdd = (Button) itemView.findViewById(R.id.btAdd);
            addLayout = (LinearLayout) itemView.findViewById(R.id.btAddLayout);
            counterLayout = (LinearLayout) itemView.findViewById(R.id.btCounterLayout);
        }

        public void add(int position)
        {
            addLayout.setVisibility(View.GONE);
            counterLayout.setVisibility(View.VISIBLE);
            activityShopDetailsBinding.cartLayout.setVisibility(View.VISIBLE);
            setMargins(activityShopDetailsBinding.rvCategoryResult,0,0,0,180);
            activityShopDetailsBinding.tvCount.setText(++countInt +" item");
            activityShopDetailsBinding.tvPrice.setText("₹ "+ priceInt);

            showCountLayout.set(position,1);
            count.setText(String.valueOf(showCountLayout.get(position)));
            notifyItemChanged(position);

            DatabaseHandler db = new DatabaseHandler(ctx);
            db.addToCart(new CartListModel(position,txtListChild.getText().toString(),Double.parseDouble(txtListValue.getText().toString().substring(2)),Integer.parseInt(count.getText().toString())));
        }

        public void addMore(int position)
        {
            int countValue = Integer.parseInt(count.getText().toString());
            countValue++;
            activityShopDetailsBinding.cartLayout.setVisibility(View.VISIBLE);
            activityShopDetailsBinding.tvCount.setText(++countInt +" item");
            activityShopDetailsBinding.tvPrice.setText("₹ "+ priceInt);

            showCountLayout.set(position,countValue);
            count.setText(String.valueOf(showCountLayout.get(position)));
            notifyItemChanged(position);

            DatabaseHandler db = new DatabaseHandler(ctx);
            db.updateCartItems(new CartListModel(position,txtListChild.getText().toString(),Double.parseDouble(txtListValue.getText().toString().substring(2)),Integer.parseInt(count.getText().toString())));
        }

        public void remove(int position)
        {
            int countValue = Integer.parseInt(count.getText().toString());
            countValue--;
            if (countValue == 0)
            {
                addLayout.setVisibility(View.VISIBLE);
                counterLayout.setVisibility(View.GONE);
                countInt--;
                activityShopDetailsBinding.tvPrice.setText("₹ "+ priceInt);
                if (countInt == 0)
                {
                    activityShopDetailsBinding.cartLayout.setVisibility(View.GONE);
                    setMargins(activityShopDetailsBinding.rvCategoryResult,0,0,0,0);
                }
                else
                {
                    activityShopDetailsBinding.tvCount.setText(countInt +" item");

                    showCountLayout.set(position,countValue);

                    DatabaseHandler db = new DatabaseHandler(ctx);
                    db.deleteCartItems(new CartListModel(position,txtListChild.getText().toString(),Double.parseDouble(txtListValue.getText().toString().substring(2)),Integer.parseInt(count.getText().toString())));
                }
            }
            else
            {
                activityShopDetailsBinding.tvCount.setText(--countInt +" item");
                activityShopDetailsBinding.tvPrice.setText("₹ "+ priceInt);

                showCountLayout.set(position,countValue);
                count.setText(String.valueOf(showCountLayout.get(position)));
                notifyItemChanged(position);

                DatabaseHandler db = new DatabaseHandler(ctx);
                db.updateCartItems(new CartListModel(position,txtListChild.getText().toString(),Double.parseDouble(txtListValue.getText().toString().substring(2)),Integer.parseInt(count.getText().toString())));
            }
        }
    }

    public void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}