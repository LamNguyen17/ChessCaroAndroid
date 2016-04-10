package com.example.lam.chesscaroandroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class Obj_Help extends Activity {
    TextView tvTab1,tvTab2;
    private ActionBar mAcBAr;
    Intent intentMusicBg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.help_chess);
        loadTabs();
        doFormWidgets();

        mAcBAr = getActionBar();
        mAcBAr.setIcon(R.mipmap.helps_icon);
        mAcBAr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CDAF95")));
        mAcBAr.setTitle(Html.fromHtml("<font color='#000000'> Trợ giúp </font>"));
        mAcBAr.setHomeButtonEnabled(true);
        mAcBAr.setDisplayHomeAsUpEnabled(true);
    }
    private void doFormWidgets() {
        tvTab1 = (TextView)findViewById(R.id.tvTabGuide1);
        tvTab2 = (TextView)findViewById(R.id.tvTabGuide2);
    }
    public void loadTabs()
    {
        final TabHost tab=(TabHost) findViewById(android.R.id.tabhost);
        //gọi lệnh setup
        tab.setup();
        TabHost.TabSpec spec;
        //Tạo tab1
        spec=tab.newTabSpec("t1").setContent(R.id.tab1).setIndicator("Hướng dẫn");
        tab.addTab(spec);
        //Tạo tab2
        spec=tab.newTabSpec("t2").setContent(R.id.tab2).setIndicator("Cách chơi");
        tab.addTab(spec);
        //Thiết lập tab mặc định được chọn ban đầu là tab 0
        tab.setCurrentTab(0);
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String arg0) {
            }
        });
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
                intentMusicBg=new Intent(Obj_Help.this, BackgroundSoundService.class);
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
