package com.example.mygroceries.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygroceries.ItemsFragment;
import com.example.mygroceries.R;
import com.example.mygroceries.TabsActivity;
import com.example.mygroceries.database.DBStorageArea;
import com.example.mygroceries.database.StorageArea;

public class StorageAreaAdapter extends ListAdapter<StorageArea> {
	private EditText storageAreaName;
	private DBStorageArea db;
	private Activity activity;

	public StorageAreaAdapter(Context context, Activity activity) {
		super(context);
		this.activity = activity;
		
		db = new DBStorageArea(context);

		ArrayList<StorageArea> areas = db.getAll();

		list = areas;

		originalList = new ArrayList<StorageArea>();
		originalList.addAll(areas);

		sortList();
	}

	@Override
	public long getItemId(int position) {
		return list.get(position).getId();
	}
	
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder holder;

		if (view == null || view.getTag() == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.category_storage_row, null);

			holder.id = (TextView) view.findViewById(R.id.categoryStorageID);
			holder.text = (TextView) view.findViewById(R.id.categoryStorageText);
			holder.deleteImage = (ImageView) view.findViewById(R.id.category_storage_delete);
			holder.editImage = (ImageView) view.findViewById(R.id.category_storage_edit);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		holder.id.setText(String.valueOf(list.get(position).getId()));
		holder.text.setText(list.get(position).getName());
		holder.text.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ItemsFragment.storageID = list.get(position).getId();
				TabsActivity.actionBar.setSelectedNavigationItem(0);
			}
		});
		
		holder.deleteImage.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				deleteDialog(position);
			}
		});

		holder.editImage.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				insertUpdateDialog(position);
			}
		});

		return view;
	}

	@Override
	protected void deleteDialog(final int position) {
		if (db.dependency(list.get(position).getId())) {

			new AlertDialog.Builder(context)
					.setTitle(R.string.alert_dialog_delete_storage_area_title)
					.setMessage(
							context.getString(R.string.alert_dialog_delete_storage_area) + " "
									+ list.get(position).getName() + "?")
					.setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							db.delete(list.get(position).getId());
							int originalPosition = originalList.indexOf(new StorageArea(list.get(position).getId()));
							list.remove(position);
							originalList.remove(originalPosition);

							notifyDataSetChanged();
						}
					}).show();
		} else {
			Toast toast = Toast.makeText(context, R.string.error_items_dependency_storage_area, Toast.LENGTH_LONG);
			toast.show();
		}
	}

	@Override
	public void insertUpdateDialog(final int position) {
		View textEntryView = inflater.inflate(R.layout.alert_dialog_cat_sto_text_entry, null);
		storageAreaName = (EditText) textEntryView.findViewById(R.id.alert_dialog_cat_sto_editText);

		String title;
		if (position != -1) {
			storageAreaName.setText(list.get(position).getName());
			title = activity.getString(R.string.alert_dialog_edit_storage_area_title);
		} else {
			title = activity.getString(R.string.alert_dialog_add_storage_area);
		}

		new AlertDialog.Builder(context).setTitle(title).setView(textEntryView)
				.setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (position == -1) {
							insert();
						} else {
							update(position);
						}
					}
				}).show();
	}

	private void insert() {
		if (storageAreaName.getText().toString().equals("")) {
			return;
		}

		StorageArea sto = new StorageArea(storageAreaName.getText().toString());
		long id = db.insert(sto);
		sto.setId(id);
		addRow(sto);

		notifyDataSetChanged();
	}

	private void update(int position) {
		if (storageAreaName.getText().toString().isEmpty()) {
			Toast toast = Toast.makeText(context, R.string.error_edit, Toast.LENGTH_SHORT);
			toast.show();
		} else if (!list.get(position).getName().equals(storageAreaName.getText().toString())) {
			StorageArea sto = new StorageArea(list.get(position).getId(), storageAreaName.getText().toString());
			db.update(sto);
			int originalPosition = originalList.indexOf(new StorageArea(list.get(position).getId()));
			list.set(position, sto);
			originalList.set(originalPosition, sto);
			sortList();
			notifyDataSetChanged();

			Toast toast = Toast.makeText(context, R.string.alert_dialog_edit_storage_area_toast, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		list.clear();
		
		if (charText.length() == 0) {
			list.addAll(originalList);
		} else {
			for (StorageArea storageArea : originalList) {
				if (storageArea.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
					list.add(storageArea);
				}
			}
		}

		notifyDataSetChanged();
	}

	@Override
	protected void addRow(StorageArea area) {
		list.add(area);
		originalList.add(area);

		sortList();
	}

	@Override
	protected void sortList() {
		Collections.sort(list, new Comparator<StorageArea>() {
			@Override
			public int compare(StorageArea sa1, StorageArea sa2) {
				return sa1.getName().compareTo(sa2.getName());
			}
		});
	}

	public class ViewHolder {
		TextView id;
		TextView text;
		ImageView deleteImage;
		ImageView editImage;
	}
}
