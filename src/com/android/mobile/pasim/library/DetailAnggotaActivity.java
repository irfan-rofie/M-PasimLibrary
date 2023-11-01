package com.android.mobile.pasim.library;

import com.android.mobile.pasim.library.models.Anggota;
import com.android.mobile.pasim.library.models.TAGS;
import com.android.mobile.pasim.library.utils.AppController;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

public class DetailAnggotaActivity extends Activity {

	private TextView tv_nimNik, tv_namaAgt, tv_jenKel, tv_ttl, tv_jurusan,
			tv_keterangan, tv_alamat, tv_noKontak, tv_tglMasuk, tv_status;
	private NetworkImageView iv_foto;
	private ImageLoader imageLoader = AppController.getInstance()
			.getImageLoader();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_anggota);

		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#4396e6")));
		getActionBar().setTitle("Detail Anggota");
		getActionBar().setIcon(R.drawable.anggota);

		tv_nimNik = (TextView) findViewById(R.id.nim_nik);
		tv_namaAgt = (TextView) findViewById(R.id.nama_anggota);

		try {
			Intent intent = getIntent();
			Anggota agt = new Anggota();
			agt.setNimNik(intent.getStringExtra(TAGS.TAG_NIM_NIK));
			agt.setNama(intent.getStringExtra(TAGS.TAG_NAMA_ANGOOTA));
			agt.setJenKel(intent.getStringExtra(TAGS.TAG_JEN_KEL));
			agt.setTtl(intent.getStringExtra(TAGS.TAG_TMP_TGL_LAHIR));
			agt.setJurusan(intent.getStringExtra(TAGS.TAG_JURUSAN));
			agt.setKeterangan(intent.getStringExtra(TAGS.TAG_KETERANGAN));
			agt.setAlamat(intent.getStringExtra(TAGS.TAG_ALAMAT));
			agt.setNoKontak(intent.getStringExtra(TAGS.TAG_NO_KONTAK));
			agt.setTanggalMasuk(intent.getStringExtra(TAGS.TAG_TGL_MASUK));
			agt.setStatus(intent.getStringExtra(TAGS.TAG_STATUS));
			agt.setThumbNailUrl(intent.getStringExtra(TAGS.TAG_FOTO));

			tv_nimNik.setText(agt.getNimNik());
			tv_namaAgt.setText(agt.getNama());
			tv_jenKel.setText(agt.getJenKel());
			tv_ttl.setText(agt.getTtl());
			tv_jurusan.setText(agt.getJurusan());
			tv_keterangan.setText(agt.getKeterangan());
			tv_alamat.setText(agt.getAlamat());
			tv_noKontak.setText(agt.getNoKontak());
			tv_tglMasuk.setText(agt.getTanggalMasuk());
			tv_status.setText(agt.getStatus());
			iv_foto.setImageUrl(agt.getThumbNailUrl(), imageLoader);
		} catch (Exception e) {
		}

	}
}
