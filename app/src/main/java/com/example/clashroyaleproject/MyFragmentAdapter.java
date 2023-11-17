package com.example.clashroyaleproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyFragmentAdapter extends FragmentStateAdapter {
    public MyFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new ChestsFragment();
        } else if (position == 1){
            return new CardsFragment();
        }
        return new CurrenciesFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
