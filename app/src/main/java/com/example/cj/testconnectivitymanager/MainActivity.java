package com.example.cj.testconnectivitymanager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 获取当前的网络连接信息和网络连接状态。GPRS和Wifi网络
 * 如果需要获取网络连接状态，需要给定权限
 * <uses-permission
 * android:name="android.permission.ACCESS_NETWORK_STATE">
 * </uses-permission>
 */
public class MainActivity extends AppCompatActivity {
    private TextView gprs;
    private TextView wifi;
    private ConnectivityManager manager;//网络连接的管理对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gprs = (TextView) findViewById(R.id.gprs);
        wifi = (TextView) findViewById(R.id.wifi);
        //初始化管理器
        manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        //根据给定的网络类型获取该类型的网络连接信息,并且进一步获取当前信息中的网络连接状态
        NetworkInfo.State gprsState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        NetworkInfo.State wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        gprs.setText("GPRS连接状态：" + gprsState.toString());
        wifi.setText("Wifi连接状态：" + wifiState.toString());
        if (!gprsState.equals(NetworkInfo.State.CONNECTED) && !wifiState.equals(NetworkInfo.State
                .CONNECTED)) {
            Toast.makeText(MainActivity.this, "请设置网络连接", Toast.LENGTH_SHORT).show();
            Timer timer = new Timer();
            timer.schedule(new MyTask(),5000);
        }

    }

    //执行的任务类
    class MyTask extends TimerTask {
        @Override
        public void run() {
            //跳转到网络设置界面
            Intent i = new Intent();
            i.setAction(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(i);
        }
    }
}
