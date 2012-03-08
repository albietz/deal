package com.mines.deal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.Resources;

import com.mines.deal.Shopping.Promo;
import com.mines.deal.Shopping.Rayon;

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
		ArrayList<Rayon> rayons = new ArrayList<Rayon>();

		try {
			JSONArray mArray = mJson.getJSONArray("data");
			JSONObject mData;
			for (int i = 0; i < mArray.length(); i++) {
				mData = (JSONObject) mArray.get(i);
				if (mData.getInt("t") == 1 & mData.getInt("c") == 2) {
					JSONArray rArr = mData.getJSONArray("r");
					if (rArr.getInt(3) == 0) {
						rayons.add(new Rayon(rArr.getInt(0), rArr.getString(1)));
					}
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rayons;
	}

	public ArrayList<Promo> getPromos() {
		ArrayList<Promo> promos = new ArrayList<Promo>();

		try {
			JSONArray mArray = mJson.getJSONArray("data");
			JSONObject mData;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < mArray.length(); i++) {
				mData = (JSONObject) mArray.get(i);
				if (mData.getInt("t") == 2 & mData.getInt("c") == 2) {
					JSONArray arr = mData.getJSONArray("r");
					Promo p = new Promo(arr.getInt(0), arr.getString(4));
					p.rayonId = arr.getInt(1);
					p.prix = arr.getDouble(6);
					p.prixBarre = arr.getDouble(7);
					p.cagnotte = arr.getDouble(8);
					p.dateDebut = format.parse(arr.getString(16));
					p.dateFin = format.parse(arr.getString(17));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return promos;
	}
}
