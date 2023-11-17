package com.example.clashroyaleproject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//Extracts data and displays it in recyclerview
public class spriteListRecyclerViewAdapter extends RecyclerView.Adapter<spriteListRecyclerViewAdapter.myViewHolder>  {

    private Context context;

    //redundant, delete this


    Sprite [] spriteArray;



    private SpriteListRVAInterface spriteListRVAInterface;






    public spriteListRecyclerViewAdapter(Context context, Sprite [] spriteArray, SpriteListRVAInterface spriteListRVAInterface) {
        this.context = context;
        this.spriteArray = spriteArray;
        this.spriteListRVAInterface = spriteListRVAInterface;



    }


    //This creates the viewHolder, which is designed in the myViewHolder class
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sprite, parent, false);

        myViewHolder mvh = new myViewHolder(view, spriteListRVAInterface, spriteArray);

        return mvh;
    }

    //gets the myViewHolder properties and binds the data(spriteArrayList) to each property
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final int pos = position;


        new Handler(Looper.getMainLooper()).post(new Runnable(){
            @Override
            public void run() {
                Sprite sprite = spriteArray[pos];
                holder.imageView.setImageResource(sprite.sprite_image);
            }
        });







    }

    //letting recyclerview know how much items there is to potentially display
    @Override
    public int getItemCount() {
        return spriteArray.length;
    }


    //Creates a viewHolder, which helps display views inside the recyclerview
    public static class myViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, book_of_books_type;
        ImageButton delete, bg;
        TextView spriteAmount;
        RelativeLayout upgradeLevel, deleteLayout;
        LinearLayout bannerGems;


        public myViewHolder(@NonNull View itemView, SpriteListRVAInterface spriteListRVAInterface, Sprite [] spriteArray) {
            super(itemView);

            imageView = itemView.findViewById(R.id.sprite);
            bg = itemView.findViewById(R.id.ib_normal);
            spriteAmount = itemView.findViewById(R.id.spriteAmount);
            upgradeLevel = itemView.findViewById(R.id.rl_upgrade_level);
            deleteLayout = itemView.findViewById(R.id.rl_remove_selected_sprite);

            delete = itemView.findViewById(R.id.ib_remove_selected_sprite);
            delete.setVisibility(View.GONE);

            upgradeLevel.setVisibility(View.GONE);
            deleteLayout.setVisibility(View.GONE);
            spriteAmount.setVisibility(View.GONE);

            book_of_books_type = itemView.findViewById(R.id.iv_book_of_books_type);
            book_of_books_type.setVisibility(View.GONE);

            bannerGems = itemView.findViewById(R.id.ll_bannerGems);
            bannerGems.setVisibility(View.GONE);



            bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    
                    if (spriteListRVAInterface != null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            spriteListRVAInterface.onItemViewClicked(spriteArray[position]);
                        }
                    }


                }
            });



        }
    }




}
