package com.example.c_pcombine;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MyQSTileService extends TileService {
    @Override
    public void onTileAdded() {
        super.onTileAdded();
        Tile tile = getQsTile();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tile.setSubtitle("Send copied text");
            tile.updateTile();
            Toast.makeText(MyQSTileService.this, "set tile", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        ClipboardManager clipboardManager;
        String text;
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        //Get IP from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        String ipAddress = sharedPref.getString("IP", "0.0.0.0");

        //Get text from clipboard
        //text = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
        Toast.makeText(MyQSTileService.this, "from tile", Toast.LENGTH_LONG).show();

        new Thread(new ClientThread("from tile", ipAddress)).start();
    }
}

