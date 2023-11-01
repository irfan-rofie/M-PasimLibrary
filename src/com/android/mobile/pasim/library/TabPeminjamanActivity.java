package com.android.mobile.pasim.library;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class TabPeminjamanActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_peminjaman);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor(getString(R.color.actionBar_color))));
		actionBar
				.setTitle(Html
						.fromHtml("<font weight='bold' color='#fe0000'>PEMINJAMAN</font>"));
		actionBar.setIcon(R.drawable.circle_peminjaman);
		actionBar.setDisplayHomeAsUpEnabled(true);

		TabHost tabHost = getTabHost();

		TabSpec in = tabHost.newTabSpec("AKTIF");
		in.setIndicator("AKTIF");
		Intent inIntent = new Intent(this, ListPeminjamanActivity.class);
		in.setContent(inIntent);

		TabSpec done = tabHost.newTabSpec("SELESAI");
		done.setIndicator("SELESAI");
		Intent doneIntent = new Intent(this, ListPeminjamanDoneActivity.class);
		done.setContent(doneIntent);

		tabHost.addTab(in);
		tabHost.addTab(done);
	}

}
