package com.example.mygroceries.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygroceries.R;
import com.example.mygroceries.database.Brand;
import com.example.mygroceries.database.BuyedList;
import com.example.mygroceries.database.DBBrand;
import com.example.mygroceries.database.DBBuyedList;
import com.example.mygroceries.database.DBItem;
import com.example.mygroceries.database.DBShoppingList;
import com.example.mygroceries.database.Item;

public class TobuyListAdapter extends BaseExpandableListAdapter {
	private Activity activity;
	private Map<Item, ArrayList<Brand>> collections;
	private Map<Item, ArrayList<Brand>> originalCollections;
	private List<Item> list;
	private List<Item> originalList;
	// private TextView itemId;
	// private EditText itemName;
	private Context context;

	public TobuyListAdapter(Context context, Activity activity, ArrayList<Item> list,
			Map<Item, ArrayList<Brand>> collections) {
		this.context = context;
		this.activity = activity;
		this.collections = collections;
		this.list = list;

		originalList = new ArrayList<Item>();
		originalList.addAll(list);

		originalCollections = new HashMap<Item, ArrayList<Brand>>();
		originalCollections.putAll(collections);
	}

	@Override
	public Brand getChild(int groupPosition, int childPosition) {
		return collections.get(list.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		final Brand brand = getChild(groupPosition, childPosition);
		final Item item = getGroup(groupPosition);

		LayoutInflater inflater = activity.getLayoutInflater();

		if (view == null) {
			view = inflater.inflate(R.layout.child_shopping_list, null);
		}

		TextView brandName = (TextView) view.findViewById(R.id.brand_text_sl);
		brandName.setText(brand.getName());

		final TextView brandSubText = (TextView) view.findViewById(R.id.brand_sub_text_sl);
		if (item.getType() == 0) {
			brandSubText.setText("" + brand.getQuantity());
		} else {
			if (brand.getNeed() == 0) {
				brandSubText.setText(R.string.add_brand_notNeeded);
			} else {
				brandSubText.setText(R.string.add_brand_needed);
			}
		}

		final DBBrand dbBrand = new DBBrand(context);
		final DBItem dbItem = new DBItem(context);
		final ImageView rightRow = (ImageView) view.findViewById(R.id.brand_right_sl);
		final ImageView wrongRow = (ImageView) view.findViewById(R.id.brand_wrong_sl);
		rightRow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (rightRow.getVisibility() == View.VISIBLE) {
					rightRow.setVisibility(View.INVISIBLE);
					wrongRow.setVisibility(View.VISIBLE);

					brandSubText.setText(R.string.add_brand_needed);
					brand.setNeed(1);
					dbBrand.update(brand);

					item.setType(dbBrand.checkBrandsNeed(item.getId()));
					dbItem.update(item);

					notifyDataSetChanged();
				}
			}
		});
		wrongRow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (wrongRow.getVisibility() == View.VISIBLE) {
					wrongRow.setVisibility(View.INVISIBLE);
					rightRow.setVisibility(View.VISIBLE);

					brandSubText.setText(R.string.add_brand_notNeeded);
					brand.setNeed(0);
					dbBrand.update(brand);

					item.setType(dbBrand.checkBrandsNeed(item.getId()));
					dbItem.update(item);

					notifyDataSetChanged();
				}
			}
		});

		ImageView addRow = (ImageView) view.findViewById(R.id.brand_add_sl);
		addRow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				brand.setQuantity(brand.getQuantity() + 1);
				dbBrand.update(brand);

				brandSubText.setText("" + brand.getQuantity());

				item.setQuantity(item.getQuantity() + 1);
				dbItem.update(item);

				notifyDataSetChanged();
			}
		});

		ImageView minusRow = (ImageView) view.findViewById(R.id.brand_minus_sl);
		minusRow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (brand.getQuantity() > 0) {
					brand.setQuantity(brand.getQuantity() - 1);
					dbBrand.update(brand);

					brandSubText.setText("" + brand.getQuantity());

					item.setQuantity(item.getQuantity() - 1);
					dbItem.update(item);

					notifyDataSetChanged();
				}
			}
		});

		if (item.getType() == 0) {
			addRow.setVisibility(View.VISIBLE);
			minusRow.setVisibility(View.VISIBLE);
			rightRow.setVisibility(View.INVISIBLE);
			wrongRow.setVisibility(View.INVISIBLE);
		} else {
			addRow.setVisibility(View.INVISIBLE);
			minusRow.setVisibility(View.INVISIBLE);
			if (brand.getNeed() == 1) {
				rightRow.setVisibility(View.INVISIBLE);
				wrongRow.setVisibility(View.VISIBLE);
			} else {
				rightRow.setVisibility(View.VISIBLE);
				wrongRow.setVisibility(View.INVISIBLE);
			}
		}

		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (collections != null && list != null && list.get(groupPosition) != null
				&& collections.get(list.get(groupPosition)) != null) {
			return collections.get(list.get(groupPosition)).size();
		}

		return 0;
	}

	@Override
	public Item getGroup(int groupPosition) {
		return list.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return list.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(final int position, boolean isExpanded, View view, ViewGroup parent) {
		final Item item = getGroup(position);

		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.parent_shopping_list, null);
		}

		TextView itemNameTV = (TextView) view.findViewById(R.id.itemText_sl);
		itemNameTV.setText(item.getName());

		TextView itemQuantityNeedTV = (TextView) view.findViewById(R.id.quantity_need_Text_sl);
		if (item.getType() == 0)
			itemQuantityNeedTV.setText("" + item.getQuantity());
		else {
			if (item.getType() == 1)
				itemQuantityNeedTV.setText(R.string.add_brand_needed);
			else
				itemQuantityNeedTV.setText(R.string.add_brand_notNeeded);
		}

		ImageView thumbsUpImage = (ImageView) view.findViewById(R.id.item_buyed_sl);
		thumbsUpImage.setVisibility(View.VISIBLE);
		thumbsUpImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TextView shoppingButton = (TextView) activity.findViewById(R.id.button_shopping_textView);
				if (shoppingButton.getText().toString().equals(activity.getString(R.string.shopping_list_finish))) {
					DBBuyedList dbBL = new DBBuyedList(context);
					dbBL.insert(new BuyedList(item.getId()));
					DBShoppingList dbSL = new DBShoppingList(context);
					dbSL.deleteItem(item.getId());

					int originalPosition = originalList.indexOf(new Item(list.get(position).getId()));
					list.remove(position);
					originalList.remove(originalPosition);

					notifyDataSetChanged();
				} else {
					Toast toast = Toast.makeText(context, R.string.erro_needto_start, Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});

		sortList();

		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	private void sortList() {
		Collections.sort(list, new Comparator<Item>() {
			@Override
			public int compare(Item i1, Item i2) {
				return i1.getName().compareTo(i2.getName());
			}
		});
	}

	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		list.clear();
		collections.clear();
		if (charText.length() == 0) {
			list.addAll(originalList);
			collections.putAll(originalCollections);
		} else {
			for (Item item : originalList) {
				ArrayList<Brand> brand = originalCollections.get(item);
				ArrayList<Brand> array = new ArrayList<Brand>();

				for (Brand b : brand) {
					if (b.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
						array.add(b);
					}
				}

				if (!array.isEmpty() || item.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
					collections.put(item, array);
					list.add(item);
				}
			}
		}

		notifyDataSetChanged();
	}
}