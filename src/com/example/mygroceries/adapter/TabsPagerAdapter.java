package com.example.mygroceries.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mygroceries.CategoriesFragment;
import com.example.mygroceries.ItemsFragment;
import com.example.mygroceries.StorageAreasFragment;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		
		switch (index) {
        case 0:
            // Items fragment activity
            return new ItemsFragment();
        case 1:
            // Categories fragment activity
            return new CategoriesFragment();
        case 2:
            // Storage Areas fragment activity
            return new StorageAreasFragment();
        }
		
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
        return 3;
	}
	
}
