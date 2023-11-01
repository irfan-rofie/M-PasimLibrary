package com.android.mobile.pasim.library.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.android.mobile.pasim.library.R;
import com.android.mobile.pasim.library.models.Buku;
import com.android.mobile.pasim.library.utils.AppController;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BukuListAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater layoutInflater;
	private List<Buku> bukuItems;
	private ArrayList<Buku> arrayList;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private TextView idPemesanan;
	private int counter;

	public BukuListAdapter(Activity activity, List<Buku> bukuItems) {
		this.activity = activity;
		this.bukuItems = bukuItems;
		// layoutInflater = LayoutInflater.from(activity);
		// this.arrayList = new ArrayList<Buku>();
		// this.arrayList.addAll(bukuItems);
		this.arrayList = (ArrayList<Buku>) bukuItems;
		Log.d("list nya : ", this.bukuItems.toString());
		Log.d("array list nya : ", arrayList.toString());
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Override
	public int getCount() {
		return bukuItems.size();
	}

	@Override
	public Object getItem(int location) {
		return bukuItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		int[] colors = new int[] { 0x30FF0000, 0x300000FF };
		int colorPos = position % colors.length;

		if (layoutInflater == null)
			layoutInflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = layoutInflater.inflate(R.layout.list_row_buku, null);
		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView coverBuku = (NetworkImageView) convertView
				.findViewById(R.id.coverBuku);
		TextView judulBuku = (TextView) convertView
				.findViewById(R.id.judulBuku);
		TextView kategoriBuku = (TextView) convertView
				.findViewById(R.id.kategoriBuku);
		TextView pengarang = (TextView) convertView
				.findViewById(R.id.pengarang);
		TextView jumlahBuku = (TextView) convertView
				.findViewById(R.id.jumlahBuku);

		if (getCounter() == 1) {
			idPemesanan = (TextView) convertView.findViewById(R.id.idPemesanan);
		}

		Buku buku = bukuItems.get(position);

		coverBuku.setImageUrl(buku.getCoverUrl(), imageLoader);
		judulBuku.setText(buku.getJudul_buku());
		kategoriBuku.setText(getCounter() == 2 ? buku.getTgl_pinjam() : buku
				.getKategori());
		pengarang.setText(getCounter() == 1 ? "" : getCounter() == 2 ? buku
				.getTgl_harus_kembali() : buku.getPengarang());
		jumlahBuku.setText(getCounter() == 1 ? "Waktu Pesan : "
				+ String.valueOf(buku.getWaktu_pesan())
				: getCounter() == 2 ? "" : "Stok Tersedia : "
						+ String.valueOf(buku.getJumlah_buku()));

		if (getCounter() == 1) {
			idPemesanan.setText(buku.getIdPemesanan());
		}

		convertView.setBackgroundColor(colors[colorPos]);
		return convertView;
	}

	public void filter(String charText) {
		Log.d("Array Lis Nya : ", arrayList.toString());
		charText = charText.toLowerCase(Locale.getDefault());
		bukuItems.clear();
		if (charText.length() == 0) {
			bukuItems.addAll(arrayList);
		} else {
			for (Buku bk : arrayList) {
				if (bk.getJudul_buku().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					bukuItems.add(bk);
				}
			}
		}
		notifyDataSetChanged();
	}
}
