package com.mines.deal;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button add_list = (Button) findViewById(R.id.button_addlist);
		add_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			//	String path = getCacheDir().getAbsolutePath() + "/liste";
				File picture = new File(Environment.getExternalStorageDirectory(),  "image.tmp");
				Uri outputUri = Uri.fromFile(picture);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
				startActivityForResult(intent, 100);

			}
		});
		
		Button history = (Button) findViewById(R.id.button_history);
		history.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),HistoryActivity.class);
				startActivity(intent);
				
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == 100) {
			// Bitmap mImageBitmap = (Bitmap) intent.getExtras().get("data");
			Bitmap mImageBitmap = (Bitmap) BitmapFactory
					.decodeFile(Environment.getExternalStorageDirectory() + "/image.tmp");

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
			System.out.println(result);
			super.onPostExecute(result);
		}

	}
}