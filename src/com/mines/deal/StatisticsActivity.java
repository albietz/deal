package com.mines.deal;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class StatisticsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.statistics);
		super.onCreate(savedInstanceState);

		Camembert cam = (Camembert) findViewById(R.id.camembert1);
		ArrayList<Float> pourcentages = new ArrayList<Float>();
		ArrayList<String> elements = new ArrayList<String>();
		pourcentages.add(10f);
		pourcentages.add(50f);
		pourcentages.add(20f);
		pourcentages.add(20f);
		elements.add("vins");
		elements.add("fruits et légumes");
		elements.add("fournitures");
		elements.add("ménage");
		cam.setCamembert(pourcentages, elements);
	}

}
