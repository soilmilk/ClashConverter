package com.example.clashroyaleproject;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private ItemViewModel itemViewModel;


    private String mParam1;
    private String mParam2;

    private TextView  displayedAmount, valueAmount;
    private Button inGameAmount;
    private ImageButton typeButton;
    private ImageView emote, inGameAmountType, type;
    private Resources resources;



    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment result.
     */

    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        resources = getResources();


        typeButton = v.findViewById(R.id.ib_type);
        inGameAmount = v.findViewById(R.id.b_iga);
        valueAmount = v.findViewById(R.id.tv_value);
        inGameAmountType = v.findViewById(R.id.iv_cost_type);
        type =  v.findViewById(R.id.iv_type);
        emote = v.findViewById(R.id.iv_emote);
        emote.setImageResource(R.drawable.emote_loading);
        displayedAmount = v.findViewById(R.id.displayed_amount);

        //Setting the default value
        int d = itemViewModel.setType();
        type.setImageResource(d);
        inGameAmountType.setImageResource(d);

        typeButton.setOnClickListener(view -> {
            typeButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
            int d1 = itemViewModel.changeType();
            type.setImageResource(d1);
            inGameAmountType.setImageResource(d1);
        });

        inGameAmount.setOnClickListener(view -> {
            if (getContext() == null) return;
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_add_sprite);
            if (dialog.getWindow() == null) return;
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            ImageButton close = dialog.findViewById(R.id.ib_close);
            EditText enter_amount = dialog.findViewById(R.id.edt_enter_amount);
            ImageButton add = dialog.findViewById(R.id.b_add);

            enter_amount.setText(String.valueOf(itemViewModel.getIgaDisplay().getValue()));

            close.setOnClickListener(view12 -> dialog.dismiss());

            add.setOnClickListener(view1 -> {

                LayoutInflater l = getLayoutInflater();
                View v1 = l.inflate(R.layout.toast,null);
                Toast t = new Toast(getContext());
                TextView tv = v1.findViewById(R.id.tv_toast);
                t.setDuration(Toast.LENGTH_SHORT);


                try {
                    int amountEntered = Integer.parseInt(enter_amount.getText().toString());

                    if (amountEntered > 5000000){

                        tv.setText(resources.getString(R.string.exceeded_maximum_amount));
                        t.setView(v1);
                        t.show();

                    } else {
                        itemViewModel.getIgaDisplay().setValue(amountEntered);
                        dialog.dismiss();


                    }

                } catch (NumberFormatException n){
                    tv.setText(resources.getString(R.string.invalid_answer));
                    t.setView(v1);
                    t.show();

                }



            });

            enter_amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            dialog.show();

        });

        itemViewModel.recalculateValues();
        itemViewModel.setDisplayedResult();

        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Value changes based on 2 events:
        //Displayed cost
        itemViewModel.getDisplayedCost().observe(getViewLifecycleOwner(), integer -> {
            String s = integer + " ";
            displayedAmount.setText(s);
            try {
                //Checks for errors
                int i = integer/itemViewModel.getIgaDisplay().getValue();
                long l = Math.round((double) integer /itemViewModel.getIgaDisplay().getValue());
                valueAmount.setText(String.valueOf(  l + "x"));
                emote.setImageResource(returnEmote(l));
            } catch (Exception e){
                valueAmount.setText("--");
                emote.setImageResource(returnEmote(-1));
            }

        });
        //In-game cost.
        itemViewModel.getIgaDisplay().observe(getViewLifecycleOwner(), amount -> {
            inGameAmount.setText(String.valueOf(amount));
            try {
                int i = itemViewModel.getDisplayedCost().getValue()/amount;
                valueAmount.setText(Math.round((double)itemViewModel.getDisplayedCost().getValue() /amount) +"x");
                long l = Math.round((double)itemViewModel.getDisplayedCost().getValue() /amount);
                emote.setImageResource(returnEmote(l));
            } catch (Exception e){
                valueAmount.setText("--");
                emote.setImageResource(returnEmote(-1));
            }
        });

    }


    public int returnEmote(long value){
        if (value == -1){
            return R.drawable.emote_loading;
        } else if (value >= 0 && value <= 5){
            return R.drawable.emote_prince_suprise;
        } else if (value > 5 && value < 10){
            return R.drawable.emote_goblin_confused;
        } else if (value == 10){
            return R.drawable.emote_king_10_points;
        } else if (value > 10 && value < 20) {
            return R.drawable.emote_electro_wizard_thumbs_up;
        } else if (value >= 20 && value < 30){
            return R.drawable.emote_goblin_excited;
        }
        return R.drawable.emote_royal_hog_gold;
    }
}

