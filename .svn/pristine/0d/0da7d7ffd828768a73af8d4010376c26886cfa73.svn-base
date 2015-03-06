package com.example.mygroceries.database;

import java.io.Serializable;

public class Brand implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String description;
	private String barCode;
	private double price;
	private int quantity;
	private int need; // 0 - not needed(tenho); 1 - needed(não tenho);
	private int favourite; // 0 - not favourite; 1 - favourite
	private long itemId;

	public Brand() {
	};

	public Brand(String name, String description, String barCode, double price, int quantity, int need, int favourite) {
		this.name = name;
		this.description = description;
		this.barCode = barCode;
		this.price = price;
		this.quantity = quantity;
		this.need = need;
		this.favourite = favourite;
	}

	public Brand(long id, String name, String description, String barCode, double price, int quantity, int need,
			int favourite) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.barCode = barCode;
		this.price = price;
		this.quantity = quantity;
		this.need = need;
		this.favourite = favourite;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getNeed() {
		return need;
	}

	public void setNeed(int need) {
		this.need = need;
	}

	public int getFavourite() {
		return favourite;
	}

	public void setFavourite(int favourite) {
		this.favourite = favourite;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
}
