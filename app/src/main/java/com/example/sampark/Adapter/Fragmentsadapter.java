package com.example.sampark.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sampark.Fragments.Chatfragment;
import com.example.sampark.Fragments.callfragment;
import com.example.sampark.Fragments.statusfragment;

public class Fragmentsadapter extends FragmentPagerAdapter {
    public Fragmentsadapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Chatfragment();
            case 1: return new statusfragment();
            case 2: return  new callfragment();
            default :
                return new Chatfragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title =null;
        if(position==0){
            title="Chats";
        }
        if(position==1){
            title="Status";
        }
        if(position==2){
            title="Calls";
        }
        return title;
    }
}
