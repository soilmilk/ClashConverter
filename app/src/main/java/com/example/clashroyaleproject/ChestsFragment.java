package com.example.clashroyaleproject;


import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.clashroyaleproject.databinding.FragmentChestsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChestsFragment extends Fragment implements SpriteListRVAInterface {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private FragmentChestsBinding binding;
    private CalculateFragment calculateFragment;

    private RecyclerView.LayoutManager spriteListLayoutManager;
    private spriteListRecyclerViewAdapter rva1;

    private final Sprite [] spritesList = {
            //new Sprite(R.drawable.chest_wooden_chest, 5 ,1, "chest", "silver_chest"),
            new Sprite(R.drawable.chest_silver_chest, 5 ,1, "chest", "silver_chest", 0),
            new Sprite(R.drawable.chest_golden_chest, 5 ,1, "chest", "golden_chest", 0),
            new Sprite(R.drawable.chest_gold_crate, 5 ,1, "chest", "gold_crate", 0),
            new Sprite(R.drawable.chest_plentiful_gold_crate, 5 ,1, "chest", "plentiful_gold_crate", 0),
            new Sprite(R.drawable.chest_overflowing_gold_crate, 5 ,1, "chest", "overflowing_gold_crate", 0),
            new Sprite(R.drawable.crown_chest, 5 ,1, "chest", "crown_chest", 0),
            //new Sprite(R.drawable.chest_level_up_chest_removebg_preview, 5 ,1, "chest", "silver_chest"),
            new Sprite(R.drawable.chest_epic, 5 ,1, "chest", "epic_chest", 0),
            new Sprite(R.drawable.chest_fortune_chest_removebg_preview, 5 ,1, "chest", "fortune_chest", 0),
            new Sprite(R.drawable.chest_giantchest_closed, 5 ,1, "chest", "giant_chest", 0),
            new Sprite(R.drawable.chest_magical_chest, 5 ,1, "chest", "magical_chest", 0),
            new Sprite(R.drawable.chest_legendary_chest, 5 ,1, "chest", "legendary_chest", 0),
            new Sprite(R.drawable.chest_lightning_chest, 5 ,1, "chest", "lightning_chest", 0),
            new Sprite(R.drawable.megalightningchest_removebg_preview_1, 5 ,1, "chest", "mega_lightning_chest", 0),
            new Sprite(R.drawable.kings_chest, 5 ,1, "chest", "kings_chest", 0),
            new Sprite(R.drawable.chest_legendary_kings_chest, 5 ,1, "chest", "legendary_kings_chest", 0),
            new Sprite(R.drawable.chest_champion_chest, 5 ,1, "chest", "champion_chest", 0),
            new Sprite(R.drawable.chest_royal_wild, 5 ,1, "chest", "royal_wild_chest", 0),
    };

    public ChestsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Chests.
     */

    public static ChestsFragment newInstance(String param1, String param2) {
        ChestsFragment fragment = new ChestsFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         binding = FragmentChestsBinding.inflate(inflater, container, false);
         //setting the layoutmanager to be a grid layout
         spriteListLayoutManager = new GridLayoutManager(binding.getRoot().getContext(), 4);
         binding.rvSprites.setLayoutManager(spriteListLayoutManager);

         rva1 = new spriteListRecyclerViewAdapter(getContext(), spritesList, ChestsFragment.this );

         binding.rvSprites.setAdapter(rva1);
         binding.rvSprites.setHasFixedSize(true);
         calculateFragment = (CalculateFragment) getParentFragment();
        return binding.getRoot();
    }



    @Override
    public void onItemViewClicked(Sprite sprite) {
        calculateFragment.onItemViewClickedMainFrag(sprite);
    }


    @Override
    public void onDestroyView() {
        calculateFragment = null;
        spriteListLayoutManager = null;
        binding = null;
        rva1 = null;
        super.onDestroyView();

    }
}