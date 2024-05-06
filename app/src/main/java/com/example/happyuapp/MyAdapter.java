package com.example.happyuapp;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.happyuapp.FirstRegisterFragment;

public class MyAdapter extends FragmentStateAdapter {
    private int numPages;

    public MyAdapter(@NonNull FragmentActivity fragmentActivity, int numPages) {
        super(fragmentActivity);
        this.numPages = numPages;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new FirstRegisterFragment();
            case 1:
                return new SecondRegisterFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return numPages;
    }
}
