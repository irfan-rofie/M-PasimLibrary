package com.android.mobile.pasim.library.adapter;

import java.util.List;

import com.android.mobile.pasim.library.R;
import com.android.mobile.pasim.library.models.Anggota;
import com.android.mobile.pasim.library.utils.AppController;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AnggotaListAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private List<Anggota> anggotaItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public AnggotaListAdapter(Activity activity, List<Anggota> anggotaItems) {
		this.activity = activity;
		this.anggotaItems = anggotaItems;
	}

	@Override
	public int getCount() {
		return anggotaItems.size();
	}

	@Override
	public Object getItem(int location) {
		return anggotaItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row_anggota, null);
		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.thumbnail);

		TextView nama = (TextView) convertView.findViewById(R.id.nama);
		TextView nimNik = (TextView) convertView.findViewById(R.id.nimnik);
		TextView jurKet = (TextView) convertView.findViewById(R.id.jurket);
		TextView tahunMasuk = (TextView) convertView
				.findViewById(R.id.tahunMasuk);

		Anggota anggota = anggotaItems.get(position);


		thumbNail.setImageUrl(anggota.getThumbNailUrl(), imageLoader);

		nama.setText(anggota.getNama());
		nimNik.setText(anggota.getNimNik());
		jurKet.setText(anggota.getJurusan());
		tahunMasuk.setText(anggota.getTanggalMasuk());

		return convertView;
	}

}
