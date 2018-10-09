package com.example.ganesh.hotelmanageapp.OrderLiquor;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ganesh.hotelmanageapp.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class LiquorAdapter extends RecyclerView.Adapter<LiquorAdapter.MyViewHolder> {

    private Context mContext;
    private List<Liquor> liquorList;
    private String testTitle, key;
    public static DatabaseReference databaseReference;
    public static ValueEventListener postListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public LiquorAdapter(Context mContext, List<Liquor> liquorList) {
        this.mContext = mContext;
        this.liquorList = liquorList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.liquor_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Liquor liquor = liquorList.get(position);
        holder.title.setText(liquor.getName());
        holder.count.setText(liquor.getSize());
        this.testTitle = liquor.getName();

        // loading album cover using Glide library

        //StorageReference storageReference = FirebaseStorage.getInstance().getReference("Liquors/4a5e0a53-f77c-4141-b003-38b141d820b6");
        Glide.with(mContext).load(liquor.getFileName()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(mContext,"Name : "+ liquor.getName(),Toast.LENGTH_SHORT).show();
                showPopupMenu(holder.overflow,liquor);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view,Liquor liquor) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_liquor, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(liquor));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        Liquor liquor;

        public MyMenuItemClickListener(Liquor liquor) {
            this.liquor = liquor;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.orderNow:
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();

                    liquor.setQuantity(1);
                    liquor.setDateTime(formatter.format(date));

                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseUser = firebaseAuth.getCurrentUser();
//                    String current_uid = "u2MnKhvoF2TzdScRubamMsfXKBE3";
                   String current_uid = firebaseUser.getUid();


                    databaseReference = FirebaseDatabase.getInstance().getReference("users/" + current_uid + "/Order");
                    key = databaseReference.push().getKey();
                    databaseReference.child(key).setValue(liquor);
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
    public int getItemCount() {
        return liquorList.size();
    }

}
