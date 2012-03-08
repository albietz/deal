package com.mines.deal;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class StatisticsActivity extends Activity {

	private ArrayList<Float> pourcentages;
	private ArrayList<String> elements;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ScrollView sv = (ScrollView) inflater
				.inflate(R.layout.statistics, null);
		LinearLayout ll = (LinearLayout) sv.findViewById(R.id.statistics_ll);

		Camembert cam = (Camembert) sv.findViewById(R.id.camembert1);
		pourcentages = new ArrayList<Float>();
		elements = new ArrayList<String>();
		pourcentages.add(10f);
		pourcentages.add(50f);
		pourcentages.add(20f);
		pourcentages.add(20f);
		elements.add("autres");
		elements.add("fruits et légumes");
		elements.add("fournitures");
		elements.add("ménage");
		cam.setCamembert(pourcentages, elements);

		RelativeLayout statsLayout;

		for (int i = 0; i < pourcentages.size(); i++) {
			statsLayout = (RelativeLayout) inflater.inflate(
					R.layout.statistics_item, null);

			TextView name = (TextView) statsLayout.findViewById(R.id.name);
			TextView quantity = (TextView) statsLayout
					.findViewById(R.id.quantity);
			name.setText(elements.get(i));
			quantity.setText(String.valueOf(pourcentages.get(i)));

			ll.addView(statsLayout);

		}
		System.out.println(String.valueOf(ll.getChildCount()));
		setContentView(sv);
	}

}
