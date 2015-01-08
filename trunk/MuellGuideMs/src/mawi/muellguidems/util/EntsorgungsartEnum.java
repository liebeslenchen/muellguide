package mawi.muellguidems.util;

public enum EntsorgungsartEnum {

//	ELEKTOKLEINGERAET, ALTGLAS, ALTKLEIDER, RECYCLINGHOF;
	
	
	ALTGLAS("Altglas"), ELEKTROKLEINGERAETE("Elektrokleingeräte"), RECYCLINGHOF(
			"Recyclinghof"), IMMER_OFFEN("immer geöffnet"), RESTMUELL(
			"Restmüll"), BIOTONNE("Biotonne"), PAPIERMUELL("Papiermüll"), GELBER_SACK(
			"Gelber Sack"), ALTKLEIDER("Altkleider");

	private final String description;

	private EntsorgungsartEnum(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return description;
	}

}
