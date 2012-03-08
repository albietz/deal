package com.mines.deal;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Shopping extends SQLiteOpenHelper {

	public Shopping(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public static class Item {
		public int quantity;
		public float price;

		public Item(int quantity, float price, String name) {
			this.quantity = quantity;
			this.price = price;
			this.name = name;
		}

		public String name;
	}

	public static class Cart {
		public ArrayList<Item> items;
		public Date date;

		public float getTotal() {
			float total = 0;
			for (Item item : items) {
				total += item.quantity * item.price;
			}
			return total;
		}

		public int getQuantity() {
			int total = 0;
			for (Item item : items) {
				total += item.quantity;
			}
			return total;
		}

		public Cart(Date date) {
			this.date = date;
			this.items = new ArrayList<Item>();
		}

		public Cart() {
			this(new Date());
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
