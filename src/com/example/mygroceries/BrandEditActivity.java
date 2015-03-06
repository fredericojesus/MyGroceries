package com.example.mygroceries;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygroceries.database.Brand;
import com.example.mygroceries.database.DBBrand;
import com.example.mygroceries.database.DBBuyedList;
import com.example.mygroceries.database.DBItem;
import com.example.mygroceries.database.DBShoppingList;
import com.example.mygroceries.database.Item;
import com.example.mygroceries.zxing.IntentIntegrator;
import com.example.mygroceries.zxing.IntentResult;

public class BrandEditActivity extends Activity {
	private TextView itemName;
//	private TextView brandId;
	private EditText brandName;
	private EditText brandDescription;
	private EditText brandBarCode;
	private EditText brandPrice;
	private TextView brandQuantity;
	private TextView brandNeed;
	private CheckBox brandFavorite;
	private Brand brand;
	private Item item;
	private int itemQuantity;
	// private long itemId = -1;
	private String codeBar = "";
	private Activity activity;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brand_edit);
		this.activity = this;

		actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		brand = (Brand) this.getIntent().getExtras().getSerializable("brand");
		item = (Item) this.getIntent().getExtras().getSerializable("item");
		codeBar = this.getIntent().getExtras().getString("codeBar");
		
		if(brand != null)
			setTitle(getString(R.string.add_brand_edit_title));

		init();
		itemName.setText(item.getName());
		itemQuantity = item.getQuantity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.brand_edit, menu);
		return true;
	}

	private void init() {
		itemName = (TextView) this.findViewById(R.id.item_name_textView);
		brandName = (EditText) this.findViewById(R.id.brand_name_editText);
		brandDescription = (EditText) this.findViewById(R.id.brand_description_editText);
		brandBarCode = (EditText) this.findViewById(R.id.brand_codebar_editText);
		brandPrice = (EditText) this.findViewById(R.id.brand_price_editText);
		brandQuantity = (TextView) this.findViewById(R.id.brand_quantity_textView);
		brandNeed = (TextView) this.findViewById(R.id.brand_need_textView);
		brandFavorite = (CheckBox) this.findViewById(R.id.brand_favourite_checkBox);
		final ImageButton need = (ImageButton) this.findViewById(R.id.need_button);
		final ImageButton notNeed = (ImageButton) this.findViewById(R.id.notNeed_button);
		Button addBrand = (Button) this.findViewById(R.id.add_brand_button);
		Button removeBrand = (Button) this.findViewById(R.id.remove_brand_button);
		ImageButton brandScan = (ImageButton) this.findViewById(R.id.btScan);
		
		if (brand != null) {
			brandName.setText(brand.getName());
			brandDescription.setText(brand.getDescription());
			brandBarCode.setText(brand.getBarCode());
			brandPrice.setText(String.valueOf(brand.getPrice()));
			brandQuantity.setText(String.valueOf(brand.getQuantity()));

			if (item.getType() == 0) {
				brandNeed.setVisibility(View.INVISIBLE);
				need.setVisibility(View.INVISIBLE);
				notNeed.setVisibility(View.INVISIBLE);

				brandQuantity.setVisibility(View.VISIBLE);
				addBrand.setVisibility(View.VISIBLE);
				removeBrand.setVisibility(View.VISIBLE);
			} else {
				brandQuantity.setVisibility(View.INVISIBLE);
				addBrand.setVisibility(View.INVISIBLE);
				removeBrand.setVisibility(View.INVISIBLE);

				brandNeed.setVisibility(View.VISIBLE);
				if (brand.getNeed() == 0) {
					brandNeed.setText(R.string.add_brand_notNeeded);
					need.setVisibility(View.INVISIBLE);
					notNeed.setVisibility(View.VISIBLE);
				} else {
					brandNeed.setText(R.string.add_brand_needed);
					need.setVisibility(View.VISIBLE);
					notNeed.setVisibility(View.INVISIBLE);
				}
			}

			if (brand.getFavourite() == 1) {
				brandFavorite.setChecked(true);
			} else {
				brandFavorite.setChecked(false);
			}
		} else {
			if (codeBar != null && !codeBar.isEmpty()) {
				brandBarCode.setText(codeBar);
			}

			if (item.getType() == 0) {
				brandNeed.setVisibility(View.INVISIBLE);
				need.setVisibility(View.INVISIBLE);
				notNeed.setVisibility(View.INVISIBLE);

				brandQuantity.setVisibility(View.VISIBLE);
				addBrand.setVisibility(View.VISIBLE);
				removeBrand.setVisibility(View.VISIBLE);
			} else {
				brandQuantity.setVisibility(View.INVISIBLE);
				addBrand.setVisibility(View.INVISIBLE);
				removeBrand.setVisibility(View.INVISIBLE);

				brandNeed.setVisibility(View.VISIBLE);
				// DEFAULT VALUE: NEED
				brandNeed.setText(R.string.add_brand_notNeeded);
				need.setVisibility(View.INVISIBLE);
				notNeed.setVisibility(View.VISIBLE);
			}
		}

		brandScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentIntegrator scanIntegrator = new IntentIntegrator(activity);
				scanIntegrator.initiateScan();
			}
		});

		need.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DBShoppingList dbSL = new DBShoppingList(activity.getBaseContext());
				DBBuyedList dbBL = new DBBuyedList(activity.getBaseContext());
				if (brand == null || (!dbSL.existsItem(item.getId()) && !dbBL.existsItem(item.getId()))) {
					if (need.getVisibility() == View.VISIBLE) {
						need.setVisibility(View.INVISIBLE);
						notNeed.setVisibility(View.VISIBLE);
						brandNeed.setText(R.string.add_brand_notNeeded);
					}
				} else {
					Toast toast = Toast.makeText(activity, R.string.error_needto_finish, Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});

		notNeed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DBShoppingList dbSL = new DBShoppingList(activity.getBaseContext());
				DBBuyedList dbBL = new DBBuyedList(activity.getBaseContext());
				if (brand == null || (!dbSL.existsItem(item.getId()) && !dbBL.existsItem(item.getId()))) {
					if (notNeed.getVisibility() == View.VISIBLE) {
						need.setVisibility(View.VISIBLE);
						notNeed.setVisibility(View.INVISIBLE);
						brandNeed.setText(R.string.add_brand_needed);
					}
				} else {
					Toast toast = Toast.makeText(activity, R.string.error_needto_finish, Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});

		addBrand.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DBShoppingList dbSL = new DBShoppingList(activity.getBaseContext());
				DBBuyedList dbBL = new DBBuyedList(activity.getBaseContext());
				if (brand == null || (!dbSL.existsItem(item.getId()) && !dbBL.existsItem(item.getId()))) {
					int aux = Integer.parseInt(brandQuantity.getText().toString()) + 1;
					brandQuantity.setText(String.valueOf(aux));
					itemQuantity++;
				} else {
					Toast toast = Toast.makeText(activity, R.string.error_needto_finish, Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});

		removeBrand.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DBShoppingList dbSL = new DBShoppingList(activity.getBaseContext());
				DBBuyedList dbBL = new DBBuyedList(activity.getBaseContext());
				if (brand == null || (!dbSL.existsItem(item.getId()) && !dbBL.existsItem(item.getId()))) {
					int aux = Integer.parseInt(brandQuantity.getText().toString()) - 1;
					// quantity can't be negative
					if (aux < 0)
						aux = 0;
					else
						itemQuantity--;
					brandQuantity.setText(String.valueOf(aux));
				} else {
					Toast toast = Toast.makeText(activity, R.string.error_needto_finish, Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult.getContents() != null) {
			String scanContent = scanningResult.getContents();
			brandBarCode.setText(scanContent);
		}
	}

	private void insert() {
		DBBrand db = new DBBrand(this);
		DBItem dbItem = new DBItem(this);

		populateBrand();
		String error = validation(db);

		if (error.isEmpty()) {
			brand.setId(db.insert(brand));

			if (item.getType() == 0)
				item.setQuantity(itemQuantity);
			else
				item.setType(db.checkBrandsNeed(item.getId()));
			dbItem.update(item);

			startActivity(new Intent(BrandEditActivity.this, TabsActivity.class));
			finish();
		} else {
			Toast toast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private void update() {
		DBBrand db = new DBBrand(this);
		DBItem dbItem = new DBItem(this);

		populateBrand();
		String error = validation(db);

		if (error.isEmpty()) {
			db.update(brand);

			if (item.getType() == 0)
				item.setQuantity(itemQuantity);
			else
				item.setType(db.checkBrandsNeed(item.getId()));
			dbItem.update(item);

			startActivity(new Intent(BrandEditActivity.this, TabsActivity.class));
			finish();
		} else {
			Toast toast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private String validation(DBBrand db) {
		String error = "";

		if (brandName.getText().toString().isEmpty()) {
			error = getString(R.string.error_edit);
		} else if (!brandBarCode.getText().toString().isEmpty() &&
				db.barCodeExists(brandBarCode.getText().toString(), brand.getId())[0] != -1) {
			error = getString(R.string.error_brand_barcode) + " " + brandBarCode.getText().toString();
		} else if (!brandPrice.getText().toString().isEmpty() && Double.valueOf(brandPrice.getText().toString()) < 0) {
			error = getString(R.string.error_brand_price_neg);
		}

		return error;
	}

	private void populateBrand() {
		if (brand == null) {
			brand = new Brand();
		}

		brand.setName(brandName.getText().toString());
		brand.setDescription(brandDescription.getText().toString());
		brand.setBarCode(brandBarCode.getText().toString());

		if (brandPrice.getText().toString().isEmpty()) {
			brand.setPrice(0);
		} else {
			brand.setPrice(Double.parseDouble(brandPrice.getText().toString()));
		}

		if (item.getType() == 0) {
			if (brandQuantity.getText().toString().isEmpty()) {
				brand.setQuantity(0);
			} else {
				brand.setQuantity(Integer.parseInt(brandQuantity.getText().toString()));
			}
		} else {
			if (brandNeed.getText().toString().equals(getString(R.string.add_brand_needed))) {
				brand.setNeed(1);
			} else {
				brand.setNeed(0);
			}
		}

		if (item.getId() != 0) {
			brand.setItemId(item.getId());
		}

		if (brandFavorite.isChecked()) {
			brand.setFavourite(1);
		} else {
			brand.setFavourite(0);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
//		case R.id.save_brand:
//			if (brand == null)
//				insert();
//			else
//				update();
//			break;
		case android.R.id.home:
			onBackPressed();
			break;
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(BrandEditActivity.this, TabsActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void saveBrand(View view) {
		if (brand == null)
			insert();
		else
			update();
	}
 }
