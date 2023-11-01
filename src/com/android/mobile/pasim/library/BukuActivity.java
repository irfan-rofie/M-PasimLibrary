package com.android.mobile.pasim.library;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mobile.pasim.library.adapter.BukuListAdapter;
import com.android.mobile.pasim.library.models.Buku;
import com.android.mobile.pasim.library.models.TAGS;
import com.android.mobile.pasim.library.models.URLFunctions;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BukuActivity extends Activity {

	private static final String TAG = BukuActivity.class.getSimpleName();
	private ProgressDialog progressDialog;
	private ArrayList<Buku> bukuList = new ArrayList<Buku>();
	private ListView listView;
	private BukuListAdapter bukuListAdapter;
	private AutoCompleteTextView editSearch;
	private Button btncar;
	@SuppressWarnings("rawtypes")
	private ArrayAdapter arrayAdapter;
	private ArrayList<String> judulBuku;
	private String judul;
	@SuppressWarnings("unused")
	private View focus;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buku);

		judulBuku = new ArrayList<String>();

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor(getString(R.color.actionBar_color))));
		actionBar
				.setTitle(Html
						.fromHtml("<font weight='bold' color='#fe0000'>BUKU</font>"));
		actionBar.setIcon(R.drawable.circle_buku);
		actionBar.setDisplayHomeAsUpEnabled(true);

		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
				URLFunctions.URLGETBUKUJSON,
				new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						hideProgressDialog();
						for (int i = 0; i < response.length(); i++) {
							try {
								JSONObject jsonObject = response
										.getJSONObject(i);
								String bk = jsonObject
										.getString(TAGS.TAG_JUDUL_BUKU);
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
								buku.setResensi(jsonObject
										.getString(TAGS.TAG_RESENSI));
								buku.setRak(jsonObject.getString(TAGS.TAG_RAK));
								bukuList.add(buku);
								judulBuku.add(bk);
							} catch (JSONException e) {
								e.printStackTrace();
								android.util.Log.d("Err : ", e.getMessage());
							}
						}
						bukuListAdapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError errorResponse) {
//						Toast.makeText(
//								getApplicationContext(),
//								"Error Response : "
//										+ errorResponse.getMessage(),
//								Toast.LENGTH_SHORT).show();
						Log.d(TAG,
								"Error, Karena : " + errorResponse.getMessage());
						hideProgressDialog();
					}
				});

		AppController.getInstance().addToRequestQueue(jsonArrayRequest);

		listView = (ListView) findViewById(R.id.list_buku);
		bukuListAdapter = new BukuListAdapter(this, bukuList);
		listView.setAdapter(bukuListAdapter);

		arrayAdapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, judulBuku);
		editSearch = (AutoCompleteTextView) findViewById(R.id.search);
		editSearch.setAdapter(arrayAdapter);

		btncar = (Button) findViewById(R.id.btn_car);
		btncar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				judul = editSearch.getText().toString();
				if (TextUtils.isEmpty(judul)) {
					editSearch.setError("Harus di isi");
					focus = editSearch;
				} else {
					editSearch.setText(null);
					Intent ite = new Intent(BukuActivity.this,
							ResultSearchActivityByJudul.class);
					// ite.putExtra(TAGS.TAG_ID, "2");
					ite.putExtra(TAGS.TAG_JUDUL_BUKU, judul);
					startActivity(ite);
				}
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				view.getParent();
				Intent i = new Intent(BukuActivity.this,
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
				b.setResensi(bukuList.get(position).getResensi());
				b.setRak(bukuList.get(position).getRak());

				i.putExtra(TAGS.TAG_KODE_BUKU, b.getKode_buku());
				i.putExtra(TAGS.TAG_JUDUL_BUKU, b.getJudul_buku());
				i.putExtra(TAGS.TAG_KATEGORI, b.getKategori());
				i.putExtra(TAGS.TAG_PENGARANG, b.getPengarang());
				i.putExtra(TAGS.TAG_PENERBIT, b.getPenerbit());
				i.putExtra(TAGS.TAG_JUMLAH, b.getJumlah_buku());
				i.putExtra(TAGS.TAG_COVER, b.getCoverUrl());
				i.putExtra(TAGS.TAG_THN_TERBIT, b.getTahun_terbit());
				i.putExtra(TAGS.TAG_RESENSI, b.getResensi());
				i.putExtra(TAGS.TAG_RAK, b.getRak());
				startActivity(i);
			}
		});

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading...");
		progressDialog.show();

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		// SearchManager searchManager = (SearchManager)
		// getSystemService(Context.SEARCH_SERVICE);
		// SearchView searchView = (SearchView) menu.findItem(R.id.quickSearch)
		// .getActionView();
		// searchView.setSearchableInfo(searchManager
		// .getSearchableInfo(getComponentName()));

		 return super.onCreateOptionsMenu(menu);
//		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// case R.id.quickSearch:
		// return true;
		case R.id.advance:
			Intent in = new Intent(BukuActivity.this,
					AdvanceSearchActivity.class);
			startActivity(in);
			return true;
			// case R.id.reload:
			// refreshMenuItem = item;
			// new SyncData().execute();
			// return true;
		default:
			return super.onOptionsItemSelected(item);
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
