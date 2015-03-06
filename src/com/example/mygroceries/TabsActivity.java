package com.example.mygroceries;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.example.mygroceries.adapter.TabsPagerAdapter;
import com.example.mygroceries.zxing.IntentIntegrator;
import com.example.mygroceries.zxing.IntentResult;

public class TabsActivity extends FragmentActivity implements ActionBar.TabListener {

	public static ActionBar actionBar;

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabs);
		this.context = this.getLayoutInflater().getContext();

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		actionBar.addTab(actionBar.newTab().setText(R.string.title_fragment_item).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_fragment_category).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_fragment_storage_area).setTabListener(this));

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tabs, menu);

		return true;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult.getContents() != null) {
			String scanContent = scanningResult.getContents();
			new ScanBarCode(this, context, scanContent, 1);
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(TabsActivity.this, MenuActivity.class);
		startActivity(intent);
		finish();
	}
}