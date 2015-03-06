package com.example.mygroceries;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygroceries.adapter.CategorySpinnerAdapter;
import com.example.mygroceries.adapter.ItemAdapter;
import com.example.mygroceries.adapter.StorageAreaSpinnerAdapter;
import com.example.mygroceries.database.Brand;
import com.example.mygroceries.database.Category;
import com.example.mygroceries.database.DBBrand;
import com.example.mygroceries.database.DBCategory;
import com.example.mygroceries.database.DBItem;
import com.example.mygroceries.database.DBStorageArea;
import com.example.mygroceries.database.Item;
import com.example.mygroceries.database.StorageArea;
import com.example.mygroceries.zxing.IntentIntegrator;

public class ItemsFragment extends Fragment {
	public static long categoryID = -1;
	public static long storageID = -1;

	private ArrayList<Item> parentList;
	private ArrayList<Brand> childList;
	private Map<Item, ArrayList<Brand>> collections;
	private ExpandableListView expandableListView;
	private int lastExpandedPosition = -1;
	private ItemAdapter adapter;
	private View rootView;
	private EditText itemName;
	private RadioButton radioButtonQuantity;
	private RadioButton radioButtonNeed;
	private Spinner spinnerCategory;
	private Spinner spinnerStorageArea;
	private CheckBox checkBoxEssential;
	private TextView textMinimum;
	private EditText editMinimum;
	private EditText editSearch;
	private LayoutInflater layoutInflater;
	private Activity activity;

	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
		activity = this.getActivity();
		this.layoutInflater = layoutInflater;
		rootView = layoutInflater.inflate(R.layout.fragment_items, container, false);
		setHasOptionsMenu(true);

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.stock_menu, menu);
	}

	@Override
	public void setMenuVisibility(final boolean visible) {
		super.setMenuVisibility(visible);
		
		if (parentList != null) {
			if (visible) {
				parentList.clear();

				if (categoryID == -1 && storageID == -1) {
					createParentList();
				} else if (categoryID != -1) {
					createParentListFilterCategory();
				} else if (storageID != -1) {
					createParentListFilterStorage();
				}

				createChildList();

				adapter = new ItemAdapter(rootView.getContext(), this.getActivity(), parentList, collections);

				expandableListView.setAdapter(adapter);
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (categoryID == -1 && storageID == -1) {
			createParentList();
		} else if (categoryID != -1) {
			createParentListFilterCategory();
		} else if (storageID != -1) {
			createParentListFilterStorage();
		}

		createChildList();

		adapter = new ItemAdapter(rootView.getContext(), this.getActivity(), parentList, collections);

		expandableListView = (ExpandableListView) this.getView().findViewById(R.id.items_list);

		expandableListView.setAdapter(adapter);

		expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			public void onGroupExpand(int groupPosition) {
				if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
					expandableListView.collapseGroup(lastExpandedPosition);
				}
				lastExpandedPosition = groupPosition;

			}
		});

		expandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				return true;
			}
		});

		expandableListView.setIndicatorBounds(10, 30);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.search_menu:
			search(item);
			break;
		case R.id.add_menu:
			add(item);
			break;
		case R.id.codebar_menu:
			IntentIntegrator scanIntegrator = new IntentIntegrator(this.getActivity());
			scanIntegrator.initiateScan();
			break;
		case android.R.id.home:
			this.getActivity().onBackPressed();
			break;
		}
		return true;
	}

	private void search(MenuItem item) {
		editSearch = (EditText) item.getActionView();
		editSearch.addTextChangedListener(textWatcher);

		item.setOnActionExpandListener(new OnActionExpandListener() {

			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				editSearch.setText("");
				editSearch.requestFocus();

				InputMethodManager imm = (InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
				return true;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				editSearch.clearFocus();

				return true;
			}
		});
	}

	private void add(MenuItem item) {
		View alertView = layoutInflater.inflate(R.layout.alert_dialog_add_item, null);
		itemName = (EditText) alertView.findViewById(R.id.alert_dialog_item_text);
		radioButtonQuantity = (RadioButton) alertView.findViewById(R.id.radio_quantity);
		radioButtonQuantity.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				if(checkBoxEssential.isChecked()) {
					textMinimum.setVisibility(View.VISIBLE);
					editMinimum.setVisibility(View.VISIBLE);
				} else {
					textMinimum.setVisibility(View.GONE);
					editMinimum.setVisibility(View.GONE);
				}
			}
		});
		radioButtonNeed = (RadioButton) alertView.findViewById(R.id.radio_need);
		radioButtonNeed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				textMinimum.setVisibility(View.GONE);
				editMinimum.setVisibility(View.GONE);
			}
		});
		
		textMinimum = (TextView) alertView.findViewById(R.id.alert_dialog_item_mininum);
		editMinimum = (EditText) alertView.findViewById(R.id.alert_dialog_item_mininum_edit);
		checkBoxEssential = (CheckBox) alertView.findViewById(R.id.checkEssential);
		checkBoxEssential.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkBoxEssential.isChecked() && radioButtonQuantity.isChecked()) {
					textMinimum.setVisibility(View.VISIBLE);
					editMinimum.setVisibility(View.VISIBLE);
				} else {
					textMinimum.setVisibility(View.GONE);
					editMinimum.setVisibility(View.GONE);
				}
			}
		});

		spinnerCategory = (Spinner) alertView.findViewById(R.id.category_spinner);
		spinnerStorageArea = (Spinner) alertView.findViewById(R.id.storage_area_spinner);

		populateSpinnerCategory();

		populateSpinnerStorageArea();

		AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity())
				.setTitle(R.string.alert_dialog_add_item_title).setView(alertView)
				.setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Context context = rootView.getContext();
						if (itemName.getText().toString().equals("")) {
							return;
						}

						DBItem db = new DBItem(context);

						Item item = new Item();
						item.setName(itemName.getText().toString());

						if (radioButtonQuantity.isChecked()) {
							item.setType(0);
						} else {
							item.setType(1);
						}

						if (checkBoxEssential.isChecked()) {
							item.setEssential(1);

							if (editMinimum.getText().toString().isEmpty()) {
								item.setMinimum(0);
							} else {
								item.setMinimum(Integer.parseInt(editMinimum.getText().toString()));
							}
						} else {
							item.setEssential(0);
						}

						item.setCategoryId(spinnerCategory.getAdapter().getItemId(
								spinnerCategory.getSelectedItemPosition()));
						item.setStorageAreaId(spinnerStorageArea.getAdapter().getItemId(
								spinnerStorageArea.getSelectedItemPosition()));

						long id = db.insert(item);
						item.setId(id);
						adapter.addRow(item);
						adapter.notifyDataSetChanged();

						Toast toast = Toast.makeText(context, R.string.alert_dialog_add_item_toast, Toast.LENGTH_SHORT);
						toast.show();
					}
				}).create();
		alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		alertDialog.show();
	}

	private void populateSpinnerCategory() {
		DBCategory db = new DBCategory(rootView.getContext());

		ArrayList<Category> categories = db.getAll();

		CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(this.getActivity(), categories);

		spinnerCategory.setAdapter(adapter);
	}

	private void populateSpinnerStorageArea() {
		DBStorageArea db = new DBStorageArea(rootView.getContext());

		ArrayList<StorageArea> storageAreas = db.getAll();

		StorageAreaSpinnerAdapter adapter = new StorageAreaSpinnerAdapter(this.getActivity(), storageAreas);

		spinnerStorageArea.setAdapter(adapter);
	}

	private void createParentList() {
		DBItem db = new DBItem(rootView.getContext());

		parentList = db.getAll();
	}

	private void createParentListFilterCategory() {
		DBItem db = new DBItem(rootView.getContext());

		parentList = db.filterCategory(categoryID);
	}

	private void createParentListFilterStorage() {
		DBItem db = new DBItem(rootView.getContext());

		parentList = db.filterStorage(storageID);
	}

	private void createChildList() {
		collections = new LinkedHashMap<Item, ArrayList<Brand>>();

		DBBrand db = new DBBrand(rootView.getContext());

		for (Item item : parentList) {

			childList = db.getBrandWithItemId(item.getId());

			collections.put(item, childList);
		}
	}

	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());

			adapter.filter(text);
			
			if(adapter.onlyOne()){
				expandableListView.expandGroup(0);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	};
}
