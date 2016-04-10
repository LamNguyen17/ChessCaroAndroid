package com.example.lam.chesscaroandroid;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class BackgroundSoundService extends Service {
    MediaPlayer mediaPlayer;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Khởi tạo file âm thanh lấy ra từ raw trong resource");
        mediaPlayer = MediaPlayer.create(BackgroundSoundService.this, R.raw.sound_trackss);// Khởi tạo file âm thanh
        mediaPlayer.setLooping(true);// Thiết lập lặp đi lặp lại
        //mediaPlayer.setVolume(100, 100);// Thiết lập âm lượng 2 bên tai nghe
    }
    public int onStartCommand(Intent intent, int flags, int startId){
        mediaPlayer.start();
        Toast.makeText(this, "ON", Toast.LENGTH_LONG).show();
        System.out.println("Media Palyer started");
        if(mediaPlayer.isLooping() != true){
            System.out.println("Problem in Playing Audio");
        }
        return 1;
    }
    public void onDestroy(){
        Toast.makeText(this, "OFF", Toast.LENGTH_LONG).show();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
    // Source code by LamNguyen
}
