package mawi.muellguidems.util;

public enum StringEnum {
	ALTGLAS("Altglas"), ELEKTROKLEINGERAETE("Elektrokleingeräte"), RECYCLINGHOF(
			"Recyclinghof"), IMMER_OFFEN("immer geöffnet"), RESTMUELL(
			"Restmüll"), BIOTONNE("Biotonne"), PAPIERMUELL("Papiermüll"), GELBER_SACK(
			"Gelber Sack"), ALTKLEIDER("Altkleider"), HEUTE_NICHT_OFFEN(
			"heute nicht geöffnet");

	private final String description;

	private StringEnum(String description) {
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
