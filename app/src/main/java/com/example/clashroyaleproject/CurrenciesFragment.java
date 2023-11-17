package com.example.clashroyaleproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clashroyaleproject.databinding.FragmentCurrenciesBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrenciesFragment extends Fragment implements SpriteListRVAInterface{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private RecyclerView.LayoutManager spriteListLayoutManager;
    private spriteListRecyclerViewAdapter rva;

    private FragmentCurrenciesBinding binding;
    private CalculateFragment calculateFragment;


    private final Sprite [] spritesList = {
            new Sprite(R.drawable.gold, 5000000 ,1000, "currency", null, 0),
            new Sprite(R.drawable.gem, 5000000 ,100, "currency", null, 0),
            new Sprite(R.drawable.shop_token, 500000 ,100, "currency", null, 0),
            new Sprite(R.drawable.chest_key, 100 ,1, "gemValue", null, 0),
            new Sprite(R.drawable.boost_potion, 100 ,1, "gemValue", null, 0),
            new Sprite(R.drawable.magic_coin, 100 ,1, "magic_coin", null, 0),
            new Sprite(R.drawable.banner, 100 ,2, "banner_token", null, 0),
            new Sprite(R.drawable.evolution_shard, 100 ,1, "shard", null, 1000),
            new Sprite(R.drawable.wild_shard, 100 ,1, "shard", null, 1000),
    };


    public CurrenciesFragment() {
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
    public static CurrenciesFragment newInstance(String param1, String param2) {
        CurrenciesFragment fragment = new CurrenciesFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentCurrenciesBinding.inflate(inflater, container, false);

        spriteListLayoutManager = new GridLayoutManager(getActivity(), 4);
        binding.rvSprites.setLayoutManager(spriteListLayoutManager);

        rva = new spriteListRecyclerViewAdapter(getContext(), spritesList, CurrenciesFragment.this );

        binding.rvSprites.setAdapter(rva);

        binding.rvSprites.setHasFixedSize(true);
        calculateFragment = (CalculateFragment) getParentFragment();
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onItemViewClicked(Sprite sprite) {
        calculateFragment.onItemViewClickedMainFrag(sprite);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        spriteListLayoutManager = null;
        rva = null;
        binding = null;

    }
}