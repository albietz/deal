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
	private int currentColor;

	public Camembert(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public Camembert(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setARGB(255, 12, 50, 72);
		currentColor = mPaint.getColor();
		// TODO Auto-generated constructor stub
	}

	public Camembert(Context context) {
		super(context);
		mPaint = new Paint();
		mPaint.setARGB(255, 50, 50, 50);
	}

	public void setCamembert(ArrayList<Float> pourcentages) {
		this.pourcentages = pourcentages;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// canvas.drawLine(10,10,100,100, mPaint);
		RectF mBigOval = new RectF(40, 10, 280, 250);

		float cumul = 0;
		canvas.drawColor(Color.BLACK);
		for (float pourcentage : pourcentages) {
			Paint myPaint = new Paint();
			myPaint.setColor(currentColor + 128);
			canvas.drawArc(mBigOval, cumul, pourcentage * 3.6f, true, myPaint);
			cumul += pourcentage * 3.6f;
			currentColor += 256*(23+4*256+150*256*256);
		}
	}

}
