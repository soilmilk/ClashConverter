package com.example.clashroyaleproject;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clashroyaleproject.Util.ChestData;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("FieldCanBeLocal")
public class CalculateFragment extends Fragment implements CalculateFragmentInterface {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private boolean invalidChest;

    private View v, v1;
    private ItemViewModel itemViewModel;


    private TabLayout tabLayout, tabLayout2;
    private ViewPager2 viewPager2, viewPagerPopUp;
    private MyFragmentAdapter fragmentAdapter;
    private FragmentManager  fragmentManager;

    private int layout;
    private Toast t;
    private TextView tv;

    private Resources resources;

    private PagerAdapter pagerAdapter;

    public CalculateFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Calculate.
     */

    public static CalculateFragment newInstance(String param1, String param2) {
        CalculateFragment fragment = new CalculateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_calculate, container, false);

        LayoutInflater l = getLayoutInflater();
        v1 = l.inflate(R.layout.toast,null);
        t = new Toast(getContext());
        tv = v1.findViewById(R.id.tv_toast);
        t.setDuration(Toast.LENGTH_SHORT);

        tabLayout = v.findViewById(R.id.tabLayout);
        viewPager2 = v.findViewById(R.id.viewPagerSprites);

        //set drawable?
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.kings_chest));
        TabLayout.Tab tab2 = tabLayout.newTab().setIcon(R.drawable.wc_elite);
        tabLayout.addTab(tab2 );
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.gem));

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) tab.setCustomView(R.layout.view_home_tab);
        }

        fragmentManager = getChildFragmentManager();
        fragmentAdapter = new MyFragmentAdapter(fragmentManager, getViewLifecycleOwner().getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        resources = getResources();

        return v;
    }



    public void onItemViewClickedMainFrag(Sprite sprite){


        //Keeping an instance of CalculateFragment in CardsFragment and holding the CalculateFragment view. Better to create an abstract class and get fragments to inherit it and use onItemClicked.
        //This might be chestData.legendaries * resources.getInteger(R.integer.legendary_to_ewc)ing bc u are referencing v
        View view = v.findViewById(R.id.fragment_calculate);

        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_sprite, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in));


        popupWindow.setContentView(popupView);

        view.post(() -> popupWindow.showAtLocation(view, Gravity.CENTER,0 , 35));

        viewPagerPopUp = popupView.findViewById(R.id.viewPagerInfo);

        int arena = itemViewModel.getArena().getValue();
        invalidChest = false;

        //Changes Layout and sets base gold value for every sprite.
        switch(sprite.type){
            case "chest":
                layout = R.layout.data_chests;
                new Thread(() -> {
                    ChestData chestData = ChestDataBase.getInstance(getContext()).chestDao().getChestData(sprite.chest_name, arena);
                    try {
                        sprite.ewcValue = (int)(
                                chestData.commons * resources.getInteger(R.integer.common_to_ewc) +
                                        chestData.rares * resources.getInteger(R.integer.rare_to_ewc) +
                                        chestData.epics * resources.getInteger(R.integer.epic_to_ewc) +
                                        chestData.legendaries * resources.getInteger(R.integer.legendary_to_ewc) +
                                        chestData.champions * resources.getInteger(R.integer.champion_to_ewc)
                        );
                        sprite.goldValue = chestData.total_gold;
                    } catch (NullPointerException n){
                        sprite.ewcValue = 0;
                        sprite.goldValue = 0;
                        invalidChest = true;
                    }

                }).start();
                break;
            case "card":
            case "currency": //currency like gold, shop tokens, gem use same layout as card
                layout = R.layout.data_card;
                setCurrencyBaseGV(sprite);
                break;
            case "magic_coin":
            case "shard":
            case "book":
                layout = R.layout.data_book;
                setBookBaseGV(sprite);
                break;
            case "banner_token":
                layout = R.layout.data_banner_token;
                sprite.goldValue = 50/ itemViewModel.getGoldToGem().getValue();
                break;
            case "gemValue":
                layout = R.layout.data_gem_value_sprites;
                if (sprite.sprite_image == R.drawable.boost_potion) {
                    sprite.goldValue = resources.getInteger(R.integer.boost_potion_gems) / itemViewModel.getGoldToGem().getValue();
                } else if (sprite.sprite_image == R.drawable.chest_key) {
                    sprite.goldValue = 72 / itemViewModel.getGoldToGem().getValue();
                }
                break;
        }

        new Thread((() -> {
            if (getContext() == null) return;
            pagerAdapter = new PagerAdapter(
                    sprite,
                    new int[] {R.layout.dialog_add_sprite, layout},
                    CalculateFragment.this ,
                    ChestDataBase.getInstance(getContext()).chestDao().getChestData(sprite.chest_name, arena),
                    getContext(),
                    itemViewModel);

            if (getActivity() == null) return;
            getActivity().runOnUiThread(() -> viewPagerPopUp.setAdapter(pagerAdapter));
        })).start();

        tabLayout2 = popupView.findViewById(R.id.tabLayout2);

        tabLayout2.addTab(tabLayout2.newTab().setIcon(R.drawable.baseline_add_circle_24));
        tabLayout2.addTab(tabLayout2.newTab().setIcon(R.drawable.baseline_info_24) );

        tabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerPopUp.setCurrentItem(tab.getPosition());

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPagerPopUp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout2.selectTab(tabLayout2.getTabAt(position));
            }
        });

        //Handle all the components
        ImageButton b = popupView.findViewById(R.id.ib_close2);
        b.setOnClickListener(view1 -> {


            popupView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_out));

            popupView.postDelayed(popupWindow::dismiss, 250);

        });

        ImageButton add = popupView.findViewById(R.id.b_add_popup);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (invalidChest){
                    tv.setText(resources.getString(R.string.not_found) + itemViewModel.getArena().getValue() +  ")");
                    t.setView(v1);
                    t.show();
                    return;
                }

                int amountEntered = 1;
                int amountPresent = 0;

                try {
                    amountEntered = Integer.parseInt(pagerAdapter.getEditText());
                } catch (NumberFormatException n){
                    tv.setText(resources.getString(R.string.max_amount_items)   + sprite.maxAmountSelected + ".");
                    t.setView(v1);
                    t.show();
                    return;
                }

                try {
                    if (sprite.startLevel == -1){
                        amountPresent = itemViewModel.getSSA().getValue().get(itemViewModel.getSS().getValue().indexOf(sprite));
                    } else {
                        for (Sprite s: itemViewModel.getSS().getValue()){
                            if(sprite.areSpritesEqual(s)){
                                amountPresent = itemViewModel.getSSA().getValue().get(itemViewModel.getSS().getValue().indexOf(s));
                            }
                        }
                    }

                } catch (Exception IndexOutOfBoundsException) {
                    //do nothing
                }

                if (amountEntered + amountPresent > sprite.maxAmountSelected) {
                    tv.setText(resources.getString(R.string.max_amount_items)   + sprite.maxAmountSelected + ".");
                    t.setView(v1);
                    t.show();

                } else {
                    popupView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_out));
                    popupView.postDelayed(popupWindow::dismiss, 250);

                    //If the sprite has a startLevel (e.g. a common book), then loop thru spritesSelected to check if that book with startLevel exists
                    boolean found = false;
                    if (sprite.startLevel != -1 || sprite.sprite_image == R.drawable.wild_shard || sprite.sprite_image == R.drawable.evolution_shard){
                        for (Sprite s: itemViewModel.getSS().getValue()){
                            if (sprite.areSpritesEqual(s)){
                                int pos = itemViewModel.getSS().getValue().indexOf(s);
                                ArrayList<Integer> oldSSA = itemViewModel.getSSA().getValue();
                                int amtPresent = oldSSA.get(pos);
                                oldSSA.set(pos, amtPresent + amountEntered);
                                itemViewModel.setSSA(oldSSA);
                                itemViewModel.recalculateValues();

                                found = true;
                            }
                        }


                        //If it does not exist, add a new book Sprite because the books need to be different  (e.g. common book 13->14, common book 12->13)
                        if (!found){
                            itemViewModel.addSprite(createNewSprite(sprite), amountEntered);
                        }
                    } else {
                        //This applies to chests, cards, etc.
                        itemViewModel.addSprite(sprite, amountEntered);
                    }



                }

            }

            private Sprite createNewSprite(Sprite sprite) {
                Sprite newSprite = new Sprite(sprite.sprite_image, sprite.maxAmountSelected, sprite.defaultAmount, sprite.type, sprite.chest_name, sprite.ewcValue);
                newSprite.startLevel = sprite.startLevel;
                newSprite.endLevel = sprite.endLevel;
                newSprite.drawable_book_of_books_type = sprite.drawable_book_of_books_type;
                newSprite.bannerGems = sprite.bannerGems;
                newSprite.goldValue = sprite.goldValue;
                newSprite.ewcValue = sprite.ewcValue;

                return newSprite;
            }
        });

        ImageView displaySprite = popupView.findViewById(R.id.displaySprite);
        
        displaySprite.setImageResource(sprite.sprite_image);

    }

    private void setCurrencyBaseGV(Sprite sprite) {
        if (sprite.sprite_image == R.drawable.wc_common) {
            sprite.goldValue = resources.getInteger(R.integer.common_to_gold);
        } else if (sprite.sprite_image == R.drawable.wc_rare) {
            sprite.goldValue = resources.getInteger(R.integer.rare_to_gold);
        } else if (sprite.sprite_image == R.drawable.wc_epic) {
            sprite.goldValue = resources.getInteger(R.integer.epic_to_gold);
        } else if (sprite.sprite_image == R.drawable.wc_legendary) {
            sprite.goldValue = resources.getInteger(R.integer.legendary_to_gold);
        } else if (sprite.sprite_image == R.drawable.wc_champion) {
            sprite.goldValue = resources.getInteger(R.integer.champion_to_gold);
        } else if (sprite.sprite_image == R.drawable.wc_elite) {
            sprite.goldValue = resources.getInteger(R.integer.ewc_to_gold);
        } else if (sprite.sprite_image == R.drawable.gold) {
            sprite.goldValue = 1;
        } else if (sprite.sprite_image == R.drawable.gem) {
            sprite.goldValue = 1 / itemViewModel.getGoldToGem().getValue();
        } else if (sprite.sprite_image == R.drawable.shop_token) {
            sprite.goldValue = resources.getInteger(R.integer.shopTokenToGold);
        }
    }

    private void setBookBaseGV(Sprite sprite) {

        if (sprite.sprite_image == R.drawable.magic_coin) {
            sprite.goldValue = resources.getIntArray(R.array.upgradeGold)[12];
        } else if (sprite.sprite_image == R.drawable.wild_shard || sprite.sprite_image == R.drawable.evolution_shard) {
            sprite.goldValue = resources.getIntArray(R.array.shardToGoldString)[0];
            return;
        } else if (sprite.sprite_image == R.drawable.book_common) {
            sprite.goldValue = resources.getInteger(R.integer.common_to_gold) * resources.getIntArray(R.array.common_upgrade_cards)[12];
        } else if (sprite.sprite_image == R.drawable.book_rare) {
            sprite.goldValue = resources.getInteger(R.integer.rare_to_gold) * resources.getIntArray(R.array.rare_upgrade_cards)[10];
        } else if (sprite.sprite_image == R.drawable.book_epic) {
            sprite.goldValue = resources.getInteger(R.integer.epic_to_gold) * resources.getIntArray(R.array.epic_upgrade_cards)[7];
        } else if (sprite.sprite_image == R.drawable.book_legendary) {
            sprite.goldValue = resources.getInteger(R.integer.legendary_to_gold) * resources.getIntArray(R.array.legendary_upgrade_cards)[4];
        } else if (sprite.sprite_image == R.drawable.book_of_books) {
            sprite.goldValue = resources.getInteger(R.integer.champion_to_gold) * resources.getIntArray(R.array.champion_upgrade_cards)[2];
        }
        //Resetting start and endlevel
        sprite.startLevel = 13;
        sprite.endLevel = 14;

    }


    @Override
    public void onDestroyView() {
        v = null;
        itemViewModel = null;
        viewPager2.setAdapter(null);
        viewPager2 = null;
        tabLayout = null;
        tabLayout2 = null;
        pagerAdapter = null;
        super.onDestroyView();

    }

    @Override
    public void hideKeyBoard(View view) {
        if (getActivity() == null) return;
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void customToast(String message) {
        tv.setText(message);
        t.setView(v1);
        t.show();
    }


}
