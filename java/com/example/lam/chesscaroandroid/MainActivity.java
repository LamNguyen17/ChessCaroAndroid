package com.example.lam.chesscaroandroid;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button btnHumVsCom,btnHumVsHum,btnSettings,btnHelp,btnInfo;
    Intent intentMusicBg,intentHumVsHum,intentHumVsCom;
    ActionBar mAcBAr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MyActivity", "onCreate");
        setContentView(R.layout.activity_main);
        // method gọi đến id
        connectView();

        mAcBAr = getActionBar();
        mAcBAr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CDAF95")));
        mAcBAr.setTitle(Html.fromHtml("<font color='#000000'> Chess caro </font>"));

        intentMusicBg=new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(intentMusicBg);
//        intentMusicBg.setAction(Intent.ACTION_GET_CONTENT);
//        intentMusicBg.setType("audio/mpeg");
//        startActivityForResult(Intent.createChooser(intentMusicBg, getString(R.raw.sound_trackss)), REQ_CODE_PICK_SOUNDFILE);

        System.out.println("MainActivity onCreate");
        btnHumVsHum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentHumVsHum = new Intent(MainActivity.this, Obj_HumVsHum.class);
                System.out.println("Truyền tab từ Main sang Obj_PlayChess");
                intentHumVsHum.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHumVsHum);

            }
        });
        btnHumVsCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentHumVsCom = new Intent(MainActivity.this, Obj_HumVsCom.class);
                System.out.println("Truyền tab từ Main sang Obj_PlayComputer");
                intentHumVsCom.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHumVsCom);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingIntent = new Intent(getBaseContext(), Obj_Settings.class);
                System.out.println("Khởi động setting");
                startActivity(settingIntent);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent helpIntent = new Intent(MainActivity.this, Obj_Help.class);
                System.out.println("btn_Help");
                startActivity(helpIntent);
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(MainActivity.this, Obj_Info.class);
                startActivity(infoIntent);
            }
        });
    }

    private void connectView() {
        btnHumVsCom = (Button)findViewById(R.id.btnHumVsCom);
        btnSettings = (Button)findViewById(R.id.btnSettings);
        btnHelp = (Button)findViewById(R.id.btnHelp);
        btnHumVsHum = (Button)findViewById(R.id.btnHumVsHum);
        btnInfo =(Button)findViewById(R.id.btnInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.itemss, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.music:
                Toast.makeText(getBaseContext(), "Tắt âm thanh", Toast.LENGTH_SHORT).show();
                intentMusicBg=new Intent(MainActivity.this, BackgroundSoundService.class);
                stopService(intentMusicBg);
                break;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    // Source code by LamNguyen


}
