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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Obj_HumVsCom extends Activity {
    ActionBar mAcBAr;
    Intent intentMusicBg, homeIntent,newgameIntentMul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_humvscom);

        mAcBAr = getActionBar();
        mAcBAr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CDAF95")));
        mAcBAr.setTitle(Html.fromHtml("<font color='#000000'> Người vs Máy </font>"));
        mAcBAr.setHomeButtonEnabled(true);
        mAcBAr.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater;
        inflater = getMenuInflater();
        inflater.inflate(R.menu.item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.newgame:
                AlertDialog.Builder builder = new AlertDialog.Builder(Obj_HumVsCom.this);
                builder.setTitle("New game");
                builder.setMessage("Bạn có muốn tạo game mới không ?");
                builder.setIcon(R.mipmap.newgame);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newgameIntentMul = new Intent(Obj_HumVsCom.this, Obj_HumVsCom.class);
                        newgameIntentMul.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        newgameIntentMul.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(newgameIntentMul);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
                break;

            case R.id.music:
                Toast.makeText(getBaseContext(), "Tắt âm thanh", Toast.LENGTH_SHORT).show();
                intentMusicBg = new Intent(Obj_HumVsCom.this, BackgroundSoundService.class);
                stopService(intentMusicBg);
                //startService(intentMusicBg);
                break;

            case R.id.home:
                Toast.makeText(getBaseContext(), "Về nhà chính", Toast.LENGTH_SHORT).show();
                homeIntent = new Intent(Obj_HumVsCom.this, MainActivity.class);
                startActivity(homeIntent);
                break;

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
        // Source code by LamNguyen
    }
}
