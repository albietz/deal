package com.mines.deal;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import android.util.JsonReader;

public class CaddieJsonReader {
	public List<Shopping.Achat> readJsonStream(String s) throws IOException {
		StringReader sr = new StringReader(s);
		JsonReader reader = new JsonReader(sr);
		try {
			return readAchatsArray(reader);
		} finally {
			reader.close();
		}
	}

	public List<Shopping.Achat> readAchatsArray(JsonReader reader)
			throws IOException {
		List<Shopping.Achat> achats = new ArrayList<Shopping.Achat>();

		reader.beginArray();
		while (reader.hasNext()) {
			achats.add(readMessage(reader));
		}
		reader.endArray();
		return achats;
	}

	public Shopping.Achat readMessage(JsonReader reader) throws IOException {
		String name = null;
		float price = 0;
		int quantity = 0;

		reader.beginObject();
		while (reader.hasNext()) {
			String type = reader.nextName();
			if (type.equals("name")) {
				name = reader.nextString();
			} else if (type.equals("price")) {
				price = Float.parseFloat(reader.nextString());
			} else if (type.equals("qty")) {
				quantity = Integer.parseInt(reader.nextString());
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return new Shopping.Achat(quantity, price, name);
	}

}
