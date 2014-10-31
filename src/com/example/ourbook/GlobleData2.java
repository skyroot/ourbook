package com.example.ourbook;

import java.util.List;

import org.apache.http.cookie.Cookie;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


public class GlobleData2 {
	
	// �жϵ�¼״̬
	public static Boolean flag = false;

	public static String cookies;

	public static void showToast(Context c, String s) {
		Toast.makeText(c, s, Toast.LENGTH_LONG).show();

	}

	public static boolean hasInternet(Activity a) {
		ConnectivityManager manager = (ConnectivityManager) a
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info == null || !info.isConnected()) {
			return false;
		}
		if (info.isRoaming()) {
			return true;
		}
		return true;
	}
}
