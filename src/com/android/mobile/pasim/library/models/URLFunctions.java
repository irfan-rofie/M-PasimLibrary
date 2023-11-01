package com.android.mobile.pasim.library.models;

public class URLFunctions {

	// ip psso 10.102.103.34
	// ip local 192.168.56.1
	// dikc psso 192.168.10.55
	// server www.atpsbusiness.com:86

	public static final String IPCONFIG = "www.atpsbusiness.com:86";
	public static final String URLDEFAULT = "http://" + IPCONFIG
			+ "/APP_PERPUS/mobile/";

	public static final String URLLOGIN = URLDEFAULT + "login.php?"
			+ TAGS.TAG_NIM + "=";
	public static final String URLGETANGGOTAJSON = URLDEFAULT
			+ "anggotajson.php";
	public static final String URLGETBUKUJSON = URLDEFAULT + "bukujson.php?ip="
			+ IPCONFIG;
	public static final String URLKATEGORIJSON = URLDEFAULT
			+ "kategorijson.php";
	public static final String URLCARIBUKU = URLDEFAULT
			+ "caribukujson.php?ip=" + IPCONFIG + TAGS.TAG_AND_FIELD;
	public static final String URLCARIBUKUBYJUDUL = URLDEFAULT
			+ "caribukubyjuduljson.php?ip=" + IPCONFIG + TAGS.TAG_AND_JUDUL;
	public static final String URLBOOKING = URLDEFAULT + "booking.php";
	public static final String URLCEKBOOKING = URLDEFAULT + "cekBooking.php";
	public static final String URLCEKPEMINJAMAN = URLDEFAULT
			+ "cekPeminjaman.php";
	public static final String URLPESANANJSON = URLDEFAULT
			+ "pesananjson.php?ip=" + IPCONFIG + "&" + TAGS.TAG_NIM + "=";
	public static final String URLPEMINJAMANJSON = URLDEFAULT
			+ "peminjaman.php?ip=" + IPCONFIG + "&" + TAGS.TAG_NIM + "=";
	public static final String URLPEMINJAMANDONE = URLDEFAULT
			+ "peminjamandone.php?ip=" + IPCONFIG + "&" + TAGS.TAG_NIM + "=";
	public static final String URLPEMINJAMANDETAIL = URLDEFAULT
			+ "peminjamandetail.php?ip=" + IPCONFIG + "&"
			+ TAGS.TAG_ID_TRANSAKSI + "=";
	public static final String URLGETUSERPROFILE = URLDEFAULT
			+ "userprofile.php?ip=" + IPCONFIG + "&" + TAGS.TAG_NIM + "=";
	public static final String URLUBAHPASSWORD = URLDEFAULT
			+ "ubahpassword.php?" + TAGS.TAG_NIM + "=";
	public static final String URLCANCELPEMESANAN = URLDEFAULT
			+ "cancelPemesanan.php?" + TAGS.TAG_ID_PEMESANAN + "=";
}
