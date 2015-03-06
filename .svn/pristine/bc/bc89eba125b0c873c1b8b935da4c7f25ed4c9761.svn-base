package com.example.mygroceries.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBStorageArea extends DBManager<StorageArea> {

	public DBStorageArea(Context context) {
		super(context);
	}

	@Override
	public long insert(StorageArea storageArea) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(STORAGE_AREA_NAME, storageArea.getName());

		long product_id = db.insert(TABLE_STORAGE_AREA, null, values);

		closeDB();

		return product_id;
	}

	@Override
	public void delete(long id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_STORAGE_AREA, ID + " = ?", new String[] { String.valueOf(id) });

		closeDB();
	}

	@Override
	public void update(StorageArea storageArea) {
		SQLiteDatabase dataBase = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(STORAGE_AREA_NAME, storageArea.getName());

		dataBase.update(TABLE_STORAGE_AREA, values, ID + " = ?", new String[] { String.valueOf(storageArea.getId()) });

		closeDB();
	}

	@Override
	public ArrayList<StorageArea> getAll() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<StorageArea> storageAreas = new ArrayList<StorageArea>();

		String selectQuery = "SELECT * FROM " + TABLE_STORAGE_AREA;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				StorageArea storageArea = new StorageArea();
				storageArea.setId(c.getInt((c.getColumnIndex(ID))));
				storageArea.setName((c.getString(c.getColumnIndex(STORAGE_AREA_NAME))));

				storageAreas.add(storageArea);
			} while (c.moveToNext());
		}

		closeDB();

		return storageAreas;
	}

	public boolean dependency(long id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT 1 FROM " + TABLE_ITEM + " WHERE " + ITEM_STORAGE_AREA_ID + " = " + id;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c.getCount() != 0) {
			closeDB();
			return false;
		}

		closeDB();
		return true;
	}
}
