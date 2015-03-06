package com.example.mygroceries;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.mygroceries.database.DBItem;
import com.example.mygroceries.database.DefaultValues;
import com.example.mygroceries.widget.MyButton;
import com.example.mygroceries.zxing.IntentIntegrator;
import com.example.mygroceries.zxing.IntentResult;

public class MenuActivity extends Activity {

	private MyButton codebarReaderButton;
	private MyButton shoppingListButton;
	private MyButton manageStockButton;
	private MyButton helpButton;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		this.context = this.getLayoutInflater().getContext();
		
		initTypeFace();
		
//		//depois apagar
		DBItem db = new DBItem(getBaseContext());
		if(db.getAll().isEmpty()) {
			new DefaultValues(this);
		}
	}

	private void initTypeFace() {
		codebarReaderButton = (MyButton) findViewById(R.id.codebar_reader_button);
		shoppingListButton = (MyButton) findViewById(R.id.shopping_list_button);
		manageStockButton = (MyButton) findViewById(R.id.manage_stock_button);
		helpButton = (MyButton) findViewById(R.id.help_button);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	public void scanCodeBar(View view){
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();
	}
	
	public void showShoppingListActivity(View view) {
		Intent intent = new Intent(MenuActivity.this, ShoppingListActivity.class);
        startActivity(intent);
        finish();
	}
	
	public void showTabsActivity(View view) {
		Intent intent = new Intent(MenuActivity.this, TabsActivity.class);
        startActivity(intent);
        finish();
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult.getContents() != null) {
			 String scanContent = scanningResult.getContents();
			 new ScanBarCode(this, context, scanContent, 0);
		} 
	}
}