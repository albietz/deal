package com.mines.deal;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Camembert extends View {

	private Paint mPaint;
	private List<Float> pourcentages;
	private List<String> elements;
	private int currentColor;

	public Camembert(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public Camembert(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setARGB(255, 180, 100, 100);
		currentColor = mPaint.getColor();
		pourcentages = new ArrayList<Float>();
		elements = new ArrayList<String>();
		// TODO Auto-generated constructor stub
	}

	public Camembert(Context context) {
		super(context);
		mPaint = new Paint();
		mPaint.setARGB(255, 50, 50, 50);
	}

	public void setCamembert(ArrayList<Float> pourcentages,
			ArrayList<String> elements) {
		this.pourcentages = pourcentages;
		this.elements = elements;
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMinimumHeight(getWidth());
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		RectF mBigOval = new RectF(60, 60, canvas.getWidth() - 60,
				canvas.getWidth() - 60);
		float mid = canvas.getWidth() / 2;
		float radius = ((canvas.getWidth() - 60)) * 0.28f;
		float cumul = 0;
		canvas.drawColor(Color.argb(0, 0, 0, 0));
		float pourcentage, xText, yText;
		String element;
		Paint textPaint = new Paint();
		textPaint.setTextSize(30);
		textPaint.setColor(Color.BLACK);

		for (int i = 0; i < pourcentages.size(); i++) {
			pourcentage = pourcentages.get(i);
			element = elements.get(i);
			xText = (float) (mid + radius
					* Math.cos((cumul + pourcentage * 3.6 / 2) / 180 * 3.14));
			yText = (float) (mid + radius
					* Math.sin((cumul + pourcentage * 3.6 / 2) / 180 * 3.14));
			Paint myPaint = new Paint();
			myPaint.setAntiAlias(true);
			myPaint.setColor(currentColor);
			canvas.drawArc(mBigOval, cumul, pourcentage * 3.6f, true, myPaint);
			canvas.drawText(element, xText - 5 * element.length(), yText,
					textPaint);
			cumul += pourcentage * 3.6f;
			currentColor += 15 + 14 * 256 + 10 * 256 * 256;
		}
	}
}
