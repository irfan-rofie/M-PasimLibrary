package com.android.mobile.pasim.library;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.mobile.pasim.library.models.Anggota;
import com.android.mobile.pasim.library.models.TAGS;
import com.android.mobile.pasim.library.models.URLFunctions;
import com.android.mobile.pasim.library.utils.AppController;
import com.android.mobile.pasim.library.utils.SessionManager;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

public class UserProfileActivity extends Activity {

	private static String TAG = UserProfileActivity.class.getSimpleName();
	private TextView tv_nimNik, tv_namaAgt, tv_jenKel, tv_ttl, tv_jurKet,
			tv_alamat, tv_noKontak, tv_tglMasuk, tv_status;
	private NetworkImageView niv_foto;
	private ImageLoader imageLoader = AppController.getInstance()
			.getImageLoader();
	private SessionManager sessionManager;
	private String nimnik;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor(getString(R.color.actionBar_color))));
		actionBar
				.setTitle(Html
						.fromHtml("<font weight='bold' color='#fe0000'>PROFIL</font>"));
		actionBar.setIcon(R.drawable.circle_profil);
		actionBar.setDisplayHomeAsUpEnabled(true);

		tv_nimNik = (TextView) findViewById(R.id.tv_nim_nik);
		tv_namaAgt = (TextView) findViewById(R.id.tv_nama_anggota);
		tv_jenKel = (TextView) findViewById(R.id.tv_jen_kel);
		tv_ttl = (TextView) findViewById(R.id.tv_tmp_tgl_lahir);
		tv_jurKet = (TextView) findViewById(R.id.tv_jur_ket);
		tv_alamat = (TextView) findViewById(R.id.tv_alamat);
		tv_noKontak = (TextView) findViewById(R.id.tv_no_kontak);
		tv_tglMasuk = (TextView) findViewById(R.id.tv_tgl_masuk);
		tv_status = (TextView) findViewById(R.id.tv_status);
		niv_foto = (NetworkImageView) findViewById(R.id.niv_foto);

		sessionManager = new SessionManager(getApplicationContext());
		HashMap<String, String> hashMap = sessionManager.getUserDetails();
		nimnik = hashMap.get(SessionManager.KEY_USERNAME);

		JsonObjectRequest objectRequest = new JsonObjectRequest(Method.GET,
				URLFunctions.URLGETUSERPROFILE + nimnik, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.d(TAG, "Responnya : " + jsonObject.toString());
						try {
							Anggota anggota = new Anggota();
							anggota.setNimNik(jsonObject
									.getString(TAGS.TAG_NIM));
							anggota.setNama(jsonObject
									.getString(TAGS.TAG_NAMA_ANGOOTA));
							anggota.setJenKel(jsonObject
									.getString(TAGS.TAG_JEN_KEL));
							anggota.setTtl(jsonObject
									.getString(TAGS.TAG_TMP_TGL_LAHIR));
							anggota.setJurKet(jsonObject
									.getString(TAGS.TAG_JUR_KET));
							anggota.setAlamat(jsonObject
									.getString(TAGS.TAG_ALAMAT));
							anggota.setNoKontak(jsonObject
									.getString(TAGS.TAG_NO_KONTAK));
							anggota.setTanggalMasuk(jsonObject
									.getString(TAGS.TAG_TGL_MASUK));
							anggota.setStatus(jsonObject
									.getString(TAGS.TAG_STATUS));
							anggota.setThumbNailUrl(jsonObject
									.getString(TAGS.TAG_FOTO));

							tv_nimNik.setText("Nomor Induk                : "
									+ anggota.getNimNik());
							tv_namaAgt.setText("Nama Lengkap             : "
									+ anggota.getNama());
							tv_jenKel.setText("Jenis Kelamin               : "
									+ anggota.getJenKel());
							tv_ttl.setText("Tempat Tanggal Lahir : "
									+ anggota.getTtl());
							tv_jurKet.setText("Jurusan                          : "
									+ anggota.getJurKet());
							tv_alamat
									.setText("Alamat                            : "
											+ anggota.getAlamat());
							tv_noKontak
									.setText("No Kontak                      : "
											+ anggota.getNoKontak());
							tv_tglMasuk.setText("Tanggal Keanggotaan : "
									+ anggota.getTanggalMasuk());
							tv_status
									.setText("Status                              : "
											+ anggota.getStatus());
							niv_foto.setImageUrl(anggota.getThumbNailUrl(),
									imageLoader);

						} catch (JSONException e) {
							e.printStackTrace();
							Log.d(TAG, "JsonExecption : " + e.getMessage());
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
						Log.d(TAG, "volleyError" + error.getMessage());
					}
				});

		AppController.getInstance().addToRequestQueue(objectRequest);

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.reload_only, menu);
	// return super.onCreateOptionsMenu(menu);
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.reload:
	// refreshMenuItem = item;
	// new SyncData().execute();
	// return true;
	// default:
	// return super.onOptionsItemSelected(item);
	// }
	// }
	//
	// private class SyncData extends AsyncTask<String, Void, String> {
	// @Override
	// protected void onPreExecute() {
	// refreshMenuItem.setActionView(R.layout.action_progressbar);
	// refreshMenuItem.expandActionView();
	// }
	//
	// @Override
	// protected String doInBackground(String... params) {
	// try {
	// Thread.sleep(3000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// refreshMenuItem.collapseActionView();
	// refreshMenuItem.setActionView(null);
	// }
	// };

}
