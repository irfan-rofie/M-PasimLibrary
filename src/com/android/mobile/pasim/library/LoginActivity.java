package com.android.mobile.pasim.library;

import com.android.mobile.pasim.library.R;
import com.android.mobile.pasim.library.models.TAGS;
import com.android.mobile.pasim.library.models.URLFunctions;
import com.android.mobile.pasim.library.utils.AlertDialogManager;
import com.android.mobile.pasim.library.utils.AppController;
import com.android.mobile.pasim.library.utils.SessionManager;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private static String TAG = LoginActivity.class.getSimpleName();

	private SessionManager sessionManager;

	private EditText et_nimnik;
	private EditText et_password;
	private Button btn_login;
	private String nimnik;
	private String password;

	private View mLoginStatusView;
	private View mLoginFormView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		sessionManager = new SessionManager(getApplicationContext());

		et_nimnik = (EditText) findViewById(R.id.nim_nik);
		et_password = (EditText) findViewById(R.id.password);
		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		btn_login = (Button) findViewById(R.id.sign_in_button);
		btn_login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				attemptLogin();
			}
		});

	}

	public void attemptLogin() {

		et_nimnik.setError(null);
		et_password.setError(null);

		nimnik = et_nimnik.getText().toString();
		password = et_password.getText().toString();

		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(password)) {
			et_password
					.setError(getString(R.string.error_nim_nik_password_required));
			focusView = et_password;
			cancel = true;
		}

		if (TextUtils.isEmpty(nimnik)) {
			et_nimnik
					.setError(getString(R.string.error_nim_nik_password_required));
			focusView = et_nimnik;
			cancel = true;
		}

		if (cancel) {
			focusView.requestFocus();
		} else {
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(false);
			prosesLogin();
		}
	}

	public void prosesLogin() {

		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				URLFunctions.URLLOGIN + nimnik + TAGS.TAG_PASSWORD + password,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.d(TAG,
								"Respon JsonObject : " + jsonObject.toString());
						try {
							String result = jsonObject
									.getString(TAGS.TAG_RESULT);
							if (Integer.parseInt(result) == 1) {
								String JsonNama = jsonObject
										.getString(TAGS.TAG_NAMA_ANGOOTA);
								sessionManager.createLoginSession(nimnik,
										JsonNama, password);
								showProgress(true);
								AlertDialogManager alertDialogManager = new AlertDialogManager();
								alertDialogManager.showAlertDialogLogin(
										LoginActivity.this, "Sukses",
										"Login Berhasil", true);
							} else {
								showProgress(true);
								AlertDialogManager alertDialogManager = new AlertDialogManager();
								alertDialogManager
										.showAlertDialogLogin(
												LoginActivity.this,
												"Kesalahan",
												"Login Gagal, Periksa Kembali Username dan Password",
												false);
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Log.e(TAG,
									"Error JsonException : " + e.getMessage());
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
						Log.e(TAG, "Error Response : " + error.getMessage());
//						Toast.makeText(getApplicationContext(),
//								error.getMessage(), Toast.LENGTH_LONG).show();
					}
				});

		AppController.getInstance().addToRequestQueue(request);

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

}
