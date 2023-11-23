package com.example.clashroyaleproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
@SuppressWarnings("FieldCanBeLocal")
public class CalculateActivity extends AppCompatActivity implements spriteSelectedRVAInterface {

    private RecyclerView spriteSelectedRecyclerView;
    private RecyclerView.LayoutManager spriteSelectedLayoutManager;
    private spriteSelectedRecyclerViewAdapter rva2;
    private ArrayList<Sprite> spritesSelected;
    private ArrayList<Integer> spritesSelectedAmount;
    private ItemViewModel  viewModel;

    private Resources resources;
    private Parcelable mListState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate);



        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        resources = getResources();
        spritesSelectedAmount = new ArrayList<>();
        spritesSelected = new ArrayList<>();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bnv);
        NavController navController = Navigation.findNavController(CalculateActivity.this,  R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.calculateFragment,
                R.id.resultFragment,
                R.id.settingsFragment
        ).build();

        NavigationUI.setupActionBarWithNavController(CalculateActivity.this, navController, appBarConfiguration);
        try{
            getSupportActionBar().hide();
        } catch (NullPointerException n){
            //Nothing
        }


        spriteSelectedRecyclerView = findViewById(R.id.rv_selected_sprites);
        spriteSelectedLayoutManager = new GridLayoutManager(CalculateActivity.this, 4);
        spriteSelectedRecyclerView.setLayoutManager(spriteSelectedLayoutManager);

        rva2 = new spriteSelectedRecyclerViewAdapter(new ArrayList<>(), new ArrayList<>(), CalculateActivity.this);
        spriteSelectedRecyclerView.setAdapter(rva2);
        spriteSelectedRecyclerView.setHasFixedSize(true);

        viewModel.setVars(spritesSelected,
                spritesSelectedAmount,
                Double.parseDouble(getResources().getStringArray(R.array.goldToGemString)[2]),
                1.0/getResources().getInteger(R.integer.shopTokenToGold),
                Double.parseDouble(getResources().getStringArray(R.array.usd)[5].substring(1)) / Double.parseDouble(getResources().getStringArray(R.array.gemOfferCurrencyString)[0]),
                 resources.getInteger(R.integer.number_of_arenas) - 1);

        viewModel.getSSA().observe(CalculateActivity.this, ssa -> rva2.updateSpritesSelectedItems(viewModel.getSS().getValue(), ssa));

        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.circle_explosion);
        anim1.setDuration(200);
        anim1.setInterpolator(new AccelerateDecelerateInterpolator());

        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        anim2.setDuration(1000);
        anim1.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimationSet s = new AnimationSet(true);
        s.addAnimation(anim1);
        s.addAnimation(anim2);
        View explosion = findViewById(R.id.view_explosion);

        FloatingActionButton trash = findViewById(R.id.fab_delete_all);
        trash.setOnClickListener(view -> {
            trash.setVisibility(View.INVISIBLE);
            explosion.setVisibility(View.VISIBLE);
            explosion.startAnimation(s);
            explosion.setVisibility(View.INVISIBLE);
            viewModel.clearAll();
            trash.setVisibility(View.VISIBLE);
        });

    }

    @Override
    public void onItemClicked(int position) {
        rva2.notifyItemChanged(position);

    }


    @Override
    public void onDeleteButtonClicked(Sprite s) {
        s.visibility = !s.visibility;
        viewModel.deleteSprite(s);

    }
}