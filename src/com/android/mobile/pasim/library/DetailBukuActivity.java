package com.android.mobile.pasim.library;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.mobile.pasim.library.models.Buku;
import com.android.mobile.pasim.library.models.TAGS;
import com.android.mobile.pasim.library.models.URLFunctions;
import com.android.mobile.pasim.library.utils.AlertDialogManager;
import com.android.mobile.pasim.library.utils.AppController;
import com.android.mobile.pasim.library.utils.SessionManager;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailBukuActivity extends Activity {

	private TextView tv_kdBuku, tv_jdlBuku, tv_pengarang, tv_penerbit,
			tv_thnTerbit, tv_jmlBuku, tv_kategori, tv_resensi, tv_rak;
	private NetworkImageView niv_cover;
	private ImageLoader imageLoader = AppController.getInstance()
			.getImageLoader();
	private Button btn_booking;

	private static String TAG = DetailBukuActivity.class.getSimpleName();
	private SessionManager sessionManager;
	private String nim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_buku);

		sessionManager = new SessionManager(getApplicationContext());
		HashMap<String, String> user = sessionManager.getUserDetails();
		nim = user.get(SessionManager.KEY_USERNAME);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor(getString(R.color.actionBar_color))));
		actionBar
				.setTitle(Html
						.fromHtml("<font weight='bold' color='#fe0000'>DETAIL BUKU</font>"));
		actionBar.setIcon(R.drawable.circle_buku);
		actionBar.setDisplayHomeAsUpEnabled(true);

		tv_kdBuku = (TextView) findViewById(R.id.tv_kode_buku);
		tv_jdlBuku = (TextView) findViewById(R.id.tv_judul_buku);
		tv_pengarang = (TextView) findViewById(R.id.tv_pengarang);
		tv_penerbit = (TextView) findViewById(R.id.tv_penerbit);
		tv_thnTerbit = (TextView) findViewById(R.id.tv_thn_terbit);
		tv_kategori = (TextView) findViewById(R.id.tv_kategori);
		tv_jmlBuku = (TextView) findViewById(R.id.tv_juml_buku);
		tv_rak = (TextView) findViewById(R.id.tv_rak);
		tv_resensi = (TextView) findViewById(R.id.tv_resensi);
		niv_cover = (NetworkImageView) findViewById(R.id.niv_cover);
		btn_booking = (Button) findViewById(R.id.btn_booking);

		try {
			Intent intent = getIntent();
			final Buku bk = new Buku();
			bk.setKode_buku(intent.getStringExtra(TAGS.TAG_KODE_BUKU));
			bk.setJudul_buku(intent.getStringExtra(TAGS.TAG_JUDUL_BUKU));
			bk.setPengarang(intent.getStringExtra(TAGS.TAG_PENGARANG));
			bk.setPenerbit(intent.getStringExtra(TAGS.TAG_PENERBIT));
			bk.setTahun_terbit(intent.getStringExtra(TAGS.TAG_THN_TERBIT));
			bk.setResensi(intent.getStringExtra(TAGS.TAG_RESENSI));
			bk.setJumlah_buku(intent.getIntExtra(TAGS.TAG_JUMLAH, 0));
			bk.setKategori(intent.getStringExtra(TAGS.TAG_KATEGORI));
			bk.setRak(intent.getStringExtra(TAGS.TAG_RAK));
			bk.setCoverUrl(intent.getStringExtra(TAGS.TAG_COVER));

			tv_kdBuku.setText   ("Kode Buku          : " + bk.getKode_buku());
			tv_jdlBuku.setText  ("Judul Buku          : " + bk.getJudul_buku());
			tv_pengarang.setText("Pengarang          : " + bk.getPengarang());
			tv_penerbit.setText ("Penerbit               : " + bk.getPenerbit());
			tv_thnTerbit.setText("Tahun Terbit        : " + bk.getTahun_terbit());
			tv_jmlBuku.setText  ("Jumlah Tersedia : " + bk.getJumlah_buku());
			tv_kategori.setText ("Kategori               : " + bk.getKategori());
			tv_resensi.setText  ("Deskripsi              : " + bk.getResensi());
			tv_rak.setText      ("Lokasi Buku         : " + bk.getRak());
			niv_cover.setImageUrl(bk.getCoverUrl(), imageLoader);

			btn_booking.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					kirimBooking(nim, bk.getKode_buku());
				}
			});
		} catch (Exception e) {
		}

	}

	private void kirimBooking(final String nim, final String kodebuku) {

		String tag_string_req = "req_booking";

		StringRequest stringRequest = new StringRequest(Method.POST,
				URLFunctions.URLBOOKING, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d(TAG, "Responnya : " + response.toString());
						try {
							JSONObject jsonObject = new JSONObject(response);
							String respon = jsonObject
									.getString(TAGS.TAG_RESULT);
							AlertDialogManager dialogManager = new AlertDialogManager();
							if (Integer.parseInt(respon) == 1) {
								dialogManager
										.showAlertDialogBooking(
												DetailBukuActivity.this,
												"Sukses",
												"Buku Berhasil Pesan, Tunggu Konfirmasi Selanjutnyua Untuk Diproses",
												true);
								// Toast.makeText(
								// getApplicationContext(),
								// "Pemesanan Buku Berhasil, Silahkan Ambil di Perpustakaan",
								// Toast.LENGTH_SHORT).show();
							} else if (Integer.parseInt(respon) == 2) {
								dialogManager
										.showAlertDialogBooking(
												DetailBukuActivity.this,
												"Sukses",
												"Buku Berhasil Pesan, Namun Harus Menunggu Sampai Buku Tersedia Kembali Untuk Diproses. Sementara Buku Masih Kosong",
												true);
							} else if (Integer.parseInt(respon) == 400) {
								dialogManager
										.showAlertDialogBooking(
												DetailBukuActivity.this,
												"Perhatian",
												"Pesanan Gagal Diproses, Kamu Sudah Pesan 3 Buku",
												false);
							} else if (Integer.parseInt(respon) == 500) {
								dialogManager
										.showAlertDialogBooking(
												DetailBukuActivity.this,
												"Perhatian",
												"Pesanan Gagal Diproses, Kamu Sudah Pesan Buku Yang Sama",
												false);
							} else if (Integer.parseInt(respon) == 600) {
								dialogManager
										.showAlertDialogBooking(
												DetailBukuActivity.this,
												"Perhatian",
												"Pesanan Gagal Diproses, Kamu Sudah Pinjam 3 Buku dan belum dikembalikan",
												false);
							} else if (Integer.parseInt(respon) == 700) {
								dialogManager
										.showAlertDialogBooking(
												DetailBukuActivity.this,
												"Perhatian",
												"Pesanan Gagal Diproses, Kamu Sudah Meminjam Buku Yang Sama",
												false);
							} else {
								dialogManager.showAlertDialogBooking(
										DetailBukuActivity.this, "Gagal",
										"Pemesanan Buku Gagal", true);

								// Toast.makeText(getApplicationContext(),
								// "Pemesanan Buku Gagal",
								// Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
//							Toast.makeText(getApplicationContext(),
//									"Error Response : " + e.getMessage(),
//									Toast.LENGTH_SHORT).show();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError err) {
//						Toast.makeText(getApplicationContext(),
//								"Error Respon Listener : " + err.getMessage(),
//								Toast.LENGTH_SHORT).show();
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put(TAGS.TAG_NIM, nim);
				params.put(TAGS.TAG_KODE_BUKU, kodebuku);
				return params;
			}
		};

		AppController.getInstance().addToRequestQueue(stringRequest,
				tag_string_req);
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
