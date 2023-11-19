package com.example.clashroyaleproject;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.clashroyaleproject.Util.ChestData;

public class PagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Sprite sprite;

    private final int [] layout;

    private String amount;
    private final CalculateFragmentInterface calculateFragment;
    private final ChestData chestData;
    private final Context context;

    private final Resources resources;
    private final ItemViewModel itemViewModel;

    //For card to gold conversions for cards
    private int convertedIntCard = 0;
    //For card to gold conversions for books
    private int convertIntBook = 0;
    private double convertDoubleCard;


    public PagerAdapter(Sprite sprite, int [] layout, CalculateFragmentInterface calculateFragment, ChestData cd, Context context, ItemViewModel itemViewModel) {
        this.sprite = sprite;
        this.layout = layout;
        this.amount = String.valueOf(sprite.defaultAmount);
        this.calculateFragment = calculateFragment;
        chestData = cd;
        this.context = context;
        resources = context.getResources();
        this.itemViewModel = itemViewModel;

        if (sprite.type.equals("chest")){
            if (chestData == null){
                layout[1] = R.layout.data_invalid;
            } else {
                setChestEWCValue();
            }

        }

    }

    public class ViewHolder0 extends RecyclerView.ViewHolder {
        EditText enter_amount;

        public ViewHolder0(@NonNull View itemView) {
            super(itemView);
            enter_amount = itemView.findViewById(R.id.edt_enter_amount);
            enter_amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before,
                                          int count) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    amount = editable.toString();
                }
            });

            enter_amount.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    calculateFragment.hideKeyBoard(v);
                }
            });


            ((RelativeLayout) itemView.findViewById(R.id.rl_close)).setVisibility(View.INVISIBLE);
            ((RelativeLayout) itemView.findViewById(R.id.rl_yes)).setVisibility(View.INVISIBLE);
            ((ImageView) itemView.findViewById(R.id.add_sprite_background)).setVisibility(View.INVISIBLE);
            ((ImageButton) itemView.findViewById(R.id.b_add)).setVisibility(View.INVISIBLE);
            ((ImageButton) itemView.findViewById(R.id.ib_close)).setVisibility(View.INVISIBLE);


        }
    }
    public class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView commons, commonsGV, rares, raresGV, epics, epicsGV, legendaries, legendariesGV, champions, championsGV, gold, totalgold;

        TextView convertedGV, totalgoldcard, invalidDataText;
        ImageView card;


        Spinner spinner;
        TextView totalGoldBook;
        RelativeLayout relativeLayoutRadioButtons;
        RadioGroup radioGroupCards;
        ConstraintLayout constraintLayoutShardInfo;

        ImageButton subtract, add;
        TextView bannerTokenGems, totalGoldBanner;

        ImageView spriteGem;
        TextView gemValue, convertedGoldFromGemValue;
        public ViewHolder1(@NonNull View itemView) {
            super(itemView);

            if (layout[1] == R.layout.data_chests) {
                commons = itemView.findViewById(R.id.tv_commons);
                commonsGV = itemView.findViewById(R.id.tv_commons_goldvalue);
                rares = itemView.findViewById(R.id.tv_rares);
                raresGV = itemView.findViewById(R.id.tv_rares_goldvalue);
                epics = itemView.findViewById(R.id.tv_epics);
                epicsGV = itemView.findViewById(R.id.tv_epics_goldvalue);
                legendaries = itemView.findViewById(R.id.tv_legendaries);
                legendariesGV = itemView.findViewById(R.id.tv_legendaries_goldvalue);
                champions = itemView.findViewById(R.id.tv_champions);
                championsGV = itemView.findViewById(R.id.tv_champions_goldValue);
                gold = itemView.findViewById(R.id.tv_goldAmount);
                totalgold = itemView.findViewById(R.id.tv_totalGold);
            } else if (layout[1] == R.layout.data_card) {
                card = itemView.findViewById(R.id.iv_card);
                convertedGV = itemView.findViewById(R.id.tv_one_card_goldvalue);
                totalgoldcard = itemView.findViewById(R.id.tv_totalGoldCard);
            } else if (layout[1] == R.layout.data_book) {
                spinner = itemView.findViewById(R.id.upgrade_spinner);
                totalGoldBook = itemView.findViewById(R.id.tv_totalGoldBook);
                relativeLayoutRadioButtons = itemView.findViewById(R.id.rl_radio_buttons);
                radioGroupCards = itemView.findViewById(R.id.rg_cards);
                constraintLayoutShardInfo = itemView.findViewById(R.id.cl_shard_info);

                //code
            } else if (layout[1] == R.layout.data_banner_token) {
                subtract = itemView.findViewById(R.id.ib_subtract);
                add = itemView.findViewById(R.id.ib_add);
                bannerTokenGems = itemView.findViewById(R.id.banner_token_gems);
                totalGoldBanner = itemView.findViewById(R.id.tv_totalGoldBanner);
            } else if (layout[1] == R.layout.data_gem_value_sprites) {
                spriteGem = itemView.findViewById(R.id.iv_spriteGem);
                gemValue = itemView.findViewById(R.id.tv_gemValue);
                convertedGoldFromGemValue = itemView.findViewById(R.id.tv_totalGoldGemValue);
            } else {
                invalidDataText = itemView.findViewById(R.id.tv_invalid_data);
            }


        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new ViewHolder0(LayoutInflater.from(parent.getContext()).inflate(layout[0], parent, false));
            case 1:
                return new ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(layout[1], parent, false));

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0)holder;
                viewHolder0.enter_amount.setText(String.valueOf(sprite.defaultAmount));
                break;
            case 1:
                ViewHolder1 viewHolder1 = (ViewHolder1) holder;
                if (layout[1] == R.layout.data_chests) {
                    viewHolder1.commons.setText(String.valueOf(roundDecimal(chestData.commons, 2)));
                    viewHolder1.commonsGV.setText(String.valueOf((int) chestData.commons * resources.getInteger(R.integer.common_to_gold)));
                    viewHolder1.rares.setText(String.valueOf(roundDecimal(chestData.rares, 2)));
                    viewHolder1.raresGV.setText(String.valueOf((int) (chestData.rares * resources.getInteger(R.integer.rare_to_gold))));
                    viewHolder1.epics.setText(String.valueOf(roundDecimal(chestData.epics, 2)));
                    viewHolder1.epicsGV.setText(String.valueOf((int) (chestData.epics * resources.getInteger(R.integer.epic_to_gold))));
                    viewHolder1.legendaries.setText(String.valueOf(roundDecimal(chestData.legendaries, 3)));
                    viewHolder1.legendariesGV.setText(String.valueOf((int) (chestData.legendaries * resources.getInteger(R.integer.legendary_to_gold))));
                    viewHolder1.champions.setText(String.valueOf(roundDecimal(chestData.champions, 3)));
                    viewHolder1.championsGV.setText(String.valueOf((int) (chestData.champions * resources.getInteger(R.integer.champion_to_gold))));
                    viewHolder1.gold.setText("+" + String.valueOf(chestData.gold));
                    viewHolder1.totalgold.setText(String.valueOf(chestData.total_gold));
                } else if (layout[1] == R.layout.data_card) {
                    viewHolder1.card.setImageResource(sprite.sprite_image);
                    boolean isDouble = false;
                    if (sprite.sprite_image == R.drawable.wc_common || sprite.sprite_image == R.drawable.wc_rare || sprite.sprite_image == R.drawable.wc_epic || sprite.sprite_image == R.drawable.wc_legendary || sprite.sprite_image == R.drawable.wc_champion || sprite.sprite_image == R.drawable.wc_elite) {
                        convertedIntCard = (int) sprite.goldValue;
                    } else if (sprite.sprite_image == R.drawable.gold) {
                        convertedIntCard = 1;
                    } else if (sprite.sprite_image == R.drawable.gem) {
                        convertDoubleCard = 1 / itemViewModel.getGoldToGem().getValue();
                        convertDoubleCard = roundDecimal(convertDoubleCard, 0);
                        isDouble = true;
                    } else if (sprite.sprite_image == R.drawable.shop_token) {
                        convertDoubleCard = Double.parseDouble(resources.getString(R.string.shopTokenToGoldString));
                        isDouble = true;
                    }
                    if (!isDouble) {
                        viewHolder1.convertedGV.setText(String.valueOf(convertedIntCard));
                        viewHolder1.totalgoldcard.setText(String.valueOf(convertedIntCard));
                    } else {
                        viewHolder1.convertedGV.setText(String.valueOf(convertDoubleCard));
                        viewHolder1.totalgoldcard.setText(String.valueOf(convertDoubleCard));
                    }
                } else if (layout[1] == R.layout.data_book) {
                    viewHolder1.relativeLayoutRadioButtons.setVisibility(View.GONE);
                    viewHolder1.constraintLayoutShardInfo.setVisibility(View.GONE);
                    if (sprite.sprite_image == R.drawable.book_common) {//context is CalculateFragment.this
                        viewHolder1.spinner.setAdapter(new LevelAdapter(resources.getIntArray(R.array.common_upgrade_cards), context, R.drawable.wc_common));
                        convertIntBook = resources.getInteger(R.integer.common_to_gold);
                    } else if (sprite.sprite_image == R.drawable.book_rare) {
                        viewHolder1.spinner.setAdapter(new LevelAdapter(resources.getIntArray(R.array.rare_upgrade_cards), context, R.drawable.wc_rare));
                        convertIntBook = resources.getInteger(R.integer.rare_to_gold);
                    } else if (sprite.sprite_image == R.drawable.book_epic) {
                        viewHolder1.spinner.setAdapter(new LevelAdapter(resources.getIntArray(R.array.epic_upgrade_cards), context, R.drawable.wc_epic));
                        convertIntBook = resources.getInteger(R.integer.epic_to_gold);
                    } else if (sprite.sprite_image == R.drawable.book_legendary) {
                        viewHolder1.spinner.setAdapter(new LevelAdapter(resources.getIntArray(R.array.legendary_upgrade_cards), context, R.drawable.wc_legendary));
                        convertIntBook = resources.getInteger(R.integer.legendary_to_gold);
                    } else if (sprite.sprite_image == R.drawable.magic_coin) {
                        viewHolder1.spinner.setAdapter(new LevelAdapter(resources.getIntArray(R.array.upgradeGold), context, R.drawable.gold));
                        convertIntBook = 1;
                    } else if (sprite.sprite_image == R.drawable.evolution_shard || sprite.sprite_image == R.drawable.wild_shard) {
                        viewHolder1.spinner.setVisibility(View.GONE);
                        viewHolder1.relativeLayoutRadioButtons.setVisibility(View.VISIBLE);
                        viewHolder1.constraintLayoutShardInfo.setVisibility(View.VISIBLE);
                        int[] shardGoldValues = resources.getIntArray(R.array.shardToGoldString);
                        viewHolder1.radioGroupCards.setOnCheckedChangeListener((radioGroup, id) -> {
                            if (id == R.id.rb_common) {
                                viewHolder1.totalGoldBook.setText(String.valueOf(shardGoldValues[0]));
                                sprite.drawable_book_of_books_type = R.drawable.wc_common;
                            } else if (id == R.id.rb_rare) {
                                viewHolder1.totalGoldBook.setText(String.valueOf(shardGoldValues[1]));
                                sprite.drawable_book_of_books_type = R.drawable.wc_rare;
                            } else if (id == R.id.rb_epic) {
                                viewHolder1.totalGoldBook.setText(String.valueOf(shardGoldValues[2]));
                                sprite.drawable_book_of_books_type = R.drawable.wc_epic;
                            } else if (id == R.id.rb_legendary) {
                                viewHolder1.totalGoldBook.setText(String.valueOf(shardGoldValues[3]));
                                sprite.drawable_book_of_books_type = R.drawable.wc_legendary;
                            } else if (id == R.id.rb_champion) {
                                viewHolder1.totalGoldBook.setText(String.valueOf(shardGoldValues[4]));
                                sprite.drawable_book_of_books_type = R.drawable.wc_champion;
                            }
                        });
                        viewHolder1.radioGroupCards.check(R.id.rb_common);
                    } else if (sprite.sprite_image == R.drawable.book_of_books) {
                        viewHolder1.relativeLayoutRadioButtons.setVisibility(View.VISIBLE);
                        viewHolder1.radioGroupCards.setOnCheckedChangeListener((radioGroup, id) -> {
                            if (id == R.id.rb_common) {
                                viewHolder1.spinner.setAdapter(new LevelAdapter(resources.getIntArray(R.array.common_upgrade_cards), context, R.drawable.wc_common));
                                convertIntBook = resources.getInteger(R.integer.common_to_gold);
                                sprite.drawable_book_of_books_type = R.drawable.wc_common;
                            } else if (id == R.id.rb_rare) {
                                viewHolder1.spinner.setAdapter(new LevelAdapter(resources.getIntArray(R.array.rare_upgrade_cards), context, R.drawable.wc_rare));
                                convertIntBook = resources.getInteger(R.integer.rare_to_gold);
                                sprite.drawable_book_of_books_type = R.drawable.wc_rare;
                            } else if (id == R.id.rb_epic) {
                                viewHolder1.spinner.setAdapter(new LevelAdapter(resources.getIntArray(R.array.epic_upgrade_cards), context, R.drawable.wc_epic));
                                convertIntBook = resources.getInteger(R.integer.epic_to_gold);
                                sprite.drawable_book_of_books_type = R.drawable.wc_epic;
                            } else if (id == R.id.rb_legendary) {
                                viewHolder1.spinner.setAdapter(new LevelAdapter(resources.getIntArray(R.array.legendary_upgrade_cards), context, R.drawable.wc_legendary));
                                convertIntBook = resources.getInteger(R.integer.legendary_to_gold);
                                sprite.drawable_book_of_books_type = R.drawable.wc_legendary;
                            } else if (id == R.id.rb_champion) {
                                viewHolder1.spinner.setAdapter(new LevelAdapter(resources.getIntArray(R.array.champion_upgrade_cards), context, R.drawable.wc_champion));
                                convertIntBook = resources.getInteger(R.integer.champion_to_gold);
                                sprite.drawable_book_of_books_type = R.drawable.wc_champion;
                            }
                        });
                        viewHolder1.radioGroupCards.check(R.id.rb_champion);
                    }


                    viewHolder1.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            TextView a = view.findViewById(R.id.tv_amount);
                            TextView lvlStart = view.findViewById(R.id.tv_level_start);
                            TextView lvlEnd = view.findViewById(R.id.tv_level_end);

                            //Setting gv of book to whatever the user chooses
                            int amount = Integer.parseInt(a.getText().toString()) * convertIntBook;
                            viewHolder1.totalGoldBook.setText(String.valueOf(amount));

                            sprite.goldValue = amount;

                            //Setting added sprite levelStart and levelEnd so RV can display it ex. 13 -> 14.

                            sprite.startLevel = Integer.parseInt(lvlStart.getText().toString());
                            sprite.endLevel = Integer.parseInt(lvlEnd.getText().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else if (layout[1] == R.layout.data_banner_token) {
                    int gems = resources.getInteger(R.integer.banner_gems);
                    viewHolder1.bannerTokenGems.setText(String.valueOf(resources.getInteger(R.integer.banner_gems)));
                    sprite.bannerGems = gems;


                    viewHolder1.totalGoldBanner.setText(String.valueOf(roundDecimal(gems / itemViewModel.getGoldToGem().getValue(), 0)));




                    viewHolder1.subtract.setOnClickListener(view -> {
                        viewHolder1.subtract.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce));
                        int amt = Integer.parseInt(viewHolder1.bannerTokenGems.getText().toString());
                        if (amt == gems) {
                            calculateFragment.customToast(resources.getString(R.string.min_amount_gems) + gems + ".");
                        } else {
                            viewHolder1.bannerTokenGems.setText(String.valueOf(amt -= gems));
                            sprite.bannerGems -= gems;
                            viewHolder1.totalGoldBanner.setText(String.valueOf(roundDecimal(amt / itemViewModel.getGoldToGem().getValue(), 0)));
                            sprite.goldValue = sprite.bannerGems / itemViewModel.getGoldToGem().getValue();


                        }
                    });

                    viewHolder1.add.setOnClickListener(view -> {
                        viewHolder1.add.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce));
                        int amt = Integer.parseInt(viewHolder1.bannerTokenGems.getText().toString());
                        if (amt == 800) {
                            calculateFragment.customToast(resources.getString(R.string.max_amount_gems));

                        } else {
                            viewHolder1.bannerTokenGems.setText(String.valueOf(amt += gems));
                            sprite.bannerGems += gems;
                            viewHolder1.totalGoldBanner.setText(String.valueOf(roundDecimal(amt / itemViewModel.getGoldToGem().getValue(), 0)));
                            sprite.goldValue = sprite.bannerGems / itemViewModel.getGoldToGem().getValue();

                        }
                    });
                } else if (layout[1] == R.layout.data_gem_value_sprites) {
                    viewHolder1.spriteGem.setImageResource(sprite.sprite_image);

                    int gemValue = 0;
                    if (sprite.sprite_image == R.drawable.chest_key) {
                        gemValue = resources.getInteger(R.integer.chest_key_gems);
                    } else if (sprite.sprite_image == R.drawable.boost_potion) {
                        gemValue = resources.getInteger(R.integer.boost_potion_gems);
                    }
                    viewHolder1.gemValue.setText(String.valueOf(gemValue));
                    viewHolder1.convertedGoldFromGemValue.setText(String.valueOf(roundDecimal((double) gemValue / itemViewModel.getGoldToGem().getValue(), 0)));
                } else {
                    viewHolder1.invalidDataText.setText(resources.getString(R.string.not_found)+ itemViewModel.getArena().getValue());
                }


        }
    }

    @Override
    public int getItemCount() {
        return layout.length;
    }

    public String getEditText(){
        return amount;
    }

    public double roundDecimal(double d, int decimalPlaces){
        double factor = Math.pow(10, decimalPlaces);
        return (double) Math.round(d* factor) / factor;
    }

    private void setChestEWCValue() {
        int ewcValue = 0;
        ewcValue += (int) chestData.commons * resources.getInteger(R.integer.common_to_ewc)
                + (int) chestData.rares * resources.getInteger(R.integer.rare_to_ewc)
                + (int) chestData.epics * resources.getInteger(R.integer.epic_to_ewc)
                + (int) chestData.legendaries * resources.getInteger(R.integer.legendary_to_ewc)
                + (int) chestData.champions * resources.getInteger(R.integer.champion_to_ewc);

        sprite.ewcValue = ewcValue;

    }

}
