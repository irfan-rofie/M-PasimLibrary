package com.android.mobile.pasim.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mobile.pasim.library.adapter.BukuListAdapter;
import com.android.mobile.pasim.library.models.Buku;
import com.android.mobile.pasim.library.models.Peminjaman;
import com.android.mobile.pasim.library.models.TAGS;
import com.android.mobile.pasim.library.models.URLFunctions;
import com.android.mobile.pasim.library.utils.AppController;
import com.android.mobile.pasim.library.utils.SessionManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ListPeminjamanActivity extends Activity {

	private static final String TAG = ListPeminjamanActivity.class
			.getSimpleName();
	private ProgressDialog progressDialog;
	private List<Buku> bukuList = new ArrayList<Buku>();
	private ListView listView;
	private BukuListAdapter bukuListAdapter;

	private SessionManager sessionManager;
	private String nimnik;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_in_progress);

		listView = (ListView) findViewById(R.id.list_buku);
		bukuListAdapter = new BukuListAdapter(this, bukuList);
		bukuListAdapter.setCounter(2);
		listView.setAdapter(bukuListAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				peminjamanDetail(view, position);
			}
		});

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading...");
		progressDialog.show();

		sessionManager = new SessionManager(getApplicationContext());
		HashMap<String, String> hashMap = sessionManager.getUserDetails();
		nimnik = hashMap.get(SessionManager.KEY_USERNAME);

		JsonArrayRequest req = new JsonArrayRequest(
				URLFunctions.URLPEMINJAMANJSON + nimnik,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						hideProgressDialog();
						for (int i = 0; i < response.length(); i++) {
							try {
								JSONObject jsonObject = response
										.getJSONObject(i);
								Buku buku = new Buku();
								buku.setIdTrxPeminjaman(jsonObject
										.getString(TAGS.TAG_ID_TRANSAKSI));
								buku.setJudul_buku(jsonObject
										.getString(TAGS.TAG_JUDUL_BUKU));
								buku.setCoverUrl(jsonObject
										.getString(TAGS.TAG_COVER));
								buku.setTgl_pinjam(jsonObject
										.getString(TAGS.TAG_TGL_PINJAM));
								buku.setTgl_harus_kembali(jsonObject
										.getString(TAGS.TAG_TGL_HARUS_KEMBALI));
								bukuList.add(buku);
							} catch (JSONException e) {
								e.printStackTrace();
								Log.d("error nya : " ,e.getMessage());
//								Toast.makeText(getApplicationContext(),
//										"Error: " + e.getMessage(),
//										Toast.LENGTH_LONG).show();
							}
						}
						bukuListAdapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d(TAG, "Error: " + error.getMessage());
//						Toast.makeText(getApplicationContext(),
//								"Error Response : " + error.getMessage(),
//								Toast.LENGTH_SHORT).show();
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

	public void peminjamanDetail(View view, int position) {
		view.getParent();
		Intent intent = new Intent(getApplicationContext(),
				DetailPeminjamanActivity.class);
		Peminjaman p = new Peminjaman();
		p.setIdTransaksi(bukuList.get(position).getIdTrxPeminjaman());
		intent.putExtra(TAGS.TAG_ID_TRANSAKSI, p.getIdTransaksi());
		startActivity(intent);
	}
}
