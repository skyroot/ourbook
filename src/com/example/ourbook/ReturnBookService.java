package com.example.ourbook;

import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


//ReturnBookService.class是指要定期启动的一个service，
//里面包含了新增一个子线程，专门用作登录图书馆，获取所借图书信息list，判断某本书是否超期，则调用提醒方法notify



public class ReturnBookService extends Service{
	
	public void onCreate()
    {
        super.onCreate();
    }
	public void new_notice(String s, String s1, Intent intent)
    {
        //NotificationManager：是状态栏通知的管理类，负责发通知、清楚通知等。 NotificationManager是一个系统Service，必须通过getSystemService()方法来获取。 
		NotificationManager notificationmanager = (NotificationManager)getSystemService("notification");
        
		//是具体的状态栏通知对象，可以设置icon、文字、提示声音、振动等等参数。 下面是设置一个通知需要的基本参数：
		//An icon(通知的图标) Atitleandexpandedmessage(通知的标题和内容) APendingIntent(点击通知执行页面跳转) 
		Notification notification = new Notification(R.drawable.icon, getResources().getString(R.string.new_message), System.currentTimeMillis());
        notification.flags = 16;
        notification.defaults = 6;
        intent.setFlags(0x14000000);
        notification.setLatestEventInfo(this, s, s1, PendingIntent.getActivity(this, R.string.app_name, intent, 0x8000000));
        notificationmanager.notify(0x7f090024, notification);
    }
	
	public int onStartCommand(Intent intent, int flags, int startId)
    {
		new Thread(new Runnable() {

            public void run()
            {
            	Map map = null;
                JsoupUtil.Loginurl(number, passwd);
                list = JsoupUtil.getMyBooks(LibAPI.cookie);*/
                try
                {
                    if (((Boolean)map.get("is_onoverdate")).booleanValue())
                    {
                        new_notice("图书即将超期", "你有图书即将超期，请注意", new Intent(ReturnBookService.this, BorrowedActivity.class));
                        return;
                    }
                    else if (((Boolean)map.get("is_overdate")).booleanValue())
                    {
                        new_notice("图书已超期", "你有超期图书，请注意", new Intent(ReturnBookService.this, BorrowedActivity.class));
                        return;
                    }
                }
                catch (Exception exception)
                {
                    Log.d("RETURN_BOOK_NOTICE", "parse return_date error");
                }
            	
            }
		}).start();//Thread结束
		
		return super.onStartCommand(intent,flags,startId);
    }
	
	public IBinder onBind(Intent intent)
    {
        return null;
    }
	
}
