package com.android.mobile.pasim.library;

import com.android.mobile.pasim.library.R;
import com.android.mobile.pasim.library.utils.AlertDialogManager;
import com.android.mobile.pasim.library.utils.ConnectionDetector;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class MainActivity extends Activity {

	private static int SPLASH_TIME_OUT = 3000;
	private Boolean isConnectionDetector = false;
	private ConnectionDetector connectionDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_);
		connectionDetector = new ConnectionDetector(getApplicationContext());
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				isConnectionDetector = connectionDetector
						.isConnectingToInternet();

				if (isConnectionDetector) {
					Intent in = new Intent(MainActivity.this,
							HomeActivity.class);
					startActivity(in);
					finish();
				} else {
					AlertDialogManager alertDialogManager = new AlertDialogManager();
					alertDialogManager.showAlertDialogDetect(MainActivity.this,
							"Tidak Ada Koneksi Internet",
							"Kamu tidak mempunyai koneksi internet.", false);
				}
			}
		}, SPLASH_TIME_OUT);
	}
}
