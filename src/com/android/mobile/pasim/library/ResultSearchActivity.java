package com.android.mobile.pasim.library;

import java.util.ArrayList;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ResultSearchActivity extends Activity {

	private static final String TAG = ResultSearchActivity.class
			.getSimpleName();
	private ProgressDialog progressDialog;
	private List<Buku> bukuList = new ArrayList<Buku>();
	private ListView listView;
	private BukuListAdapter bukuListAdapter;
	private String getFieldBefore, getFieldAfter;
	private String getValue;
	// private String judulBuku;
	private String url;
	// private int id;

	private AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor(getString(R.color.actionBar_color))));
		actionBar
				.setTitle(Html
						.fromHtml("<font weight='bold' color='#fe0000'>HASIL PENCARIAN</font>"));
		actionBar.setIcon(R.drawable.circle_buku);
		actionBar.setDisplayHomeAsUpEnabled(true);

		listView = (ListView) findViewById(R.id.list_buku);
		bukuListAdapter = new BukuListAdapter(this, bukuList);
		listView.setAdapter(bukuListAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				view.getParent();
				Intent i = new Intent(ResultSearchActivity.this,
						DetailBukuActivity.class);
				Buku b = new Buku();
				b.setKode_buku(bukuList.get(position).getKode_buku());
				b.setJudul_buku(bukuList.get(position).getJudul_buku());
				b.setKategori(bukuList.get(position).getKategori());
				b.setPengarang(bukuList.get(position).getPengarang());
				b.setPenerbit(bukuList.get(position).getPenerbit());
				b.setJumlah_buku(bukuList.get(position).getJumlah_buku());
				b.setCoverUrl(bukuList.get(position).getCoverUrl());
				b.setTahun_terbit(bukuList.get(position).getTahun_terbit());

				i.putExtra(TAGS.TAG_KODE_BUKU, b.getKode_buku());
				i.putExtra(TAGS.TAG_JUDUL_BUKU, b.getJudul_buku());
				i.putExtra(TAGS.TAG_KATEGORI, b.getKategori());
				i.putExtra(TAGS.TAG_PENGARANG, b.getPengarang());
				i.putExtra(TAGS.TAG_PENERBIT, b.getPenerbit());
				i.putExtra(TAGS.TAG_JUMLAH, b.getJumlah_buku());
				i.putExtra(TAGS.TAG_COVER, b.getCoverUrl());
				i.putExtra(TAGS.TAG_THN_TERBIT, b.getTahun_terbit());
				startActivity(i);
			}
		});

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Mencari ...");
		progressDialog.show();

		Intent it = getIntent();
		// id = Integer.parseInt(it.getStringExtra(TAGS.TAG_ID));

		// if (id == 1) {
		getFieldBefore = it.getStringExtra(TAGS.TAG_FIELD);
		if (getFieldBefore.contains(" ")) {
			getFieldAfter = getFieldBefore.replace(" ", "%20");
		} else {
			getFieldAfter = getFieldBefore;
		}
		getValue = it.getStringExtra(TAGS.TAG_VALUE);
		url = URLFunctions.URLCARIBUKU + getFieldAfter + TAGS.TAG_AND_VALUE
				+ getValue;
		// } else {
		// judulBuku = it.getStringExtra(TAGS.TAG_JUDUL_BUKU);
		// url = URLFunctions.URLCARIBUKUBYJUDUL + judulBuku;
		// }

		Log.d("URL", url);

		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						Log.d(TAG, "panjang Array : " + response.length());
						if (response.length() > 0) {
							for (int i = 0; i < response.length(); i++) {
								try {
									JSONObject jsonObject = response
											.getJSONObject(i);
									Buku buku = new Buku();
									buku.setJudul_buku(jsonObject
											.getString(TAGS.TAG_JUDUL_BUKU));
									buku.setKategori(jsonObject
											.getString(TAGS.TAG_KATEGORI));
									buku.setPengarang(jsonObject
											.getString(TAGS.TAG_PENGARANG));
									buku.setJumlah_buku(jsonObject
											.getInt(TAGS.TAG_JUMLAH));
									buku.setCoverUrl(jsonObject
											.getString(TAGS.TAG_COVER));
									buku.setKode_buku(jsonObject
											.getString(TAGS.TAG_KODE_BUKU));
									buku.setPenerbit(jsonObject
											.getString(TAGS.TAG_PENERBIT));
									buku.setTahun_terbit(jsonObject
											.getString(TAGS.TAG_THN_TERBIT));
									bukuList.add(buku);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							hideProgressDialog();
						} else {
							alert.showAlertDialogResult(
									ResultSearchActivity.this, "Perhatian",
									"Data Tidak Ditemukan", true);
						}
						bukuListAdapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError errorResponse) {
						Log.d(TAG,
								"Error, Karena : " + errorResponse.getMessage());
						hideProgressDialog();
					}
				});
		AppController.getInstance().addToRequestQueue(jsonArrayRequest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.reload, menu);

		// SearchManager searchManager = (SearchManager)
		// getSystemService(Context.SEARCH_SERVICE);
		// SearchView searchView = (SearchView) menu.findItem(R.id.quickSearch)
		// .getActionView();
		// searchView.setSearchableInfo(searchManager
		// .getSearchableInfo(getComponentName()));

		// return super.onCreateOptionsMenu(menu);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// case R.id.quickSearch:
		// return true;
		// case R.id.reload:
		// refreshMenuItem = item;
		// new SyncData().execute();
		// return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		hideProgressDialog();
	}

	private void hideProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	// private class SyncData extends AsyncTask<String, Void, String> {
	// @Override
	// protected void onPreExecute() {
	// // set the progress bar view
	// refreshMenuItem.setActionView(R.layout.action_progressbar);
	//
	// refreshMenuItem.expandActionView();
	// }
	//
	// @Override
	// protected String doInBackground(String... params) {
	// // not making real request in this demo
	// // for now we use a timer to wait for sometime
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
	// // remove the progress bar view
	// refreshMenuItem.setActionView(null);
	// }
	// };

}
