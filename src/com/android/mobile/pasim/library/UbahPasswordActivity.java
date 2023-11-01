package com.android.mobile.pasim.library;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.mobile.pasim.library.models.TAGS;
import com.android.mobile.pasim.library.models.URLFunctions;
import com.android.mobile.pasim.library.utils.AlertDialogManager;
import com.android.mobile.pasim.library.utils.AppController;
import com.android.mobile.pasim.library.utils.SessionManager;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UbahPasswordActivity extends Activity {

	private EditText et_passLama;
	private EditText et_passBaru;
	private EditText et_konfPassBaru;
	private Button btn_ubahPass;
	private String passLama;
	private String passBaru;
	private String konfPassBaru;
	private SessionManager sessionManager;
	private String sessionPassword;
	private String sessionNimNik;
	private AlertDialogManager alertDialogManager = new AlertDialogManager();
	private static String TAG = UbahPasswordActivity.class.getSimpleName();
	private View focusView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ubah_password);

		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color
						.parseColor(getString(R.color.actionBar_color))));
		getActionBar()
				.setTitle(
						Html.fromHtml("<font weight='bold' color='#fe0000'>Ubah Password</font>"));
		getActionBar().setIcon(R.drawable.change);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		et_passLama = (EditText) findViewById(R.id.passlama);
		et_passBaru = (EditText) findViewById(R.id.passbaru);
		et_konfPassBaru = (EditText) findViewById(R.id.konfpassbaru);
		btn_ubahPass = (Button) findViewById(R.id.btn_ubah_pwd);

		btn_ubahPass.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				prepareUbahPassword();
			}
		});

	}

	public void prepareUbahPassword() {

		passLama = et_passLama.getText().toString().trim();
		passBaru = et_passBaru.getText().toString().trim();
		konfPassBaru = et_konfPassBaru.getText().toString().trim();

		boolean cancel = false;
		if (TextUtils.isEmpty(passLama)) {
			et_passLama.setError("Tidak Boleh Kosong");
			focusView = et_passLama;
			cancel = true;
		} else {
			if (TextUtils.isEmpty(passBaru)) {
				et_passLama.setError("Tidak Boleh Kosong");
				focusView = et_passBaru;
				cancel = true;
			} else {
				if (TextUtils.isEmpty(konfPassBaru)) {
					et_passLama.setError("Tidak Boleh Kosong");
					focusView = et_konfPassBaru;
					cancel = true;
				}
			}
		}

		if (cancel) {
			focusView.requestFocus();
		} else {
			sessionManager = new SessionManager(getApplicationContext());
			HashMap<String, String> map = sessionManager.getUserDetails();
			sessionPassword = map.get(SessionManager.KEY_PASSWORD);
			sessionNimNik = map.get(SessionManager.KEY_USERNAME);

			if (!sessionPassword.equals(passLama)) {
				alertDialogManager.showAlertDialogUbahPassword(
						UbahPasswordActivity.this, "Kesalahan",
						"Password Lama Tidak Sama", false);
				clearEditText();
			} else {
				if (!passBaru.equals(konfPassBaru)) {
					alertDialogManager.showAlertDialogUbahPassword(
							UbahPasswordActivity.this, "Kesalahan",
							"Kombinasi Password Baru Tidak Sama", false);
					clearEditText();
				} else {
					ubahPassword();
				}
			}
		}
	}

	public void clearEditText() {
		et_passLama.setText(null);
		et_passBaru.setText(null);
		et_konfPassBaru.setText(null);
	}

	public void ubahPassword() {

		JsonObjectRequest objectRequest = new JsonObjectRequest(Method.GET,
				URLFunctions.URLUBAHPASSWORD + sessionNimNik
						+ TAGS.TAG_PASSWORD + passBaru, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.d(TAG,
								"Respon JSONObject : " + jsonObject.toString());
						try {
							String result = jsonObject.getString("result");
							AlertDialogManager alertDialogManager = new AlertDialogManager();
							if (Integer.parseInt(result) == 1) {
								sessionManager.createPasswordSession(passBaru);
								alertDialogManager.showAlertDialogUbahPassword(
										UbahPasswordActivity.this, "Sukses",
										"Ubah Password Berhasil", true);
							} else {
								alertDialogManager.showAlertDialogUbahPassword(
										UbahPasswordActivity.this, "Gagal",
										"Ubah Password Gagal", false);
								clearEditText();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Log.d(TAG, "Exception nya : " + e.getMessage());
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
						Log.d(TAG, "Error Respon nya : " + error.getMessage());
//						Toast.makeText(getApplicationContext(),
//								error.getMessage(), Toast.LENGTH_LONG).show();
					}
				});

		AppController.getInstance().addToRequestQueue(objectRequest);
	}
}
