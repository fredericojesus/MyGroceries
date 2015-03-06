package com.example.mygroceries.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import com.example.mygroceries.R;

public class Dialogs {
	private Context context;
	private boolean button = true;
	
	
	public Dialogs(Context context){
		this.context = context;
	}
	
	public void showDialog(String message){
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public void confirmationDialog(Activity activity, View view, String title, String buttonCancel, String ButtonOk){
		new AlertDialog.Builder(activity)
    	.setTitle(title)
    	.setView(view)
    	.setNegativeButton(getButtonCancel(buttonCancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				handleButtonClick(1);
				button = false;
			}
		})
    	.setPositiveButton(getButtonOk(ButtonOk), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				handleButtonClick(2);
				button = true;
			}
		}).show();
	}
	
	private String getButtonOk(String nameButton){
		String name;
		
		if (nameButton.isEmpty()){
			name = context.getString(R.string.alert_dialog_ok);
		} else {
			name = nameButton;
		}
		
		return name;
	}
	
	private String getButtonCancel(String nameButton){
		String name;
		
		if (nameButton.isEmpty()){
			name = context.getString(R.string.alert_dialog_cancel);
		} else {
			name = nameButton;
		}
		
		return name;
	}
	
	public boolean getOption(){
		return button;
	}
}
