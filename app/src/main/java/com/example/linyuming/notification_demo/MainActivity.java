package com.example.linyuming.notification_demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
      private Button btn;
    private NotificationManager manager;
    private Notification notis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.download);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notis = new Notification();
                Intent intents = new Intent();
                PendingIntent pdintent  = PendingIntent.getActivity(MainActivity.this, 0, intents, 0);

                notis.defaults =Notification.DEFAULT_SOUND;
                notis.flags = Notification.FLAG_NO_CLEAR;
                notis.tickerText = "下载提示";
                notis.contentIntent = pdintent;
                notis.when = System.currentTimeMillis();
                notis.contentView.setTextViewText(R.id.tvMusicName,"忐忑");
                notis.contentView.setTextViewText(R.id.tvProgress,"0kb");
                notis.contentView.setTextViewText(R.id.tvTotalLength,"100kb");
                notis.contentView.setProgressBar(R.id.tvProgress,100,0,false);
                manager.notify(123,notis);
                //线程  -- 开始下载
                new Thread(){
                    public void run (){
                        for (int i =0;i<= 100;i +=10){
                            try{
                                sleep(800);
                            }catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            notis.contentView.setProgressBar(R.id.pdDownload,100,i,false);
                            notis.contentView.setTextViewText(R.id.tvProgress,i+"kb");
                            manager.cancel(123);
                        }
                    }
                }.start();
            }
        });
    }
}
