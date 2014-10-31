package com.example.ourbook;

import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


//ReturnBookService.class��ָҪ����������һ��service��
//�������������һ�����̣߳�ר��������¼ͼ��ݣ���ȡ����ͼ����Ϣlist���ж�ĳ�����Ƿ��ڣ���������ѷ���notify



public class ReturnBookService extends Service{
	
	public void onCreate()
    {
        super.onCreate();
    }
	public void new_notice(String s, String s1, Intent intent)
    {
        //NotificationManager����״̬��֪ͨ�Ĺ����࣬����֪ͨ�����֪ͨ�ȡ� NotificationManager��һ��ϵͳService������ͨ��getSystemService()��������ȡ�� 
		NotificationManager notificationmanager = (NotificationManager)getSystemService("notification");
        
		//�Ǿ����״̬��֪ͨ���󣬿�������icon�����֡���ʾ�������񶯵ȵȲ����� ����������һ��֪ͨ��Ҫ�Ļ���������
		//An icon(֪ͨ��ͼ��) Atitleandexpandedmessage(֪ͨ�ı��������) APendingIntent(���ִ֪ͨ��ҳ����ת) 
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
                        new_notice("ͼ�鼴������", "����ͼ�鼴�����ڣ���ע��", new Intent(ReturnBookService.this, BorrowedActivity.class));
                        return;
                    }
                    else if (((Boolean)map.get("is_overdate")).booleanValue())
                    {
                        new_notice("ͼ���ѳ���", "���г���ͼ�飬��ע��", new Intent(ReturnBookService.this, BorrowedActivity.class));
                        return;
                    }
                }
                catch (Exception exception)
                {
                    Log.d("RETURN_BOOK_NOTICE", "parse return_date error");
                }
            	
            }
		}).start();//Thread����
		
		return super.onStartCommand(intent,flags,startId);
    }
	
	public IBinder onBind(Intent intent)
    {
        return null;
    }
	
}
