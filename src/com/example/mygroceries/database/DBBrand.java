package com.example.mygroceries.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBBrand extends DBManager<Brand> {

	public DBBrand(Context context) {
		super(context);
	}

	@Override
	public long insert(Brand brand) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(BRAND_NAME, brand.getName());
		values.put(BRAND_DESCRIPTION, brand.getDescription());
		values.put(BRAND_BARCODE, brand.getBarCode());
		values.put(BRAND_PRICE, brand.getPrice());
		values.put(BRAND_QUANTITY, brand.getQuantity());
		values.put(BRAND_NEED, brand.getNeed());
		values.put(BRAND_FAVOURITE, brand.getFavourite());
		values.put(BRAND_ITEM_ID, brand.getItemId());

		long brand_id = db.insertOrThrow(TABLE_BRAND, null, values);

		closeDB();

		return brand_id;
	}

	@Override
	public void delete(long id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_BRAND, ID + " = ?", new String[] { String.valueOf(id) });

		closeDB();
	}

	@Override
	public void update(Brand brand) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(BRAND_NAME, brand.getName());
		values.put(BRAND_DESCRIPTION, brand.getDescription());
		values.put(BRAND_BARCODE, brand.getBarCode());
		values.put(BRAND_PRICE, brand.getPrice());
		values.put(BRAND_QUANTITY, brand.getQuantity());
		values.put(BRAND_NEED, brand.getNeed());
		values.put(BRAND_FAVOURITE, brand.getFavourite());
		values.put(BRAND_ITEM_ID, brand.getItemId());

		db.update(TABLE_BRAND, values, ID + " = ?", new String[] { String.valueOf(brand.getId()) });

		closeDB();
	}

	@Override
	public ArrayList<Brand> getAll() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Brand> brands = new ArrayList<Brand>();

		String selectQuery = "SELECT P.* FROM " + TABLE_BRAND + " P ";

		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				Brand brand = new Brand();
				brand.setId(c.getLong(c.getColumnIndex(ID)));
				brand.setName(c.getString(c.getColumnIndex(BRAND_NAME)));
				brand.setDescription(c.getString(c.getColumnIndex(BRAND_DESCRIPTION)));
				brand.setBarCode("" + c.getString(c.getColumnIndex(BRAND_BARCODE)));
				brand.setPrice(c.getDouble(c.getColumnIndex(BRAND_PRICE)));
				brand.setQuantity(c.getInt(c.getColumnIndex(BRAND_QUANTITY)));
				brand.setNeed(c.getInt(c.getColumnIndex(BRAND_NEED)));
				brand.setFavourite(c.getInt(c.getColumnIndex(BRAND_FAVOURITE)));
				brand.setItemId(c.getLong(c.getColumnIndex(BRAND_ITEM_ID)));

				brands.add(brand);
			} while (c.moveToNext());
		}

		closeDB();

		return brands;
	}

	public ArrayList<Brand> getBrandWithItemId(long itemId) {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Brand> brands = new ArrayList<Brand>();

		String selectQuery = "SELECT P.* FROM " + TABLE_BRAND + " P WHERE " + BRAND_ITEM_ID + " = " + itemId;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				Brand brand = new Brand();
				brand.setId(c.getLong(c.getColumnIndex(ID)));
				brand.setName(c.getString(c.getColumnIndex(BRAND_NAME)));
				brand.setDescription(c.getString(c.getColumnIndex(BRAND_DESCRIPTION)));
				brand.setBarCode("" + c.getString(c.getColumnIndex(BRAND_BARCODE)));
				brand.setPrice(c.getDouble(c.getColumnIndex(BRAND_PRICE)));
				brand.setQuantity(c.getInt(c.getColumnIndex(BRAND_QUANTITY)));
				brand.setNeed(c.getInt(c.getColumnIndex(BRAND_NEED)));
				brand.setFavourite(c.getInt(c.getColumnIndex(BRAND_FAVOURITE)));
				brand.setItemId(c.getLong(c.getColumnIndex(BRAND_ITEM_ID)));

				brands.add(brand);
			} while (c.moveToNext());
		}

		closeDB();

		return brands;
	}

	public int[] barCodeExists(String barcode, long id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		int typeAndQuantity[] = new int[2];

		String selectQuery = "SELECT ITEM." + ITEM_TYPE + ", BRAND." + BRAND_QUANTITY + " FROM " + TABLE_BRAND
				+ " BRAND, " + TABLE_ITEM + " ITEM WHERE ITEM." + ID + " = BRAND." + BRAND_ITEM_ID + " AND BRAND."
				+ BRAND_BARCODE + " = '" + barcode + "'";

//		String selectQuery = "SELECT " + ITEM_TYPE + ", " + BRAND_QUANTITY 
//				+ " FROM " + TABLE_BRAND + " AS 'B'"
//				+ " JOIN " + TABLE_ITEM + "AS 'I' ON B." + BRAND_ITEM_ID + " = " + ID
//				+ " WHERE " + BRAND_BARCODE + " = '" + barcode + "'";
				
		if (id != -1) {
			selectQuery += " AND BRAND." + ID + " <> " + id;
		}

		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c != null) {
			c.moveToFirst();
		}

		if (c.getCount() == 0) {
			typeAndQuantity[0] = -1;
			typeAndQuantity[1] = -1;
		} else {
			typeAndQuantity[0] = c.getInt(c.getColumnIndex(ITEM_TYPE));
			typeAndQuantity[1] = c.getInt(c.getColumnIndex(BRAND_QUANTITY));
		}
		
		closeDB();
		return typeAndQuantity;
	}

	public int checkBrandsNeed(long itemID) {
		SQLiteDatabase db = this.getReadableDatabase();

		// searches for brands not needed
		String selectQuery = "SELECT 1 FROM " + TABLE_BRAND + " WHERE " + BRAND_NEED + " = '0' AND "
				+ BRAND_ITEM_ID + " = '" + itemID + "'";

		Cursor c = db.rawQuery(selectQuery, null);

		// if there isn't any brand not needed, that means, we need the item
		if (c.getCount() == 0) {
			closeDB();
			// returns needed (item type 1 means needed)
			return 1;
		}

		closeDB();
		// returns not needed (item type 2 means not needed)
		return 2;
	}

	// retorna o id do item que � preciso mudar tambem a quantidade
	public boolean changeQuantity(String code, int op, int num) {
		Brand brand = getBrandByCodeBar(code);

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		// 0 = del | 1 = add
		if (op == 0) {
			if (brand.getQuantity() - num >= 0) {
				values.put(BRAND_QUANTITY, brand.getQuantity() - num);
			} else {
				closeDB();
				return false;
			}
		} else {
			values.put(BRAND_QUANTITY, brand.getQuantity() + num);
		}

		values.put(BRAND_NAME, brand.getName());
		values.put(BRAND_DESCRIPTION, brand.getDescription());
		values.put(BRAND_BARCODE, brand.getBarCode());
		values.put(BRAND_PRICE, brand.getPrice());
		values.put(BRAND_FAVOURITE, brand.getFavourite());
		values.put(BRAND_ITEM_ID, brand.getItemId());

		db.update(TABLE_BRAND, values, ID + " = ?", new String[] { String.valueOf(brand.getId()) });

		closeDB();

		return true;
	}

	public boolean changeNeed(String code, int need) {
		Brand brand = getBrandByCodeBar(code);

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		if (need == brand.getNeed()) {
			return false;
		}

		values.put(BRAND_NEED, need);
		values.put(BRAND_NAME, brand.getName());
		values.put(BRAND_DESCRIPTION, brand.getDescription());
		values.put(BRAND_BARCODE, brand.getBarCode());
		values.put(BRAND_PRICE, brand.getPrice());
		values.put(BRAND_FAVOURITE, brand.getFavourite());
		values.put(BRAND_ITEM_ID, brand.getItemId());

		db.update(TABLE_BRAND, values, ID + " = ?", new String[] { String.valueOf(brand.getId()) });

		closeDB();

		return true;
	}

	public Brand getBrandByCodeBar(String code) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT *" + " FROM " + TABLE_BRAND + " WHERE " + BRAND_BARCODE + " = '" + code + "'";

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null) {
			c.moveToFirst();
		}

		Brand brand = new Brand();
		brand.setId(c.getLong(c.getColumnIndex(ID)));
		brand.setName(c.getString(c.getColumnIndex(BRAND_NAME)));
		brand.setDescription(c.getString(c.getColumnIndex(BRAND_DESCRIPTION)));
		brand.setBarCode("" + c.getString(c.getColumnIndex(BRAND_BARCODE)));
		brand.setPrice(c.getDouble(c.getColumnIndex(BRAND_PRICE)));
		brand.setQuantity(c.getInt(c.getColumnIndex(BRAND_QUANTITY)));
		brand.setNeed(c.getInt(c.getColumnIndex(BRAND_NEED)));
		brand.setFavourite(c.getInt(c.getColumnIndex(BRAND_FAVOURITE)));
		brand.setItemId(c.getLong(c.getColumnIndex(BRAND_ITEM_ID)));

		closeDB();

		return brand;
	}
	
	public void resetQuantityAndNeed(long id ) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(BRAND_QUANTITY, 0);
		values.put(BRAND_NEED, 1);

		db.update(TABLE_BRAND, values, BRAND_ITEM_ID + " = ?", new String[] { String.valueOf(id) });

		closeDB();
	}

	// private Brand getBrandById(long id) {
	// SQLiteDatabase db = this.getReadableDatabase();
	//
	// String selectQuery = "SELECT *" + " FROM " + TABLE_BRAND + " WHERE " + ID
	// + " = '" + id + "'";
	//
	// Cursor c = db.rawQuery(selectQuery, null);
	//
	// if (c != null) {
	// c.moveToFirst();
	// }
	//
	// Brand brand = new Brand();
	// brand.setId(c.getLong(c.getColumnIndex(ID)));
	// brand.setName(c.getString(c.getColumnIndex(BRAND_NAME)));
	// brand.setDescription(c.getString(c.getColumnIndex(BRAND_DESCRIPTION)));
	// brand.setBarCode(""+c.getString(c.getColumnIndex(BRAND_BARCODE)));
	// brand.setPrice(c.getDouble(c.getColumnIndex(BRAND_PRICE)));
	// brand.setQuantity(c.getInt(c.getColumnIndex(BRAND_QUANTITY)));
	// brand.setFavourite(c.getInt(c.getColumnIndex(BRAND_FAVOURITE)));
	// brand.setItemId(c.getLong(c.getColumnIndex(BRAND_ITEM_ID)));
	//
	// closeDB();
	//
	// return brand;
	// }
}
