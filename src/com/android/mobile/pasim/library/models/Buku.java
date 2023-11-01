package com.android.mobile.pasim.library.models;

public class Buku {

	private String kode_buku, judul_buku, pengarang, penerbit, tahun_terbit,
			resensi, kategori, coverUrl, waktu_pesan, tgl_pinjam, rak,
			tgl_harus_kembali, idTrxPeminjaman, idPemesanan;
	private int jumlah_buku;

	 public Buku() {
	 }

	public Buku(String kode_buku, String judul_buku, String pengarang,
			String penerbit, String tahun_terbit, String resensi,
			String kategori, String coverUrl, String waktu_pesan,
			String tgl_pinjam, String rak, String tgl_harus_kembali,
			String idTrxPeminjaman, String idPemesanan, int jumlah_buku) {
//		super();
		this.kode_buku = kode_buku;
		this.judul_buku = judul_buku;
		this.pengarang = pengarang;
		this.penerbit = penerbit;
		this.tahun_terbit = tahun_terbit;
		this.resensi = resensi;
		this.kategori = kategori;
		this.coverUrl = coverUrl;
		this.waktu_pesan = waktu_pesan;
		this.tgl_pinjam = tgl_pinjam;
		this.rak = rak;
		this.tgl_harus_kembali = tgl_harus_kembali;
		this.idTrxPeminjaman = idTrxPeminjaman;
		this.jumlah_buku = jumlah_buku;
		this.idPemesanan = idPemesanan;
	}

	public String getKode_buku() {
		return kode_buku;
	}

	public void setKode_buku(String kode_buku) {
		this.kode_buku = kode_buku;
	}

	public String getJudul_buku() {
		return judul_buku;
	}

	public void setJudul_buku(String judul_buku) {
		this.judul_buku = judul_buku;
	}

	public String getPengarang() {
		return pengarang;
	}

	public void setPengarang(String pengarang) {
		this.pengarang = pengarang;
	}

	public String getPenerbit() {
		return penerbit;
	}

	public void setPenerbit(String penerbit) {
		this.penerbit = penerbit;
	}

	public String getTahun_terbit() {
		return tahun_terbit;
	}

	public void setTahun_terbit(String tahun_terbit) {
		this.tahun_terbit = tahun_terbit;
	}

	public String getResensi() {
		return resensi;
	}

	public void setResensi(String resensi) {
		this.resensi = resensi;
	}

	public String getKategori() {
		return kategori;
	}

	public void setKategori(String kategori) {
		this.kategori = kategori;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getWaktu_pesan() {
		return waktu_pesan;
	}

	public void setWaktu_pesan(String waktu_pesan) {
		this.waktu_pesan = waktu_pesan;
	}

	public int getJumlah_buku() {
		return jumlah_buku;
	}

	public void setJumlah_buku(int jumlah_buku) {
		this.jumlah_buku = jumlah_buku;
	}

	public String getTgl_pinjam() {
		return tgl_pinjam;
	}

	public void setTgl_pinjam(String tgl_pinjam) {
		this.tgl_pinjam = tgl_pinjam;
	}

	public String getTgl_harus_kembali() {
		return tgl_harus_kembali;
	}

	public void setTgl_harus_kembali(String tgl_harus_kembali) {
		this.tgl_harus_kembali = tgl_harus_kembali;
	}

	public String getIdTrxPeminjaman() {
		return idTrxPeminjaman;
	}

	public void setIdTrxPeminjaman(String idTrxPeminjaman) {
		this.idTrxPeminjaman = idTrxPeminjaman;
	}

	public String getRak() {
		return rak;
	}

	public void setRak(String rak) {
		this.rak = rak;
	}

	public String getIdPemesanan() {
		return idPemesanan;
	}

	public void setIdPemesanan(String idPemesanan) {
		this.idPemesanan = idPemesanan;
	}

}
