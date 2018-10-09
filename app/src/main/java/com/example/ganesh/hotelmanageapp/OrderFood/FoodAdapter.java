package com.example.ganesh.hotelmanageapp.OrderFood;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganesh.hotelmanageapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {
    private Context mContext;
    private List<Food> foodList;
    public static DatabaseReference databaseReference;
    public static ValueEventListener postListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String key;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            //thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public FoodAdapter(Context mContext, List<Food> foodList) {
        this.mContext = mContext;
        this.foodList = foodList;
    }

    @Override
    public FoodAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_card, parent, false);

        return new FoodAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Food food = foodList.get(position);
        holder.title.setText(food.getName());
        holder.count.setText(food.getSize());;


        // loading album cover using Glide library

//        try {
//            Glide.with(mContext).load(food.getThumbnail()).into(holder.thumbnail);
//        }catch (Exception e){
//            Log.i("Error",e.getMessage());
//            e.printStackTrace();
//        }

        try {
            // Glide.with(mContext).load(food.getThumbnail()).into(holder.thumbnail);
        }catch (Exception e){
            Log.i("Error",e.getMessage());
            e.printStackTrace();
        }

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow,food);
            }
        });


    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu (View view,Food food){
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_liquor, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(food));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        Food food;
        public MyMenuItemClickListener(Food food) {
            this.food = food;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.orderNow:
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();

                    food.setQuantity(1);
                    food.setDateTime(formatter.format(date));

                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseUser = firebaseAuth.getCurrentUser();
                    //String current_uid = "u2MnKhvoF2TzdScRubamMsfXKBE3";
                    String current_uid = firebaseUser.getUid();


                    databaseReference = FirebaseDatabase.getInstance().getReference("users/" + current_uid + "/Order");
                    key = databaseReference.push().getKey();
                    databaseReference.child(key).setValue(food);
                    Toast.makeText(mContext, "Successfully Ordered", Toast.LENGTH_SHORT).show();

//                    postListener = new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Log.i("ordernow", "dataSnapshot : " + dataSnapshot);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    };
//                    databaseReference.addValueEventListener(postListener);

                    return true;

                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount () {
        return foodList.size();
    }
}

