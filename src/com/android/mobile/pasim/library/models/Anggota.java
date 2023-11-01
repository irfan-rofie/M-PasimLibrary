package com.android.mobile.pasim.library.models;

public class Anggota {

	private String nama, thumbNailUrl, tanggalMasuk, noKontak, nimNik, jenKel,
			ttl, jurusan, keterangan, alamat, status, jurKet;

	public Anggota() {
	}

	public Anggota(String nama, String thumbNailUrl, String tanggalMasuk,
			String noKontak, String nimNik) {
		this.nama = nama;
		this.thumbNailUrl = thumbNailUrl;
		this.tanggalMasuk = tanggalMasuk;
		this.noKontak = noKontak;
		this.nimNik = nimNik;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getThumbNailUrl() {
		return thumbNailUrl;
	}

	public void setThumbNailUrl(String thumbNailUrl) {
		this.thumbNailUrl = thumbNailUrl;
	}

	public String getTanggalMasuk() {
		return tanggalMasuk;
	}

	public void setTanggalMasuk(String tanggalMasuk) {
		this.tanggalMasuk = tanggalMasuk;
	}

	public String getNoKontak() {
		return noKontak;
	}

	public void setNoKontak(String noKontak) {
		this.noKontak = noKontak;
	}

	public String getNimNik() {
		return nimNik;
	}

	public void setNimNik(String nimNik) {
		this.nimNik = nimNik;
	}

	public String getJenKel() {
		return jenKel;
	}

	public void setJenKel(String jenKel) {
		this.jenKel = jenKel;
	}

	public String getTtl() {
		return ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	public String getJurusan() {
		return jurusan;
	}

	public void setJurusan(String jurusan) {
		this.jurusan = jurusan;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJurKet() {
		return jurKet;
	}

	public void setJurKet(String jurKet) {
		this.jurKet = jurKet;
	}

}
