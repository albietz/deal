package com.mines.deal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Shopping extends SQLiteOpenHelper {

	public Shopping(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public Shopping(Context context) {
		this(context, "shopping", null, 1);
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

	public static class Rayon {
		public int id;
		public String nom;

		public Rayon(int id, String nom) {
			this.id = id;
			this.nom = nom;
		}
	}

	public static class Promo {
		public int id;
		public String nom;
		public int rayonId;
		public double prix;
		public double prixBarre;
		public double cagnotte;
		public Date dateDebut;
		public Date dateFin;

		public Promo(int id, String nom) {
			this.id = id;
			this.nom = nom;
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE cart (_id INTEGER PRIMARY KEY, date TEXT);");
		db.execSQL("CREATE TABLE item (_id INTEGER PRIMARY KEY, name TEXT);");
		db.execSQL("CREATE TABLE achat (_id INTEGER PRIMARY KEY, idcart INTEGER, iditem INTEGER, name TEXT, price NUMERIC, quantity INTEGER);");

	}

	public Cart getCart(long id) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query("cart", new String[] { "date" },
				"_id is " + String.valueOf(id), null, null, null, null);
		c.moveToNext();
		Cart cart = new Cart();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			cart.date = format.parse(c.getString(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c = db.query("achat", new String[] { "iditem", "price", "quantity" },
				"idcart is " + String.valueOf(id), null, null, null, null);
		while (c.moveToNext()) {
			int iditem = (int) c.getLong(0);
			System.out.println(iditem);
			Cursor cc = db.query("item", new String[] { "name" }, "_id is "
					+ String.valueOf(iditem), null, null, null, null);
			cc.moveToNext();
			cart.achats.add(new Achat(c.getInt(2), c.getFloat(1), cc
					.getString(0)));
		}
		return cart;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
