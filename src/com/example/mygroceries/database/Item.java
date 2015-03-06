package com.example.mygroceries.database;

import java.io.Serializable;

public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private long id;
	private String name;
	private int type; //0 - quantity ; 1 - needed ; 2 - not needed
	private int essential; //0 - not essential ; 1 - essential
	private int minimum;
	private int quantity;
	private long categoryId;
	private long storageAreaId;

	public Item() {
	};
	public Item(long id) {
		this.id = id;
	};

	public Item(String name, int type, int essential, int minimum, int quantity, long categoryId, long storageAreaId) {
		this.name = name;
		this.type = type;
		this.essential = essential;
		this.minimum = minimum;
		this.quantity = quantity;
		this.categoryId = categoryId;
		this.storageAreaId = storageAreaId;
	}

	public Item(long id, String name) {
		this.id = id;
		this.name = name;
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

	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getEssential() {
		return essential;
	}

	public void setEssential(int essential) {
		this.essential = essential;
	}
	
	public int getMinimum() {
		return minimum;
	}
	
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(long storageAreaId) {
		this.storageAreaId = storageAreaId;
	}
	
	public boolean equals(Object o) {
    	if(!(o instanceof Item))
			return false;
    	Item p = (Item) o;
		return (this.id == p.id);
    }
}
