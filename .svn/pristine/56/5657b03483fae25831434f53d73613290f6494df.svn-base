package com.example.mygroceries.database;

public class StorageArea {
	private long id;
	private String name;

	public StorageArea() {
	};

	public StorageArea(long id) {
		this.id = id;
	};
	
	public StorageArea(String name) {
		this.name = name;
	};

	public StorageArea(long id, String name) {
		this.id = id;
		this.name = name;
	};

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
	
	public boolean equals(Object o) {
    	if(!(o instanceof StorageArea))
			return false;
		StorageArea p = (StorageArea) o;
		return (this.id == p.id);
    }
}
