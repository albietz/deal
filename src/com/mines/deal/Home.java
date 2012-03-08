package com.mines.deal;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mines.deal.Shopping.Cart;

public class Home extends ListActivity {
	Shopping shop;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		shop = new Shopping(this, "shopping", null, 1);
		Cursor c = shop.getReadableDatabase().query("cart",
				new String[] { "_id", "date" }, null, null, null, null, null);
		CartCursorAdapter mAdapter = new CartCursorAdapter(this, c);
		setListAdapter(mAdapter);

		ReadPromoJson rpj;
		try {
			rpj = new ReadPromoJson(getResources());
			rpj.getRayons();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		shop = new Shopping(this, "shopping", null, 1);
//
//		SQLiteDatabase db = shop.getWritableDatabase();
//		db.delete("item", null, null);
//		ContentValues values = new ContentValues();
//		values.put("name", "EAU CRISTALINE");
//		db.insert("item", null, values);
//		db.close();
//		Log.e("Home", "onCreate");
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		CartCursorAdapter adapter = (CartCursorAdapter)getListAdapter();
		adapter.changeCursor(shop.getReadableDatabase().query("cart",
				new String[] { "_id", "date" }, null, null, null, null, null));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_menu, menu);
		return true;

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getApplicationContext(),
				CaddieActivity.class);
		intent.putExtra("action", 2);
		intent.putExtra("id", id);
		startActivity(intent);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case (R.id.menu_add):
			intent = new Intent(getApplicationContext(), CaddieActivity.class);
			intent.putExtra("action", 1);
			startActivity(intent);
			break;
		case (R.id.menu_statistics):
			intent = new Intent(getApplicationContext(),
					StatisticsActivity.class);
			startActivity(intent);
			break;
		}
		return true;
	}

	private class CartCursorAdapter extends CursorAdapter {
		private LayoutInflater mInflater;
		
		public CartCursorAdapter(Context context, Cursor c) {
			super(context, c);
			mInflater = getLayoutInflater();
		}

		@Override
		public void bindView(View view, Context context, Cursor c) {
			view = mInflater.inflate(R.layout.shopping_cart, null);
			TextView tvDate = (TextView)view.findViewById(R.id.date);
			tvDate.setText(c.getString(1));
		}

		@Override
		public View newView(Context context, Cursor c, ViewGroup viewGroup) {
			View view = mInflater.inflate(R.layout.shopping_cart, null);
			TextView tvDate = (TextView)view.findViewById(R.id.date);
			tvDate.setText(c.getString(1));
			return view;
		}

	}

}