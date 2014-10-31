package com.example.ourbook;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

//Androidʵ�ֿ����������������ƶ�����ϵͳ����򵥵��ˣ�����ֻ��Ҫ����һ������������Broadcast���㲥�����ɡ�
//����дһ��Receiver�����㲥�����������̳�BroadcastReceiver
//������������ֻ��Ҫ��Ӧ�ó��������ļ�AndroidManifest.xml��ע�����Receiver������ϵͳ�����¼�����
//ReturnBookService.class��ָҪ����������һ��service


public class BootReceiver extends BroadcastReceiver {  
	 private PendingIntent mAlarmSender;  
	@Override  
	 public void onReceive(Context context, Intent intent) { 
		
	// �����������ɵ��£�����һ��Service��Activity�ȣ�������������һ����ʱ���ȳ���ÿ30��������һ��Serviceȥ��������  
	mAlarmSender = PendingIntent.getService(context, 0, new Intent(context,  
	                                      ReturnBookService.class), 0);  
	long firstTime = SystemClock.elapsedRealtime();  
	AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);  
	am.cancel(mAlarmSender);  
	am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime,  
	                 60 * 60 * 1000, mAlarmSender);  
	    }  
	} 