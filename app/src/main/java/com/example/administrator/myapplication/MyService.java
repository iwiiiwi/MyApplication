package com.example.administrator.myapplication;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/3/16.
 */

public class MyService extends Service {
    private static final String TESSBASE_PATH = Environment.getExternalStorageDirectory().toString()+File.separator+"tesseract"+File.separator;
    private static final String DEFAULT_LANGUAGE = "eng";
    String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(check("test")){
            doSth();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void doSth(){
        ComponentName cn = new ComponentName("com.android.systemui",
                "com.android.systemui.screenshot.TakeScreenshotService");//实际上是通过启动systemui的TakeScreenshotServiceservice来实现
        Intent intent = new Intent();
        intent.setComponent(cn);
//        ServiceConnection conn = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                synchronized (mScreenshotLock) {
//                    if (mScreenshotConnection != this) {
//                        return;
//                    }
//                    Messenger messenger = new Messenger(service);
//                    Message msg = Message.obtain(null, 1);
//                    final ServiceConnection myConn = this;
//                    Handler h = new Handler(mHandler.getLooper()) {
//                        @Override
//                        public void handleMessage(Message msg) {
//                            synchronized (mScreenshotLock) {
//                                if (mScreenshotConnection == myConn) {
//                                    mContext.unbindService(mScreenshotConnection);
//                                    mScreenshotConnection = null;
//                                    mHandler.removeCallbacks(mScreenshotTimeout);
//                                }
//                            }
//                        }
//                    };
//                    msg.replyTo = new Messenger(h);
//                    msg.arg1 = msg.arg2 = 0;
//                    if (mStatusBar != null && mStatusBar.isVisibleLw())
//                        msg.arg1 = 1;
//                    if (mNavigationBar != null && mNavigationBar.isVisibleLw())
//                        msg.arg2 = 1;
//                    try {
//                        messenger.send(msg);
//                    } catch (RemoteException e) {
//                    }
//                }
//            }
//            @Override
//            public void onServiceDisconnected(ComponentName name) {}
//        };
//        if (this.bindServiceAsUser(
//                intent, conn, Context.BIND_AUTO_CREATE, UserHandle.CURRENT)) {
//            mScreenshotConnection = conn;
//            mHandler.postDelayed(mScreenshotTimeout, 10000);
//        }
//    }

}

    private String readImg(){
        TessBaseAPI baseApi=new TessBaseAPI();
        baseApi.init(TESSBASE_PATH, DEFAULT_LANGUAGE);
        baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
        String path= TESSBASE_PATH+"a.png";
        baseApi.setImage(new File(path));
        final String outputText = baseApi.getUTF8Text();
        baseApi.end();
        return outputText;
    }

    private boolean check(String packageName){
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo info : list) {
            for(String pkg:info.pkgList){
                Log.e("yj",pkg);
                if(packageName.equals(pkg)){
                    return true;
                }
            }
        }
        return false;
    }















    private void startNotification(){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("测试标题")//设置通知栏标题
                .setContentText("测试内容")
//                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
//  .setNumber(number) //设置通知集合的数量
                .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
//  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON
    }
}
