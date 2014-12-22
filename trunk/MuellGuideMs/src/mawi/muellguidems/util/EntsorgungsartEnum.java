package mawi.muellguidems.util;

public enum EntsorgungsartEnum {

	ELEKTOKLEINGERAET, ALTGLAS, ALTKLEIDER, RECYCLINGHOF;

	public static EntsorgungsartEnum getEnumEntsorgungsart(String bezeichnung) {
		if (bezeichnung == "Elektrokleingerät") {
			return EntsorgungsartEnum.ELEKTOKLEINGERAET;
		} else if (bezeichnung == "Altglas") {
			return EntsorgungsartEnum.ALTGLAS;
		} else if (bezeichnung == "Altkleider") {
			return EntsorgungsartEnum.ALTKLEIDER;
		} else if (bezeichnung == "Recyclinghof") {
			return EntsorgungsartEnum.RECYCLINGHOF;
		}
		return null;
	}

}
