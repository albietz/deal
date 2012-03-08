package com.mines.deal;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CaddieActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		CaddieListAdapter mAdapter = new CaddieListAdapter();
		setListAdapter(mAdapter);
		super.onCreate(savedInstanceState);
	}

	public class CaddieListAdapter extends BaseAdapter {

		LayoutInflater mInflater;
		ArrayList<Shopping.Item> mItemList;

		public CaddieListAdapter() {
			super();
			mInflater = getLayoutInflater();
			mItemList = new ArrayList<Shopping.Item>();
		}

		@Override
		public int getCount() {
			return mItemList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mItemList.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = mInflater.inflate(R.layout.caddie_item, null);
			TextView name = (TextView) convertView.findViewById(R.id.name);
			Shopping.Item mItem = (Shopping.Item) getItem(position);
			name.setText(mItem.name);
			return convertView;
		}

	}

}
