package com.mines.deal;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;

public class Shopping extends SQLiteOpenHelper {

	public Shopping(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public static class Achat {
		public int quantity;
		public float price;

		public Achat(int quantity, float price, String name) {
			this.quantity = quantity;
			this.price = price;
			this.name = name;
		}

		public String name;
	}

	public static class Cart {
		public ArrayList<Achat> achats;
		public Date date;
		public int id;

		public float getTotal() {
			float total = 0;
			for (Achat achat : achats) {
				total += achat.quantity * achat.price;
			}
			return total;
		}

		public int getQuantity() {
			int total = 0;
			for (Achat achat : achats) {
				total += achat.quantity;
			}
			return total;
		}

		public Cart(Date date) {
			this.date = date;
			this.achats = new ArrayList<Achat>();
		}

		public Cart() {
			this(new Date());
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE cart (date INTEGER);");
		db.execSQL("CREATE TABLE item (name TEXT);");
		db.execSQL("CREATE TABLE achat (idcart INTEGER, iditem INTEGER, price NUMERIC, quantity INTEGER);");

	}

	public Cart getCart(int id) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query("cart", new String[] { "date" }, "_id is " + String.valueOf(id), null, null, null, null);
		c.moveToNext();
		Cart cart = new Cart();
		cart.date = new java.sql.Date(c.getLong(0));
		c = db.query("achat", new String[] { "iditem", "price", "quantity" }, "idcart is " + String.valueOf(id), null, null, null, null);
		while (c.moveToNext()) {
			long iditem = c.getLong(0);
			Cursor cc = db.query("item", new String[] {"name"}, "_id is " + String.valueOf(iditem), null, null, null, null);
			cc.moveToNext();
			cart.achats.add(new Achat(c.getInt(2), c.getFloat(1), cc.getString(0)));
		}
		return cart;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
