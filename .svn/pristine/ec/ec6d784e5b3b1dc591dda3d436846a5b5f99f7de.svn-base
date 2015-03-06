package com.example.mygroceries.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mygroceries.BuyedListFragment;
import com.example.mygroceries.TobuyListFragment;

public class ShoppingListPagerAdapter extends FragmentPagerAdapter {

	public ShoppingListPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public Fragment getItem(int index) {
		
		switch (index) {
        case 0:
            // TobuyList fragment activity
            return new TobuyListFragment();
        case 1:
            // BuyedList fragment activity
            return new BuyedListFragment();
        }
		
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
}
