package com.example.ourbook;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

//Android实现开机自启动可能是移动操作系统中最简单的了，我们只需要监听一个开机启动的Broadcast（广播）即可。
//首先写一个Receiver（即广播监听器），继承BroadcastReceiver
//接下来，我们只需要在应用程序配置文件AndroidManifest.xml中注册这个Receiver来监听系统启动事件即可
//ReturnBookService.class是指要定期启动的一个service


public class BootReceiver extends BroadcastReceiver {  
	 private PendingIntent mAlarmSender;  
	@Override  
	 public void onReceive(Context context, Intent intent) { 
		
	// 在这里干你想干的事（启动一个Service，Activity等），本例是启动一个定时调度程序，每30分钟启动一个Service去更新数据  
	mAlarmSender = PendingIntent.getService(context, 0, new Intent(context,  
	                                      ReturnBookService.class), 0);  
	long firstTime = SystemClock.elapsedRealtime();  
	AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);  
	am.cancel(mAlarmSender);  
	am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime,  
	                 60 * 60 * 1000, mAlarmSender);  
	    }  
	} 