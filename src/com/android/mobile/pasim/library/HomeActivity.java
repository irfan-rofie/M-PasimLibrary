package com.android.mobile.pasim.library;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.mobile.pasim.library.R;
import com.android.mobile.pasim.library.maps.RuteMapsActivity;
import com.android.mobile.pasim.library.models.TAGS;
import com.android.mobile.pasim.library.models.URLFunctions;
import com.android.mobile.pasim.library.utils.AlertDialogManager;
import com.android.mobile.pasim.library.utils.AppController;
import com.android.mobile.pasim.library.utils.SessionManager;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private Button btn_user_profile;
	private Button btn_buku;
	// private Button btn_user;
	private Button btn_pesanan;
	private Button btn_peminjaman;
	private Button btn_ubah_password;

	private static String TAG = HomeActivity.class.getSimpleName();
	private SessionManager sessionManager;
	private String uname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color
						.parseColor(getString(R.color.actionBar_color))));
		getActionBar()
				.setTitle(
						Html.fromHtml("<font weight='bold' color='#fe0000'>M-PASIM LIBRARY</font>"));

		sessionManager = new SessionManager(getApplicationContext());

//		Toast.makeText(getApplicationContext(),
//				"Login Status : " + sessionManager.isLoggedIn(),
//				Toast.LENGTH_LONG).show();

		sessionManager.checkLogin();

		HashMap<String, String> username = sessionManager.getUserDetails();
		uname = username.get(SessionManager.KEY_USERNAME);

		// final String nama = username.get(SessionManager.KEY_NAME);

		if (uname == null) {
			finish();
		}

		// btn_user = (Button) findViewById(R.id.btn_user);
		// btn_user.setText(uname + " - " + nama + " (logout)");
		// btn_user.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// AlertDialogManager alertDialogManager = new AlertDialogManager();
		// alertDialogManager.showAlertDialogLogout(HomeActivity.this,
		// "Keluar Aplikasi", nama + ", Yakin Keluar Aplikasi ?",
		// true);
		// }
		// });

		btn_user_profile = (Button) findViewById(R.id.btn_user_profile);
		btn_user_profile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getApplicationContext(),
						UserProfileActivity.class);
				startActivity(it);
			}
		});

		btn_buku = (Button) findViewById(R.id.btn_buku);
		btn_buku.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						BukuActivity.class);
				startActivity(intent);
			}
		});

		btn_pesanan = (Button) findViewById(R.id.btn_pesanan);
		btn_pesanan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cekPesananPeminjaman(uname, URLFunctions.URLCEKBOOKING, 1);
			}
		});

		btn_peminjaman = (Button) findViewById(R.id.btn_peminjaman);
		btn_peminjaman.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cekPesananPeminjaman(uname, URLFunctions.URLCEKPEMINJAMAN, 2);
			}
		});

		btn_ubah_password = (Button) findViewById(R.id.btn_ubah_password);
		btn_ubah_password.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						RuteMapsActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ubah_password:
			Intent intent = new Intent(HomeActivity.this,
					UbahPasswordActivity.class);
			startActivity(intent);
			return true;
		case R.id.logout:
			AlertDialogManager alertDialogManager = new AlertDialogManager();
			alertDialogManager.showAlertDialogLogout(HomeActivity.this,
					"Keluar Aplikasi", " Yakin Keluar Aplikasi ?", true);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void cekPesananPeminjaman(final String nim, String url, final int id) {

		String tag_string_req = "req_cek_booking_or_peminjaman";

		StringRequest stringRequest = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d(TAG, "Responnya : " + response.toString());
						try {
							JSONObject jsonObject = new JSONObject(response);
							String respon = jsonObject
									.getString(TAGS.TAG_RESULT);
							if (Integer.parseInt(respon) == 0) {
								AlertDialogManager alertDialogManager = new AlertDialogManager();
								alertDialogManager
										.showAlertDialogCekPesananPeminjaman(
												HomeActivity.this,
												"Perhatian",
												id == 1 ? "Kamu tidak mempunyai Pesanan buku"
														: "Kamu tidak mempunyai Pinjaman buku",
												true);
								// Intent inte = new Intent(
								// getApplicationContext(),
								// BlankActivity.class);
								// startActivity(inte);
							} /*
							 * else if (Integer.parseInt(respon) == 400) {
							 * Toast.makeText(getApplicationContext(),
							 * "Silahkan Cek Lagi Query nya",
							 * Toast.LENGTH_LONG).show(); }
							 */else {
								Intent intent = new Intent(
										getApplicationContext(),
										id == 1 ? ListPesananActivity.class
												: TabPeminjamanActivity.class);
								startActivity(intent);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError errorResponse) {
						Log.d(TAG, "Error: " + errorResponse.getMessage());
//						Toast.makeText(
//								getApplicationContext(),
//								"Error Response : "
//										+ errorResponse.getMessage(),
//								Toast.LENGTH_LONG).show();
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> map = new HashMap<String, String>();
				map.put(TAGS.TAG_NIM, nim);
				return map;
			}
		};
		AppController.getInstance().addToRequestQueue(stringRequest,
				tag_string_req);
	}
}
