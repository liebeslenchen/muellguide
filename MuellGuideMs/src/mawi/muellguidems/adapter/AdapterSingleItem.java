package mawi.muellguidems.adapter;

public class AdapterSingleItem {
	private String id;
	private String bezeichnung;
	private String untertitel;
	private int image;
	private String plz;

	public AdapterSingleItem(String id, String bezeichnung, String untertitel,
			int image) {
		this.id = id;
		this.bezeichnung = bezeichnung;
		this.untertitel = untertitel;
		this.image = image;
	}

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

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getUntertitel() {
		return untertitel;
	}

	public void setUntertitel(String untertitel) {
		this.untertitel = untertitel;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}
}
