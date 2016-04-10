package com.example.lam.chesscaroandroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Obj_Settings extends Activity {
    private ActionBar mAcBAr;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public Boolean firstPlayerX = true;
    public int SelectLevel= 0;
    public Boolean EnSound= true;
    Button btnOk;
    RadioButton radX,radO,radEasy,radMedium,radHard,radEnable,radDisable ;
    TextView tvSymbol,tvLevel,tvSound;
    RadioGroup radGroupSym,radGroupLev,RadGroupSou;
    Intent intentMusicBg;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    //Xử lý Sharepreference truyền static method (truền trong Touch Event)
    public static Boolean getFirstPlayerX(Context context) {
        return context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getBoolean("Select X", true);
    }


    // Lưu trạng thái của activity
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Lưu giá trị lựa chọn người chơi 1 chọn X hay O
        outState.putBoolean("Select X", firstPlayerX);
        System.out.println("onSaveState = " + firstPlayerX);

        // Lưu giá trị lựa chọn cấp độ của người chơi
        outState.getInt("SelectLevel", 0);
        System.out.println("onSaveLevel= " + SelectLevel);

        outState.putBoolean("Select Sound", EnSound);
        System.out.println("onSaveState = " + EnSound);

        super.onSaveInstanceState(outState);
    }

    // Được gọi khi tái hoạt động Activity sau khi kill OS
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        firstPlayerX=savedInstanceState.getBoolean("Select X");
        System.out.println("onRestoreInstanceState ="+ firstPlayerX);

        SelectLevel =savedInstanceState.getInt("Select Level Easy =" + SelectLevel);
        System.out.println("onRestoreInstanceState =" + SelectLevel);

        EnSound = savedInstanceState.getBoolean("Select Sound");
        System.out.println("onRestoreInstanceState =" + EnSound);

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Trạng thái trống để giá trị mặc định
        if(savedInstanceState == null){
            firstPlayerX = true;
        } else{
            firstPlayerX = savedInstanceState.getBoolean("Select X", true);
        }
        // TRạng thái trống
        if(savedInstanceState == null){
            SelectLevel =0;
        } else {
            switch (SelectLevel) {
                case 0: SelectLevel = 0;
                    break;
                case 1: SelectLevel = 1;
                    break;
                case 2: SelectLevel = -1;
                    break;
            }

            // file SOund
            if(savedInstanceState == null){
                EnSound = true;
            } else{
                EnSound = savedInstanceState.getBoolean("Select Disable Sound", true);
            }
        }

        intentMusicBg = new Intent(Obj_Settings.this, BackgroundSoundService.class);

        mAcBAr = getActionBar();
        mAcBAr.setIcon(R.mipmap.settings_icon);
        mAcBAr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CDAF95")));
        mAcBAr.setTitle(Html.fromHtml("<font color='#000000'> Cài đặt </font>"));
        mAcBAr.setHomeButtonEnabled(true);
        mAcBAr.setDisplayHomeAsUpEnabled(true);
        // tìm toàn bộ các view ID
        SearchId();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"Đã lưu cài đặt",Toast.LENGTH_SHORT).show();
            }
        });
        // bắt lựa chọn lượt đi X hay O của người chơi
        radGroupSym.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radX){
                    firstPlayerX = true;
                } else if(checkedId == R.id.radO){
                    firstPlayerX = false;
                }
                System.out.println("X= "+firstPlayerX);
            }
        });

        radGroupLev.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radEasy){
                    SelectLevel =0;
                }if(checkedId == R.id.radMedium){
                    SelectLevel =1;
                }if(checkedId == R.id.radHard){
                    SelectLevel =-1;
                }
            }
        });

        RadGroupSou.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radEnable){
                    EnSound = true;
                    startService(intentMusicBg);
                }if(checkedId == R.id.radDisable){
                    EnSound = false;
                    stopService(intentMusicBg);
                }
            }
        });
    }

    // Trạng thái đã lưu được lấy lên khi activity bắt đầu hoạt động
    // Gọi đến hàm đọc trạng thái
    @Override
    protected void onResume() {
        super.onResume();
        firstPlayerX= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getBoolean("Select X", true);
        SelectLevel= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getInt("Select Level", 0);
        // chưa viết
        EnSound = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getBoolean("Select Sound",true);

        // chọn radioGroup theo lựa chọn đã lưu
        if(firstPlayerX){
            radGroupSym.check(R.id.radX);
        } else {
            radGroupSym.check(R.id.radO);
        }

        if(SelectLevel == 0){
            radGroupLev.check(R.id.radEasy);
        } else if(SelectLevel == 1){
            radGroupLev.check(R.id.radMedium);
        } else if(SelectLevel == -1){
            radGroupLev.check(R.id.radHard);
        }

        if(EnSound){
            RadGroupSou.check(R.id.radEnable);
        } else {
            RadGroupSou.check(R.id.radDisable);
        }
    }

    ///////*/////////
    // Gọi đến hàm lưu trạng thái
    @Override
    protected void onPause() {
        super.onPause();
        getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE ).edit().putBoolean("Select X", firstPlayerX).commit();
        System.out.println("on Pause =" + firstPlayerX);

        getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE ).edit().putInt("Select Level", SelectLevel).commit();
        System.out.println("on Pause =" + SelectLevel);

        getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE).edit().putBoolean("Select Sound",EnSound ).commit();// chấp nhận lưu

    }
    ////////*//////////

    public void Save(){
        // Write share preference
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        firstPlayerX = radX.isChecked();
        editor.putBoolean("Select X", firstPlayerX);
        editor.putInt("Select Level", SelectLevel);
        editor.putBoolean("Enable Sound", EnSound);
        editor.commit();
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
                intentMusicBg=new Intent(Obj_Settings.this, BackgroundSoundService.class);
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

    private void SearchId() {
        radGroupSym = (RadioGroup)findViewById(R.id.radGroupSym);
        radGroupLev = (RadioGroup)findViewById(R.id.radGroupLev);
        RadGroupSou = (RadioGroup)findViewById(R.id.radGroupSou);
        radEnable = (RadioButton)findViewById(R.id.radEnable);
        radDisable = (RadioButton)findViewById(R.id.radDisable);
        btnOk = (Button)findViewById(R.id.btnOk);
        radX = (RadioButton)findViewById(R.id.radX);
        radO = (RadioButton)findViewById(R.id.radO);
        radEasy = (RadioButton)findViewById(R.id.radEasy);
        radMedium = (RadioButton)findViewById(R.id.radMedium);
        radHard = (RadioButton)findViewById(R.id.radHard);
        tvSymbol = (TextView)findViewById(R.id.tvSymbol);
        tvLevel= (TextView)findViewById(R.id.tvLevel);
        tvSound = (TextView)findViewById(R.id.tvSound);
    }
    // Source code by LamNguyen
}
