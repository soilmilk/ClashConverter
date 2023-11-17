package com.example.clashroyaleproject;
import android.content.Context;
import android.os.Bundle;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class spriteSelectedRecyclerViewAdapter extends RecyclerView.Adapter<spriteSelectedRecyclerViewAdapter.myViewHolder> {




    private final ArrayList<Sprite> spritesSelected;
    private final ArrayList<Integer> spritesSelectedAmount;

    private final spriteSelectedRVAInterface ssRVAi;



    public  spriteSelectedRecyclerViewAdapter(ArrayList<Sprite> spritesSelected, ArrayList<Integer> spritesSelectedAmount, spriteSelectedRVAInterface ssRVAi) {
        this.spritesSelected = spritesSelected;
        this.spritesSelectedAmount = spritesSelectedAmount;
        this.ssRVAi = ssRVAi;

    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sprite, parent, false);
        myViewHolder mvh = new myViewHolder(view, ssRVAi, spritesSelected);

        return mvh;
    }


    //Two onBindViewHolders to allow the number to only change if needed -> better UI for user
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final int pos = position;
        new Handler(Looper.getMainLooper()).post(() -> {
            holder.deleteLayout.setVisibility(View.GONE);
            Sprite sprite = spritesSelected.get(pos);
            holder.spriteImage.setImageResource(sprite.sprite_image);
            holder.spriteAmount.setText(spritesSelectedAmount.get(pos).toString());

            //Setting the green display for start/end levels
            if (sprite.startLevel == -1 || sprite.sprite_image == R.drawable.banner){
                holder.levelStart.setVisibility(View.GONE);
                holder.levelEnd.setVisibility(View.GONE);
                holder.relativeLayout.setVisibility(View.GONE);
            } else {
                holder.levelStart.setVisibility(View.VISIBLE);
                holder.levelEnd.setVisibility(View.VISIBLE);
                holder.relativeLayout.setVisibility(View.VISIBLE);

                holder.levelStart.setText(String.valueOf(sprite.startLevel));
                holder.levelEnd.setText(String.valueOf(sprite.endLevel));
            }

            //Setting the icon for the BoB type
            if (sprite.drawable_book_of_books_type == -1){
                holder.iVBookOfBooksType.setVisibility(View.GONE);
            } else {
                holder.iVBookOfBooksType.setVisibility(View.VISIBLE);
                holder.iVBookOfBooksType.setImageResource(sprite.drawable_book_of_books_type);
            }

            //Setting the display for banner gems
            if (sprite.sprite_image == R.drawable.banner){
                holder.bannerGemsLinearLayout.setVisibility(View.VISIBLE);
                holder.bannerGemsTextView.setText(String.valueOf(sprite.bannerGems));
            } else {
                holder.bannerGemsLinearLayout.setVisibility(View.GONE);
                holder.bannerGemsTextView.setVisibility(View.GONE);
            }

            //Setting the visibility of the delete button for sprites
            boolean isVisible = sprite.visibility;
            holder.deleteLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);

        });


    }


    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(!payloads.isEmpty()){
            Bundle o = (Bundle) payloads.get(0);
            holder.spriteAmount.setText(String.valueOf(o.getInt("newValue")));
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }


    }

    @Override
    public int getItemCount() {
        return spritesSelected.size();
    }


    public void updateSpritesSelectedItems( ArrayList<Sprite> newSpriteSelected, ArrayList<Integer> newSpriteSelectedAmount ){
        final MyDiffUtilCallBack diffCallback = new MyDiffUtilCallBack(spritesSelected, newSpriteSelected, spritesSelectedAmount, newSpriteSelectedAmount);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        spritesSelected.clear();
        spritesSelected.addAll(newSpriteSelected);
        spritesSelectedAmount.clear();
        spritesSelectedAmount.addAll(newSpriteSelectedAmount);

        diffResult.dispatchUpdatesTo(this);
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        ImageView spriteImage, spriteButtonBackground, iVBookOfBooksType;
        TextView spriteAmount, levelStart, levelEnd, bannerGemsTextView;
        ImageButton removeSelectedSpriteButton;

        RelativeLayout relativeLayout, deleteLayout;
        ConstraintLayout clSprite;
        LinearLayout bannerGemsLinearLayout;

        public myViewHolder(@NonNull View itemView, spriteSelectedRVAInterface spriteSelectedRVAInterface1, ArrayList<Sprite> spritesSelected) {
            super(itemView);
            spriteImage = itemView.findViewById(R.id.sprite);
            spriteButtonBackground = itemView.findViewById(R.id.ib_normal);
            levelStart = itemView.findViewById(R.id.upgrade_start_level);
            levelEnd = itemView.findViewById(R.id.upgrade_end_level);
            spriteAmount = itemView.findViewById(R.id.spriteAmount);
            removeSelectedSpriteButton = itemView.findViewById(R.id.ib_remove_selected_sprite);
            relativeLayout = itemView.findViewById(R.id.rl_upgrade_level);
            deleteLayout = itemView.findViewById(R.id.rl_remove_selected_sprite);
            clSprite = itemView.findViewById(R.id.cl_sprite);
            iVBookOfBooksType = itemView.findViewById(R.id.iv_book_of_books_type);
            bannerGemsLinearLayout = itemView.findViewById(R.id.ll_bannerGems);
            bannerGemsTextView = itemView.findViewById(R.id.tv_bannerGems);

            bannerGemsLinearLayout.setVisibility(View.GONE);
            spriteButtonBackground.setVisibility(View.INVISIBLE);

            clSprite.setBackgroundResource(R.color.white);
            spriteButtonBackground.setBackgroundResource(R.color.white);

            clSprite.setOnClickListener(view -> {
                if (spriteSelectedRVAInterface1 != null){
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){
                        Sprite s = spritesSelected.get(position);
                        s.visibility = !s.visibility;

                        spriteSelectedRVAInterface1.onItemClicked(position);


                    }
                }
            });

            removeSelectedSpriteButton.setOnClickListener(view -> {

                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){
                        spriteSelectedRVAInterface1.onDeleteButtonClicked(spritesSelected.get(position));
                    }

            });


        }
    }
}
