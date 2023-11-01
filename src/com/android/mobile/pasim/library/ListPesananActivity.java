package com.android.mobile.pasim.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mobile.pasim.library.adapter.BukuListAdapter;
import com.android.mobile.pasim.library.models.Buku;
import com.android.mobile.pasim.library.models.TAGS;
import com.android.mobile.pasim.library.models.URLFunctions;
import com.android.mobile.pasim.library.utils.AlertDialogManager;
import com.android.mobile.pasim.library.utils.AppController;
import com.android.mobile.pasim.library.utils.SessionManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

public class ListPesananActivity extends Activity {

	private static final String TAG = ListPesananActivity.class.getSimpleName();
	private ProgressDialog progressDialog;
	private List<Buku> bukuList = new ArrayList<Buku>();
	private ListView listView;
	private BukuListAdapter bukuListAdapter;

	private SessionManager sessionManager;
	private String nimnik;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor(getString(R.color.actionBar_color))));
		actionBar
				.setTitle(Html
						.fromHtml("<font weight='bold' color='#fe0000'>PEMESANAN</font>"));
		actionBar.setIcon(R.drawable.circle_booking);
		actionBar.setDisplayHomeAsUpEnabled(true);

		listView = (ListView) findViewById(R.id.list_buku);
		bukuListAdapter = new BukuListAdapter(this, bukuList);
		bukuListAdapter.setCounter(1);
		listView.setAdapter(bukuListAdapter);

		registerForContextMenu(listView);

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading...");
		progressDialog.show();

		sessionManager = new SessionManager(getApplicationContext());
		HashMap<String, String> hashMap = sessionManager.getUserDetails();
		nimnik = hashMap.get(SessionManager.KEY_USERNAME);

		JsonArrayRequest req = new JsonArrayRequest(URLFunctions.URLPESANANJSON
				+ nimnik, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				Log.d(TAG, response.toString());
				hideProgressDialog();
				for (int i = 0; i < response.length(); i++) {
					try {
						JSONObject jsonObject = response.getJSONObject(i);
						Buku buku = new Buku();
						buku.setJudul_buku(jsonObject
								.getString(TAGS.TAG_JUDUL_BUKU));
						buku.setKategori(jsonObject
								.getString(TAGS.TAG_KATEGORI));
						buku.setCoverUrl(jsonObject.getString(TAGS.TAG_COVER));
						buku.setWaktu_pesan(jsonObject
								.getString(TAGS.TAG_WAKTU_PESAN));
						buku.setIdPemesanan(jsonObject
								.getString(TAGS.TAG_ID_PEMESANAN));
						bukuList.add(buku);

					} catch (JSONException e) {
						e.printStackTrace();
//						Toast.makeText(getApplicationContext(),
//								"Error: " + e.getMessage(), Toast.LENGTH_LONG)
//								.show();
					}
				}
				bukuListAdapter.notifyDataSetChanged();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d(TAG, "Error: " + error.getMessage());
//				Toast.makeText(getApplicationContext(),
//						"Error Response : " + error.getMessage(),
//						Toast.LENGTH_SHORT).show();
			}
		});

		AppController.getInstance().addToRequestQueue(req);

	}

	private void hideProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Batalkan Pemesanan ini ?");
		// groupId, itemId, order, title
		menu.addSubMenu(0, 2, 0, "Ya");
		menu.addSubMenu(0, 3, 0, "Tidak");

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 2) {
			AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			int index = (int) info.id;
			Buku buku = (Buku) listView.getItemAtPosition(index);
			// showToast("YA : " + buku.getIdPemesanan());
			batalkanPesanan(Integer.parseInt(buku.getIdPemesanan()));
		} else if (item.getItemId() == 3) {
			item.setVisible(false);
			// showToast("TIDAK"); 081541400056
		} else {
			return false;
		}
		return true;

	}

	public void showToast(String jawaban) {
		Toast.makeText(getApplicationContext(), "kamu pilih : " + jawaban,
				Toast.LENGTH_LONG).show();

	}

	public void batalkanPesanan(int id) {
		Log.d("ID NYA : ", String.valueOf(id));
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				URLFunctions.URLCANCELPEMESANAN + id, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.d(TAG,
								"Respon JsonObject : " + jsonObject.toString());
						try {
							String result = jsonObject
									.getString(TAGS.TAG_RESULT);
							if (Integer.parseInt(result) == 1) {
								AlertDialogManager alertDialogManager = new AlertDialogManager();
								alertDialogManager
										.showAlertDialogCancelBooking(
												ListPesananActivity.this,
												"Sukses",
												"Pesanan Berhasil di Batalkan",
												true);
							} else {
								AlertDialogManager alertDialogManager = new AlertDialogManager();
								alertDialogManager
										.showAlertDialogCancelBooking(
												ListPesananActivity.this,
												"Kesalahan",
												"Pesanan Gagal di Batalkan",
												false);
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Log.e(TAG,
									"Error JsonException : " + e.getMessage());
//							Toast.makeText(getApplicationContext(),
//									"Catch : " + e.getMessage(),
//									Toast.LENGTH_LONG).show();

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
}
