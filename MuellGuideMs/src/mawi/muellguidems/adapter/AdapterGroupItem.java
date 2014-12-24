package mawi.muellguidems.adapter;

import java.util.ArrayList;

public class AdapterGroupItem {
	private String id;
	private String bezeichnung;
	private int image;

	ArrayList<AdapterChildItem> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public ArrayList<AdapterChildItem> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<AdapterChildItem> children) {
		this.children = children;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}
}
