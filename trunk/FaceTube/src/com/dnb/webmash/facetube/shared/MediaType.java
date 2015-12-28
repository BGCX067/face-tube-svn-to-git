package com.dnb.webmash.facetube.shared;

public enum MediaType {
	MUSIC(0), TELEVISION(1), MOVIES(2), BOOKS(3);
	private int value;
	
	MediaType(int value) {
		this.setValue(value);
	}
	@Override
	public String toString(){
		switch (this) {
		case MUSIC:
			return "music";
		case TELEVISION:
			return "television";
		case MOVIES:
			return "movies";
		case BOOKS:
			return "books";
		default:
			return null;
		}
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public static MediaType get (int type) {
		for (MediaType medType : MediaType.values()) {
			if (medType.getValue() == type) {
				return medType;
			}
		}		
		return null;
	}
}
