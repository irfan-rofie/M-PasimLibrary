package com.android.mobile.pasim.library;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mobile.pasim.library.models.Kategori;
import com.android.mobile.pasim.library.models.TAGS;
import com.android.mobile.pasim.library.models.URLFunctions;
import com.android.mobile.pasim.library.utils.AlertDialogManager;
import com.android.mobile.pasim.library.utils.ServiceHandler;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AdvanceSearchActivity extends Activity implements
		OnItemSelectedListener {

	private String field;
	private String value;
	private String param;
	private Spinner spinField;
	private EditText textValue;
	private List<String> list;
	private ArrayList<Kategori> kategoriList;
	private Button btnCari;
	private View focus;
	private AlertDialogManager alert = new AlertDialogManager();
	private ArrayAdapter<String> dataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor(getString(R.color.actionBar_color))));
		actionBar.setTitle(Html
				.fromHtml("<font weight='bold' color='#fe0000'>FILTER</font>"));
		actionBar.setIcon(R.drawable.search);
		actionBar.setDisplayHomeAsUpEnabled(true);

		spinField = (Spinner) findViewById(R.id.spinnerField);
		kategoriList = new ArrayList<Kategori>();
		spinField.setOnItemSelectedListener(this);

		// list.add(0, "----- Cari Berdasarkan -----");
		// list.add(1, "Kode Buku");
		// list.add(2, "Judul Buku");
		// list.add(3, "Pengarang");
		// list.add(4, "Penerbit");
		// list.add(5, "Tahun Terbit");
		// list.add(6, "Kategori");
		// list.add(7, "Rak");

		new GetCategories().execute();

		// for (int i = 0; i < kategoriList.size(); i++) {
		// list.add(kategoriList.get(i).getKategori());
		// }
		//
		// dataAdapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, list);
		// dataAdapter
		// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// spinField.setAdapter(dataAdapter);
		textValue = (EditText) findViewById(R.id.textValue);

		btnCari = (Button) findViewById(R.id.btn_cari);
		btnCari.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				prepareSearch();
			}
		});
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		field = parent.getItemAtPosition(position).toString();
	}

	public void prepareSearch() {
		value = textValue.getText().toString().trim();
		boolean salah = false;
		if (TextUtils.isEmpty(value)) {
			textValue.setError("Harus Diisi");
			focus = textValue;
			salah = true;
		}

		if (salah) {
			focus.requestFocus();
		} else {
			if (field.equals(list.get(0))) {
				alert.showAlertDialogResult(AdvanceSearchActivity.this,
						"Kesalahan", "Pilihan Pencarian Tidak Sesuai", false);
			} else {
				param = field;
				// if (field.equals(list.get(1))) {
				// param = TAGS.TAG_KODE_BUKU;
				// } else if (field.equals(list.get(2))) {
				// param = TAGS.TAG_JUDUL_BUKU;
				// } else if (field.equals(list.get(3))) {
				// param = TAGS.TAG_PENGARANG;
				// } else if (field.equals(list.get(4))) {
				// param = TAGS.TAG_PENERBIT;
				// } else if (field.equals(list.get(5))) {
				// param = TAGS.TAG_THN_TERBIT;
				// } else if (field.equals(list.get(6))) {
				// param = TAGS.TAG_KATEGORI;
				// } else if (field.equals(list.get(7))) {
				// param = TAGS.TAG_RAK;
				// }
				Search();
			}
		}
	}

	public void Search() {
		Intent intent = new Intent(AdvanceSearchActivity.this,
				ResultSearchActivity.class);
		// intent.putExtra(TAGS.TAG_ID, "1");
		intent.putExtra(TAGS.TAG_FIELD, param);
		intent.putExtra(TAGS.TAG_VALUE, value);
		startActivity(intent);
		// clearSearch();
	}

	public void clearSearch() {
		spinField.setSelection(0);
		textValue.setText("");
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	private void populateSpinner() {

		list = new ArrayList<String>();
		list.add(0, "----- Pilih Kategori -----");
		for (int i = 0; i < kategoriList.size(); i++) {
			list.add(i + 1, kategoriList.get(i).getKategori());
		}

		// Creating adapter for spinner
		dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinField.setAdapter(dataAdapter);
	}

	/**
	 * Async task to get all food categories
	 * */
	private class GetCategories extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// pDialog = new ProgressDialog(MainActivity.this);
			// pDialog.setMessage("Fetching food categories..");
			// pDialog.setCancelable(false);
			// pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(
					URLFunctions.URLKATEGORIJSON, ServiceHandler.GET);

			Log.e("Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {
						JSONArray jarray = jsonObj.getJSONArray("categories");
						for (int i = 0; i < jarray.length(); i++) {
							JSONObject catObj = (JSONObject) jarray.get(i);
							Kategori kat = new Kategori(
									catObj.getString("kategori"));
							kategoriList.add(kat);
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// if (pDialog.isShowing())
			// pDialog.dismiss();
			populateSpinner();
		}

	}

}
