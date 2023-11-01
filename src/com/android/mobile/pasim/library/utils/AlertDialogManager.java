package com.android.mobile.pasim.library.utils;

import com.android.mobile.pasim.library.BukuActivity;
import com.android.mobile.pasim.library.DetailBukuActivity;
import com.android.mobile.pasim.library.HomeActivity;
import com.android.mobile.pasim.library.ListPesananActivity;
import com.android.mobile.pasim.library.LoginActivity;
import com.android.mobile.pasim.library.MainActivity;
import com.android.mobile.pasim.library.R;
import com.android.mobile.pasim.library.ResultSearchActivity;
import com.android.mobile.pasim.library.ResultSearchActivityByJudul;
import com.android.mobile.pasim.library.UbahPasswordActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class AlertDialogManager extends Activity {

	SessionManager sessionManager;

	public void showAlertDialogDetect(final Context context, String title,
			String message, Boolean status) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setIcon(R.drawable.exit);
		alertDialog.setPositiveButton("Ya",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						MainActivity mainActivity = (MainActivity) context;
						mainActivity.finish();
					}
				});
		alertDialog.show();
	}

	public void showAlertDialogLogout(final Context context, String title,
			String message, Boolean status) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setIcon(R.drawable.exit);
		alertDialog.setPositiveButton("Ya",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sessionManager = new SessionManager(context);
						sessionManager.logoutUser();
						Intent intent = new Intent(context, LoginActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
						HomeActivity homeActivity = (HomeActivity) context;
						homeActivity.finish();
					}
				});
		alertDialog.setNegativeButton("Tidak",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alertDialog.show();
	}

	public void showAlertDialogCekPesananPeminjaman(final Context context,
			String title, String message, boolean status) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setIcon(R.drawable.warning);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.show();
	}

	public void showAlertDialogLogin(final Context context, String title,
			String message, final boolean status) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setIcon(status == true ? R.drawable.success : R.drawable.wrong);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent loginIntent;
				if (status == true) {
					loginIntent = new Intent(context, HomeActivity.class);
				} else {
					loginIntent = new Intent(context, LoginActivity.class);
				}
				loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(loginIntent);
				LoginActivity loginActivity = (LoginActivity) context;
				loginActivity.finish();
			}
		});
		builder.show();
	}

	public void showAlertDialogUbahPassword(final Context context,
			String title, String message, final boolean status) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setIcon(status == true ? R.drawable.success : R.drawable.wrong);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (status == false) {
					dialog.cancel();
				} else {
					UbahPasswordActivity activity = (UbahPasswordActivity) context;
					activity.finish();
				}
			}
		});
		builder.show();
	}

	public void showAlertDialogBooking(final Context context, String title,
			String message, final boolean status) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setIcon(status == true ? R.drawable.success : R.drawable.wrong);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DetailBukuActivity activity = (DetailBukuActivity) context;
				activity.finish();
			}
		});
		builder.show();
	}

	public void showAlertDialogResult(final Context context, String title,
			String message, final boolean status) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setIcon(status == true ? R.drawable.warning : R.drawable.wrong);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (status == false) {
					dialog.cancel();
				} else {
					ResultSearchActivity activity = (ResultSearchActivity) context;
					activity.finish();
				}
			}
		});
		builder.show();
	}

	public void showAlertDialogCancelBooking(final Context context,
			String title, String message, final boolean status) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setIcon(status == true ? R.drawable.success : R.drawable.wrong);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (status == true) {
					Intent intent = new Intent(context,
							ListPesananActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
					ListPesananActivity activity = (ListPesananActivity) context;
					activity.finish();
				} else {
					dialog.cancel();
				}
			}
		});
		builder.show();
	}

	public void showAlertDialogSearchByJudulEmpty(final Context context,
			String title, String message, final boolean status) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setIcon(status == true ? R.drawable.success : R.drawable.wrong);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(context, BukuActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
				ResultSearchActivityByJudul activity = (ResultSearchActivityByJudul) context;
				activity.finish();
			}
		});
		builder.show();
	}
}