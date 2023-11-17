package com.example.clashroyaleproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Sprite implements Parcelable {

    public int sprite_image;
    //varies for different sprites e.g. gold -> 1 mil?  book of books -> 1?
    public int maxAmountSelected;
    //the default amount shown when added e.g. gold -> 1000, book of books -> 1, common wild card -> 500?
    public int defaultAmount;
    public String type;
    public String chest_name;

    public double goldValue;
    public int ewcValue;
    public boolean visibility;
    //For books and magic coin
    public int startLevel, endLevel;
    public int drawable_book_of_books_type;
    //only for banner tokens
    public int bannerGems;




    public Sprite(int sprite_image, int maxAmountSelected, int defaultAmount, @NonNull String type, String chest_name, int ewcValue) {
        this.sprite_image = sprite_image;
        this.maxAmountSelected = maxAmountSelected;
        this.defaultAmount = defaultAmount;
        this.visibility = false;
        this.type = type;
        if (type.equals("book") || type.equals ("magic_coin")){
            this.startLevel = 13;
            this.endLevel = 14;
            this.drawable_book_of_books_type = sprite_image == R.drawable.book_of_books ? R.drawable.wc_champion : -1;
        } else {
            this.startLevel = -1;
            this.endLevel = -1;
            this.drawable_book_of_books_type = sprite_image == R.drawable.wild_shard || sprite_image == R.drawable.evolution_shard ? R.drawable.wc_common : -1;
        }

        if (sprite_image == R.drawable.banner){
            bannerGems = 50;
            //TODO: fix this code. This code is needed to bc in CFragment add, it checks if startLevel is not == -1,
            this.startLevel = 13;
            this.endLevel = 14;
        } else {
            bannerGems = -1;
        }

        this.chest_name = chest_name;

        this.ewcValue = ewcValue;

        //May be defualt later

    }

    private Sprite(Parcel in) {
        sprite_image = in.readInt();
        maxAmountSelected = in.readInt();
        defaultAmount = in.readInt();

    }

    public boolean areSpritesEqual(Sprite otherSprite) {
        return startLevel == otherSprite.startLevel && sprite_image == otherSprite.sprite_image && drawable_book_of_books_type == otherSprite.drawable_book_of_books_type && bannerGems == otherSprite.bannerGems && ewcValue == otherSprite.ewcValue;
    }





    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int i) {
        out.writeInt(sprite_image);
        out.writeInt(maxAmountSelected);
        out.writeInt(defaultAmount);

    }

    public static final Parcelable.Creator<Sprite> CREATOR = new Parcelable.Creator<Sprite>() {
        public Sprite createFromParcel(Parcel in) {
            return new Sprite(in);
        }

        public Sprite[] newArray(int size) {
            return new Sprite[size];
        }
    };
}
