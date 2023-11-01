package com.android.mobile.pasim.library;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mobile.pasim.library.adapter.AnggotaListAdapter;
import com.android.mobile.pasim.library.models.Anggota;
import com.android.mobile.pasim.library.models.TAGS;
import com.android.mobile.pasim.library.models.URLFunctions;
import com.android.mobile.pasim.library.utils.AppController;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class AnggotaActivity extends Activity {

	private static final String TAG = AnggotaActivity.class.getSimpleName();
	private ProgressDialog progressDialog;
	private List<Anggota> anggotaList = new ArrayList<Anggota>();
	private ListView listView;
	private AnggotaListAdapter anggotaListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anggota);

		listView = (ListView) findViewById(R.id.list_anggota);
		anggotaListAdapter = new AnggotaListAdapter(this, anggotaList);
		listView.setAdapter(anggotaListAdapter);

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading...");
		progressDialog.show();

		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#4396e6")));

		getActionBar().setTitle(R.string.list_anggota);
		getActionBar().setIcon(R.drawable.anggota);

		JsonArrayRequest arrayRequest = new JsonArrayRequest(
				URLFunctions.URLGETANGGOTAJSON,
				new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						hideProgressDialog();

						for (int i = 0; i < response.length(); i++) {
							try {
								JSONObject jsonObject = response
										.getJSONObject(i);
								Anggota anggota = new Anggota();
								anggota.setNama(jsonObject
										.getString(TAGS.TAG_NAMA_ANGOOTA));
								anggota.setJurusan(jsonObject
										.getString(TAGS.TAG_JUR_KET));
								anggota.setTanggalMasuk(jsonObject
										.getString(TAGS.TAG_TGL_MASUK));
								anggota.setNimNik(jsonObject
										.getString(TAGS.TAG_NIM_NIK));
								anggota.setThumbNailUrl(jsonObject
										.getString(TAGS.TAG_FOTO));
								anggotaList.add(anggota);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						anggotaListAdapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG,
								"Error, karena : " + error.getMessage());
						hideProgressDialog();
					}
				});

		AppController.getInstance().addToRequestQueue(arrayRequest);

/*		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				view.getParent();
				Intent intent = new Intent(AnggotaActivity.this,
						DetailBukuActivity.class);

				Anggota a = new Anggota();

				a.setNimNik(anggotaList.get(position).getNimNik());
				a.setNama(anggotaList.get(position).getNama());

				intent.putExtra(TAGS.TAG_NIM_NIK, a.getNimNik());
				intent.putExtra(TAGS.TAG_NAMA_ANGOOTA, a.getNama());

				Toast.makeText(getApplicationContext(),
						"Nama : " + a.getNimNik(), Toast.LENGTH_LONG).show();

				startActivity(intent);
			}
		}); */
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
