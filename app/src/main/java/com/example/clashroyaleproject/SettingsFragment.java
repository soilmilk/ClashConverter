package com.example.clashroyaleproject;

import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.clashroyaleproject.Util.ChestData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private final int [] arenaIcons = {
            R.drawable.a1_goblin_stadium,
            R.drawable.a2_bone_pit,
            R.drawable.a3_barbarian_bowl,
            R.drawable.a4_spell_valley,
            R.drawable.a5_builders_workshop,
            R.drawable.a6_pekkas_playhouse,
            R.drawable.a7_royal_arena,
            R.drawable.a8_frozen_peak,
            R.drawable.a9_jungle_arena,
            R.drawable.a10_hog_mountain,
            R.drawable.a11_electro_valley,
            R.drawable.a12_spooky_town,
            R.drawable.a13_rascals_hideout,
            R.drawable.a14_serenity_peak,
            R.drawable.a15_miners_mine,
            R.drawable.a16_executioners_kitchen,
            R.drawable.a17_royal_crypt,
            R.drawable.a18_silent_sanctuary,
            R.drawable.a19_dragon_spa,
            R.drawable.a20_boot_camp,
            R.drawable.a21_clash_fest,
            R.drawable.a22_pancakes,
            R.drawable.a23_legendary
    };

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Resources resources;
    private ItemViewModel itemViewModel;
    private boolean first;

    private String [] currencyCostsArray, gemsOfferCurrency;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemViewModel =  new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        resources = getResources();
        currencyCostsArray = resources.getStringArray(R.array.usd);
        gemsOfferCurrency = resources.getStringArray(R.array.gemOfferCurrencyString);


        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

       Spinner spinnerGoldToGem = v.findViewById(R.id.spinner_gold_to_gem);



       SpinnerAdapter spinnerAdapter1 = new SpinnerAdapter(getContext(), R.layout.item_to_gem_item,
               new String[]{"1000", "10000", "100000"},
               new String[]{"60", "500", "4500"},
               "goldToGem",
               new int[] {resources.getColor(R.color.clash_gold), resources.getColor(R.color.clash_white)});


       spinnerGoldToGem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               itemViewModel.getGoldToGem().setValue(Double.parseDouble(resources.getStringArray(R.array.goldToGemString)[i]));
           }
           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

        spinnerGoldToGem.setAdapter(spinnerAdapter1);



       Spinner spinnerCurrencyToGem = v.findViewById(R.id.currency_to_gem);
       SpinnerAdapter spinnerCurrencyToGemAdapter = new SpinnerAdapter(getContext(), R.layout.item_to_gem_item,
               currencyCostsArray,
               gemsOfferCurrency,
               "currencyToGem",
               new int[] {resources.getColor(R.color.clash_gold), resources.getColor(R.color.clash_white)});

       spinnerCurrencyToGem.setAdapter(spinnerCurrencyToGemAdapter);

       spinnerCurrencyToGem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               //Substring to remove the currency symbol in front (e.g. $0.99 -> 0.99)
               itemViewModel.getGemToCurrency().setValue( Double.parseDouble(currencyCostsArray[i].substring(1))/Double.parseDouble(gemsOfferCurrency[i]));
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });



        //Display drop down menu

        Spinner spinnerArena = v.findViewById(R.id.spinner_arena);
        ArenaAdapter arenaAdapter = new ArenaAdapter(arenaIcons, getContext());

        spinnerArena.setAdapter(arenaAdapter);

        spinnerArena.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemViewModel.getArena().setValue(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerArena.setSelection(resources.getInteger(R.integer.number_of_arenas) - 1);

        Spinner spinnerCurrencies = v.findViewById(R.id.spinner_currencies);

        spinnerCurrencies.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_currencies, R.id.arenaNumber, getResources().getStringArray(R.array.currencies)));
        spinnerCurrencies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String currency = (String)adapterView.getItemAtPosition(i);
                if (!currency.equals(itemViewModel.getCurrency().getValue())){
                    itemViewModel.getCurrency().setValue(currency);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        first = true;

        itemViewModel.getCurrency().observe(getViewLifecycleOwner(), currentCurrency -> {
            if (first){
                first = false;
            } else {

                spinnerCurrencyToGemAdapter.clear();
                String[] newCurrencyCostsArray = new String[0];
                switch (currentCurrency) {
                    case "USD":
                        newCurrencyCostsArray = resources.getStringArray(R.array.usd);
                        break;
                    case "Rupees":
                        newCurrencyCostsArray = resources.getStringArray(R.array.rupees);
                        break;
                    case "Yen":
                        newCurrencyCostsArray = resources.getStringArray(R.array.yen);
                        break;
                }

                currencyCostsArray = newCurrencyCostsArray;
                //Changing the currency will update the spinner through its adapter's  notifyDataSetChanged();
                spinnerCurrencyToGemAdapter.changeCurrency(newCurrencyCostsArray);
                //After spinner change, set the selection to 0 to set new gemToCurrency ratio.
                spinnerCurrencyToGem.setAdapter(spinnerCurrencyToGemAdapter);
                spinnerCurrencyToGem.setSelection(0);
            }
        });

        //A change in arena determines the contents of the chests.
        //For loop checks and resets any chest's total_gold and ewcValue.
        itemViewModel.getArena().observe(getViewLifecycleOwner(), arena -> {
            if (itemViewModel.getSS().getValue() == null) return;
            for (Sprite s: itemViewModel.getSS().getValue()){
                if (s.type.equals("chest")){
                    new Thread(() -> {
                        ChestData chestData = ChestDataBase.getInstance(getContext()).chestDao().getChestData(s.chest_name, arena);
                        try {

                            s.ewcValue = (int)(
                                    chestData.commons * resources.getInteger(R.integer.common_to_ewc) +
                                            chestData.rares * resources.getInteger(R.integer.rare_to_ewc) +
                                            chestData.epics * resources.getInteger(R.integer.epic_to_ewc) +
                                            chestData.legendaries * resources.getInteger(R.integer.legendary_to_ewc) +
                                            chestData.champions * resources.getInteger(R.integer.champion_to_ewc)
                            );
                            s.goldValue = chestData.total_gold;
                        } catch (NullPointerException n){
                            s.ewcValue = 0;
                            s.goldValue = 0;
                        }
                    }).start();
                }
            }

        });

        return v;
    }





}