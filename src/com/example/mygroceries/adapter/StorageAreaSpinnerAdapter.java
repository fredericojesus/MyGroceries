package com.example.mygroceries.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mygroceries.R;
import com.example.mygroceries.database.StorageArea;

public class StorageAreaSpinnerAdapter extends BaseAdapter {
	private ArrayList<StorageArea> list;
	private Activity activity;
	
	public StorageAreaSpinnerAdapter(Activity activity, ArrayList<StorageArea> list) {
		this.activity = activity;
		this.list = list;	
		
		sortList();
		addRow();
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
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
			LayoutInflater inflater = activity.getLayoutInflater();
			view = inflater.inflate(R.layout.spinner_layout, null);
			
			holder.id = (TextView) view.findViewById(R.id.spinnerId);
			holder.text = (TextView) view.findViewById(R.id.spinnerText);
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.id.setText(String.valueOf(list.get(position).getId()));	
		holder.text.setText(list.get(position).getName());
			
		return view;
	}

	private void sortList() {
		Collections.sort(list, new Comparator<StorageArea>() {
			@Override
			public int compare(StorageArea sa1, StorageArea sa2) {
	            return sa1.getName().compareTo(sa2.getName());
	        }

		});
	}
	
	private void addRow(){
		list.add(0, new StorageArea());
	}
	
	public int getPosition(long id){
		int position = 0;
		
		for(int i = 0; i < list.size(); i++){
			if (list.get(i).getId() == id){
				position = i;
				break;
			}
		}
		
		return position;
	}
	
	public class ViewHolder {
		TextView id;
		TextView text;
	}
	
}
