package com.android.mobile.pasim.library;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class BlankActivity extends Activity {

	private TextView txtBlank;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blank);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor(getString(R.color.actionBar_color))));
		actionBar
				.setTitle(Html
						.fromHtml("<font weight='bold' color='#fe0000'>MY BOOKING</font>"));
		actionBar.setIcon(R.drawable.circle_booking);
		actionBar.setDisplayHomeAsUpEnabled(true);

		txtBlank = (TextView) findViewById(R.id.txtblank);
		txtBlank.setText(Html
				.fromHtml("<font color='#fe0000'><b>TIDAK ADA BUKU YANG DIPESAN</b></font>"));
	}

}
