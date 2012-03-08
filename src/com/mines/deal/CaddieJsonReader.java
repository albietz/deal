package com.mines.deal;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import android.util.JsonReader;

public class CaddieJsonReader {
	public List<Shopping.Item> readJsonStream(String s) throws IOException {
		StringReader sr = new StringReader(s);
		JsonReader reader = new JsonReader(sr);
		try {
			return readItemsArray(reader);
		} finally {
			reader.close();
		}
	}

	public List<Shopping.Item> readItemsArray(JsonReader reader)
			throws IOException {
		List<Shopping.Item> items = new ArrayList<Shopping.Item>();

		reader.beginArray();
		while (reader.hasNext()) {
			items.add(readMessage(reader));
		}
		reader.endArray();
		return items;
	}

	public Shopping.Item readMessage(JsonReader reader) throws IOException {
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
		return new Shopping.Item(quantity, price, name);
	}

}
