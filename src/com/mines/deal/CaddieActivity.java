package com.mines.deal;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.mines.deal.Shopping.Item;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CaddieActivity extends ListActivity {
	private CaddieListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		mAdapter = new CaddieListAdapter();
		setListAdapter(mAdapter);
		setContentView(R.layout.caddie);

		switch (getIntent().getIntExtra("action", 0)) {
		case 2:

			break;
		case 1:
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// String path = getCacheDir().getAbsolutePath() + "/liste";
			File picture = new File(Environment.getExternalStorageDirectory(),
					"image.tmp");
			Uri outputUri = Uri.fromFile(picture);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
			startActivityForResult(intent, 100);
			break;
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == 100) {
			// Bitmap mImageBitmap = (Bitmap) intent.getExtras().get("data");
			Bitmap mImageBitmap = (Bitmap) BitmapFactory.decodeFile(Environment
					.getExternalStorageDirectory() + "/image.tmp");

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			mImageBitmap.compress(CompressFormat.JPEG, 75, bos);

			byte[] mybm;
			mybm = bos.toByteArray();
			postBitmap(mybm);
		}
	}

	public void postBitmap(byte[] bm) {
		String imageString = Base64.encodeToString(bm, Base64.DEFAULT);
		new uploadImage().execute(imageString);
	}

	public class uploadImage extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... imageString) {
			String result = "";
			try {
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("imagestr",
						imageString[0]));

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://10.0.2.1:8080/");

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();

				result = EntityUtils.toString(entity);

			} catch (Exception e) {
				result = e.toString();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				CaddieJsonReader myJsonReader = new CaddieJsonReader();
				List<Shopping.Item> items = myJsonReader.readJsonStream(result);
				mAdapter.setItems(items);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(result);
		}

	}

	public class CaddieListAdapter extends BaseAdapter {

		LayoutInflater mInflater;
		ArrayList<Shopping.Item> mItemList;

		public CaddieListAdapter() {
			super();
			mInflater = getLayoutInflater();
			mItemList = new ArrayList<Shopping.Item>();
		}

		public void setItems(List<Item> items) {
			mItemList = (ArrayList<Shopping.Item>) items;
			notifyDataSetChanged();
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
			convertView = mInflater.inflate(R.layout.shopping_item, null);
			Shopping.Item mItem = (Shopping.Item) getItem(position);

			TextView name = (TextView) convertView.findViewById(R.id.name);
			name.setText(mItem.name);
			TextView price = (TextView) convertView.findViewById(R.id.price);
			price.setText(String.valueOf(mItem.price * mItem.quantity) + "Û");
			TextView quantity = (TextView) convertView
					.findViewById(R.id.quantity);
			quantity.setText(String.valueOf(mItem.quantity) + " x "
					+ String.valueOf(mItem.price) + "Û");

			return convertView;
		}

	}

}
