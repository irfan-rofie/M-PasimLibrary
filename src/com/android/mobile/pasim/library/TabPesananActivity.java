package com.android.mobile.pasim.library;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class TabPesananActivity extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(null);
		Resources resources = getResources();
		TabHost tabHost = getTabHost();

//		Intent inProgress = new Intent().setClass(this, cls);
//		TabSpec tabSpecProgres = tabHost.newTabSpec("InProgress")
//				.setIndicator("", resources.getDrawable(R.drawable.anggota))
//				.setContent(inProgress);
//
//		Intent completed = new Intent().setClass(this, cls);
//		TabSpec tabSpecComplet = tabHost.newTabSpec("InProgress")
//				.setIndicator("", resources.getDrawable(R.drawable.anggota))
//				.setContent(completed);

	}

}
