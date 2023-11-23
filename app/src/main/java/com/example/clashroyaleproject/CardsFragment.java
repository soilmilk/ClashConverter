package com.example.clashroyaleproject;


import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clashroyaleproject.databinding.FragmentCardsBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardsFragment extends Fragment implements SpriteListRVAInterface{


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private RecyclerView.LayoutManager spriteListLayoutManager;
    private spriteListRecyclerViewAdapter rva;
    private FragmentCardsBinding binding;
    private CalculateFragment calculateFragment;
    private final Sprite [] spritesList = {
            new Sprite(R.drawable.wc_common, 1000000 ,1, "card",null, 0),
            new Sprite(R.drawable.wc_rare, 1000000 ,1, "card", null, 0),
            new Sprite(R.drawable.wc_epic, 1000000 ,1, "card", null, 0),
            new Sprite(R.drawable.wc_legendary, 1000000 ,1, "card", null, 0),
            new Sprite(R.drawable.wc_champion, 1000000 ,1, "card", null, 0),
            new Sprite(R.drawable.wc_elite, 5000000 ,1, "card", null, 1),
            new Sprite(R.drawable.book_common, 10 ,1, "book", null, 0),
            new Sprite(R.drawable.book_epic, 10 ,1, "book", null, 0),
            new Sprite(R.drawable.book_rare, 10 ,1, "book", null, 0),
            new Sprite(R.drawable.book_legendary, 10 ,1, "book", null, 0),
            new Sprite(R.drawable.book_of_books, 10 ,1, "book", null, 0),
    };
    public CardsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cards.
     */

    public static CardsFragment newInstance(String param1, String param2) {
        CardsFragment fragment = new CardsFragment();
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
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentCardsBinding.inflate(inflater, container, false);
        spriteListLayoutManager = new GridLayoutManager(getActivity(), 4);
        binding.rvSprites.setLayoutManager(spriteListLayoutManager);

        //Setting EWC values
        Resources resources = getResources();
        spritesList[0].ewcValue = resources.getInteger(R.integer.common_to_ewc);
        spritesList[1].ewcValue = resources.getInteger(R.integer.rare_to_ewc);
        spritesList[2].ewcValue = resources.getInteger(R.integer.epic_to_ewc);
        spritesList[3].ewcValue = resources.getInteger(R.integer.legendary_to_ewc);
        spritesList[4].ewcValue = resources.getInteger(R.integer.champion_to_ewc);
        spritesList[5].ewcValue = 1;
        spritesList[6].ewcValue = resources.getInteger(R.integer.common_to_ewc) * 5000;
        spritesList[7].ewcValue = resources.getInteger(R.integer.rare_to_ewc) * 1250;
        spritesList[8].ewcValue = resources.getInteger(R.integer.epic_to_ewc) * 200;
        spritesList[9].ewcValue = resources.getInteger(R.integer.legendary_to_ewc) * 20;
        spritesList[10].ewcValue = resources.getInteger(R.integer.champion_to_ewc) * 20;

        rva = new spriteListRecyclerViewAdapter(getContext(), spritesList, CardsFragment.this );

        binding.rvSprites.setAdapter(rva);

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
        spriteListLayoutManager = null;
        rva = null;
        binding = null;
        super.onDestroyView();
    }
}