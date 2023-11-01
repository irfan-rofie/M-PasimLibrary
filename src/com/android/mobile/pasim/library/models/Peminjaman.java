package com.android.mobile.pasim.library.models;

public class Peminjaman {

	private String idTransaksi, namaAnggota, judulBuku, tglPinjam,
			tglHarusKembali, tglKembali, statusPeminjaman, telat, denda, cover;

	public String getIdTransaksi() {
		return idTransaksi;
	}

	public void setIdTransaksi(String idTransaksi) {
		this.idTransaksi = idTransaksi;
	}

	public String getNamaAnggota() {
		return namaAnggota;
	}

	public void setNamaAnggota(String namaAnggota) {
		this.namaAnggota = namaAnggota;
	}

	public String getJudulBuku() {
		return judulBuku;
	}

	public void setJudulBuku(String judulBuku) {
		this.judulBuku = judulBuku;
	}

	public String getTglPinjam() {
		return tglPinjam;
	}

	public void setTglPinjam(String tglPinjam) {
		this.tglPinjam = tglPinjam;
	}

	public String getTglHarusKembali() {
		return tglHarusKembali;
	}

	public void setTglHarusKembali(String tglHarusKembali) {
		this.tglHarusKembali = tglHarusKembali;
	}

	public String getTglKembali() {
		return tglKembali;
	}

	public void setTglKembali(String tglKembali) {
		this.tglKembali = tglKembali;
	}

	public String getStatusPeminjaman() {
		return statusPeminjaman;
	}

	public void setStatusPeminjaman(String statusPeminjaman) {
		this.statusPeminjaman = statusPeminjaman;
	}

	public String getTelat() {
		return telat;
	}

	public void setTelat(String telat) {
		this.telat = telat;
	}

	public String getDenda() {
		return denda;
	}

	public void setDenda(String denda) {
		this.denda = denda;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

}
