package com.android.mobile.pasim.library;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.mobile.pasim.library.models.Peminjaman;
import com.android.mobile.pasim.library.models.TAGS;
import com.android.mobile.pasim.library.models.URLFunctions;
import com.android.mobile.pasim.library.utils.AppController;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class DetailPeminjamanActivity extends Activity {

	private static String TAG = DetailPeminjamanActivity.class.getSimpleName();
	private NetworkImageView nivCover;
	private TextView tvIdTrx, tvJudul, tvTglPinjam, tvTglHrsKmbali,
			tvTglKmbali, tvStatusPmnjman, tvTelat, tvDenda;
	private ImageLoader imageLoader = AppController.getInstance()
			.getImageLoader();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_peminjaman);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor(getString(R.color.actionBar_color))));
		actionBar
				.setTitle(Html
						.fromHtml("<font weight='bold' color='#fe0000'>DETAIL PEMINJAMAN</font>"));
		actionBar.setIcon(R.drawable.circle_peminjaman);
		actionBar.setDisplayHomeAsUpEnabled(true);

		try {
			Intent it = getIntent();
			final Peminjaman pj = new Peminjaman();
			pj.setIdTransaksi(it.getStringExtra(TAGS.TAG_ID_TRANSAKSI));

			nivCover = (NetworkImageView) findViewById(R.id.niv_cover);
			tvIdTrx = (TextView) findViewById(R.id.tv_id_transaksi);
			tvJudul = (TextView) findViewById(R.id.tv_judul_buku);
			tvTglPinjam = (TextView) findViewById(R.id.tv_tgl_pinjam);
			tvTglHrsKmbali = (TextView) findViewById(R.id.tv_tgl_harus_kembali);
			tvTglKmbali = (TextView) findViewById(R.id.tv_tgl_kembali);
			tvStatusPmnjman = (TextView) findViewById(R.id.tv_status_peminjaman);
			tvTelat = (TextView) findViewById(R.id.tv_telat);
			tvDenda = (TextView) findViewById(R.id.tv_denda);

			JsonObjectRequest request = new JsonObjectRequest(Method.GET,
					URLFunctions.URLPEMINJAMANDETAIL + pj.getIdTransaksi(),
					null, new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject object) {
							Log.d(TAG, "Responnya : " + object.toString());
							try {
								Peminjaman pj = new Peminjaman();
								pj.setCover(object.getString(TAGS.TAG_COVER));
								pj.setIdTransaksi(object
										.getString(TAGS.TAG_ID_TRANSAKSI));
								// pj.setNamaAnggota(object.getString(TAGS.TAG_NAMA_ANGOOTA));
								pj.setJudulBuku(object
										.getString(TAGS.TAG_JUDUL_BUKU));
								pj.setTglPinjam(object
										.getString(TAGS.TAG_TGL_PINJAM));
								pj.setTglHarusKembali(object
										.getString(TAGS.TAG_TGL_HARUS_KEMBALI));
								pj.setStatusPeminjaman(object
										.getString(TAGS.TAG_STATUS_PEMINJAMAN));
								pj.setTglKembali(object
										.getString(TAGS.TAG_TGL_KEMBALI));
								pj.setTelat(object.getString(TAGS.TAG_TELAT));
								pj.setDenda(object.getString(TAGS.TAG_DENDA));

								if (imageLoader == null) {
									imageLoader = AppController.getInstance()
											.getImageLoader();
								}

								nivCover.setImageUrl(pj.getCover(), imageLoader);
								tvIdTrx.setText("ID Transaksi              : "
										+ pj.getIdTransaksi());
								tvJudul.setText("Judul Buku                : "
										+ pj.getJudulBuku());
								tvTglPinjam.setText("Tanggal Pinjam        : "
										+ pj.getTglPinjam());
								tvTglHrsKmbali
										.setText("Harus Kembali          : "
												+ pj.getTglHarusKembali());
								tvTglKmbali.setText("Tanggal Kembali       : "
										+ pj.getTglKembali());
								tvStatusPmnjman.setText("Status Peminjaman : "
										+ pj.getStatusPeminjaman());
								tvTelat.setText("Telat                             : "
										+ pj.getTelat());
								tvDenda.setText("Denda                          : "
										+ pj.getDenda());

							} catch (JSONException e) {
								Log.d(TAG, "Errornya : " + e.getMessage());
							}
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							VolleyLog.d(TAG, "Error : " + error.getMessage());
//							Toast.makeText(getApplicationContext(),
//									error.getMessage(), Toast.LENGTH_SHORT)
//									.show();

						}
					});

			AppController.getInstance().addToRequestQueue(request);

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.reload_only, menu);
	// return super.onCreateOptionsMenu(menu);
	// }
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
