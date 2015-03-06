package com.example.mygroceries.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBBuyedList extends DBManager<BuyedList> {

	public DBBuyedList(Context context) {
		super(context);
	}

	@Override
	public long insert(BuyedList buyedList) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(BUYED_LIST_ITEM_ID, buyedList.getItemId());

		long buyedList_id = db.insert(TABLE_BUYED_LIST, null, values);

		closeDB();

		return buyedList_id;
	}

	@Override
	public void delete(long id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_BUYED_LIST, BUYED_LIST_ITEM_ID + " = ?", new String[] { String.valueOf(id) });

		closeDB();
	}

	@Override
	public void update(BuyedList buyedList) {
		SQLiteDatabase dataBase = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(BUYED_LIST_ITEM_ID, buyedList.getItemId());

		dataBase.update(TABLE_BUYED_LIST, values, ID + " = ?", new String[] { String.valueOf(buyedList.getId()) });

		closeDB();
	}
	
	@Override
	public ArrayList<BuyedList> getAll() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<BuyedList> buyeds = new ArrayList<BuyedList>();

		String selectQuery = "SELECT * FROM " + TABLE_BUYED_LIST;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				BuyedList buyedList = new BuyedList();
				buyedList.setId(c.getInt((c.getColumnIndex(ID))));
				buyedList.setItemId(c.getLong(c.getColumnIndex(BUYED_LIST_ITEM_ID)));

				buyeds.add(buyedList);
			} while (c.moveToNext());
		}

		closeDB();

		return buyeds;
	}
	
	public ArrayList<Item> getAllItems() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Item> itemsBuyed = new ArrayList<Item>();

		String selectQuery = "SELECT I.* FROM " + TABLE_ITEM + " I, " + TABLE_BUYED_LIST
				+ " WHERE I." + ID + "=" + BUYED_LIST_ITEM_ID;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				Item item = new Item();
				item.setId(c.getLong(c.getColumnIndex(ID)));
				item.setName(c.getString(c.getColumnIndex(ITEM_NAME)));
				item.setType(c.getInt(c.getColumnIndex(ITEM_TYPE)));
				item.setEssential(c.getInt(c.getColumnIndex(ITEM_ESSENTIAL)));
				item.setMinimum(c.getInt(c.getColumnIndex(ITEM_MINIMUM)));
				item.setQuantity(c.getInt(c.getColumnIndex(ITEM_QUANTITY)));
				item.setCategoryId(c.getLong(c.getColumnIndex(ITEM_CATEGORY_ID)));
				item.setStorageAreaId(c.getLong(c.getColumnIndex(ITEM_STORAGE_AREA_ID)));

				itemsBuyed.add(item);
			} while (c.moveToNext());
		}

		closeDB();

		return itemsBuyed;
	}

	public void deleteAll() {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_BUYED_LIST, null, null);

		closeDB();
	}
	
	public boolean emptyBuyedList() {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT 1 FROM " + TABLE_BUYED_LIST;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c.getCount() != 0) {
			closeDB();
			return false;
		}
		
		closeDB();
		return true;
	}
	
//	public boolean emptyShoppingList() {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		String selectQuery = "SELECT 1 FROM " + TABLE_ITEM + " I WHERE " +
//				" I." + ITEM_ESSENTIAL + " = " + "1 " +
//				"AND (I." + ITEM_QUANTITY + " < " + ITEM_MINIMUM 
//				+ " OR I." + ITEM_TYPE + " = 1) "
//				+ "AND NOT EXISTS(SELECT 1 FROM " + TABLE_BUYED_LIST + " B " 
//				+ "WHERE B." + BUYED_LIST_ITEM_ID + " = I." + ID + ")";
//
//		Cursor c = db.rawQuery(selectQuery, null);
//
//		if (c.getCount() != 0) {
//			closeDB();
//			return false;
//		}
//		
//		closeDB();
//		return true;
//	}
//	
//	public boolean emptyShoppingList(long id) {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		String selectQuery = "SELECT 1 FROM " + TABLE_ITEM + " I, " + TABLE_BRAND + " B "
//				+ "WHERE I." + ID + " = B." + BRAND_ITEM_ID + " "
//				+ "AND I." + ITEM_ESSENTIAL + " = " + "1 "
//				+ "AND (I." + ITEM_QUANTITY + " < " + ITEM_MINIMUM + " "
//				+ "OR I." + ITEM_TYPE + " = 1) "
//				+ "AND B." + ID + " = " + id + " "
//				+ "OR EXISTS(SELECT 1 FROM " + TABLE_BUYED_LIST + " B " 
//				+ "WHERE B." + BUYED_LIST_ITEM_ID + " = I." + ID + " "
//				+ "AND B." + ID + " = " + id + ")";
//
//		Cursor c = db.rawQuery(selectQuery, null);
//
//		if (c.getCount() != 0) {
//			closeDB();
//			return false;
//		}
//		
//		closeDB();
//		return true;
//	}
	
	public boolean existsItem(long id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT 1 FROM " + TABLE_BUYED_LIST + " "
				+ "WHERE " + BUYED_LIST_ITEM_ID + " = " + id;
		
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.getCount() != 0) {
			closeDB();
			return true;
		}
		
		closeDB();
		return false;
	}
}
