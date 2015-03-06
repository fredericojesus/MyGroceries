package com.example.mygroceries.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBCategory extends DBManager<Category> {

	public DBCategory(Context context) {
		super(context);
	}

	@Override
	public long insert(Category category) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(CATEGORY_NAME, category.getName());

		long product_id = db.insert(TABLE_CATEGORY, null, values);

		closeDB();

		return product_id;
	}

	@Override
	public void delete(long id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_CATEGORY, ID + " = ?",
				new String[] { String.valueOf(id) });

		closeDB();
	}

	@Override
	public void update(Category category) {
		SQLiteDatabase dataBase = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(CATEGORY_NAME, category.getName());

		dataBase.update(TABLE_CATEGORY, values, ID + " = ?",
				new String[] { String.valueOf(category.getId()) });

		closeDB();
	}

	@Override
	public ArrayList<Category> getAll() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Category> categories = new ArrayList<Category>();

		String selectQuery = "SELECT * FROM " + TABLE_CATEGORY;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				Category category = new Category();
				category.setId(c.getInt((c.getColumnIndex(ID))));
				category.setName((c.getString(c.getColumnIndex(CATEGORY_NAME))));

				categories.add(category);
			} while (c.moveToNext());
		}

		closeDB();

		return categories;
	}
	
	public boolean dependency(long id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT 1 FROM " + TABLE_ITEM + " WHERE "
				+ ITEM_CATEGORY_ID + " = " + id;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c.getCount() != 0) {
			closeDB();
			return false;
		}
		
		closeDB();
		return true;
	}
}
