package com.mines.deal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.Resources;

public class ReadPromoJson {

	private JSONObject mJson;

	public ReadPromoJson(Resources res) throws IOException, JSONException {

		InputStream mInputStream = res.openRawResource(R.raw.cutepromo);

		Writer writer = new StringWriter();

		char[] buffer = new char[1024];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(
					mInputStream, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} finally {
			mInputStream.close();
		}

		mJson = new JSONObject(writer.toString());
	}

	public ArrayList<Rayon> getRayons() {
		ArrayList<Rayon> rayons = new ArrayList<ReadPromoJson.Rayon>();

		try {
			JSONArray mArray = mJson.getJSONArray("data");
			JSONObject mData;
			for (int i = 0; i < mArray.length(); i++) {
				mData = (JSONObject) mArray.get(i);
				if (mData.getString("t").equals("1") & mData.getString("c").equals("2")) {
					System.out.println(mData.toString());
					System.out.println("--------------");
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rayons;
	}

	public class Rayon {
		public int id;
		public String nom;

		public Rayon(int id, String nom) {
			super();
			this.id = id;
			this.nom = nom;
		}
	}
}
