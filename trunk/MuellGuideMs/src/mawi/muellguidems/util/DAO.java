package mawi.muellguidems.util;

import java.util.ArrayList;
import java.util.HashMap;

public class DAO {

	public static ArrayList<HashMap<String, String>> getAlleGegenstaende() {
		try {
			ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

			HashMap<String, String> gegenstand = new HashMap<String, String>();
			gegenstand.put("id", "1234");
			gegenstand.put("bezeichnung", "Flasche");
			gegenstand.put("entsorgungsart", "Altglas");
			gegenstand.put("hinweis",
					"Bitte auf Braun-/Weißglas-Trennung achten!");
			result.add(gegenstand);

			return result;
		} catch (Exception ex) {
			return null;
		}
	}
}
