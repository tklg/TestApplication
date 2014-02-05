package com.TestApp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ColorTester extends Activity {

    private SeekBar seekBarRed;
    private SeekBar seekBarGreen;
    private SeekBar seekBarBlue;

    private TextView textViewRed;
    private TextView textViewGreen;
    private TextView textViewBlue;

    public int r;
    public int g;
    public int b;

    static final String RED_VALUE = "valueRed";
    static final String GREEN_VALUE = "valueGreen";
    static final String BLUE_VALUE = "valueBlue";
    String hex = String.format("#%02x%02x%02x", r, g, b);

    File root = android.os.Environment.getExternalStorageDirectory();
    File dir = new File (root.getAbsolutePath() + "/colortester/");
    File inFile = new File(dir, "config.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        r = extras.getInt("VALUE_RED");
        g = extras.getInt("VALUE_GREEN");
        b = extras.getInt("VALUE_BLUE");

/*
        if (savedInstanceState != null) {
            r = savedInstanceState.getInt(RED_VALUE);
            g = savedInstanceState.getInt(GREEN_VALUE);
            b = savedInstanceState.getInt(BLUE_VALUE);

        } else {

        }
*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_color_tester);

        final LinearLayout layoutMain = (LinearLayout)findViewById(R.id.colorTesterLinearLayout);
        layoutMain.setBackgroundColor(Color.BLACK);
        setHEX();

        //Define all seekbars
        seekBarRed = (SeekBar) findViewById(R.id.seekBarRed);
        textViewRed = (TextView) findViewById(R.id.colorTestIndRed);
        seekBarGreen = (SeekBar) findViewById(R.id.seekBarGreen);
        textViewGreen = (TextView) findViewById(R.id.colorTestIndGreen);
        seekBarBlue = (SeekBar) findViewById(R.id.seekBarBlue);
        textViewBlue = (TextView) findViewById(R.id.colorTestIndBlue);

        // Initialize the textviews with '0'
        textViewRed.setText(seekBarRed.getProgress() + "/" + seekBarRed.getMax());
        textViewGreen.setText(seekBarGreen.getProgress() + "/" + seekBarGreen.getMax());
        textViewBlue.setText(seekBarBlue.getProgress() + "/" + seekBarBlue.getMax());

        //A bunch of stuff to initialize the view after being restored after a quit
        seekBarRed.setProgress(r);
        seekBarGreen.setProgress(g);
        seekBarBlue.setProgress(b);
        textViewRed.setText(r + "/" + seekBarRed.getMax());
        textViewGreen.setText(g + "/" + seekBarGreen.getMax());
        textViewBlue.setText(b + "/" + seekBarBlue.getMax());
        InvertTextColor();
        layoutMain.setBackgroundColor(Color.rgb(r, g, b));

        //Seekbar Change Listeners
        seekBarRed.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBarRed, int progressValue, boolean fromUser) {
                        r = progressValue;
                        textViewRed.setText(r + "/" + seekBarRed.getMax());
                        setHEX();
                        InvertTextColor();

                        layoutMain.setBackgroundColor(Color.rgb(r, g, b));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBarRed) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBarRed) {

                    }
                });

        seekBarGreen.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBarGreen, int progressValue, boolean fromUser) {
                        g = progressValue;
                        textViewGreen.setText(g + "/" + seekBarGreen.getMax());
                        setHEX();
                        InvertTextColor();

                        layoutMain.setBackgroundColor(Color.rgb(r, g, b));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBarGreen) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBarGreen) {

                    }
                });

        seekBarBlue.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBarBlue, int progressValue, boolean fromUser) {
                        b = progressValue;
                        textViewBlue.setText(b + "/" + seekBarBlue.getMax());
                        setHEX();
                        InvertTextColor();

                        layoutMain.setBackgroundColor(Color.rgb(r, g, b));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBarBlue) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBarRed) {

                    }
                });
        }

    public void InvertTextColor() {
        textViewRed.setTextColor(Color.rgb(255-r, 255-g, 255-b));
        textViewGreen.setTextColor(Color.rgb(255-r, 255-g, 255-b));
        textViewBlue.setTextColor(Color.rgb(255-r, 255-g, 255-b));
    }

    public void setHEX() {

        TextView editTextHEX = (TextView) findViewById(R.id.textHEXColor);
        hex = String.format("#%02x%02x%02x", r, g, b);
        editTextHEX.setTextColor(Color.rgb(255-r, 255-g, 255-b));
        editTextHEX.setText(hex);

    }
    //open the favorite colors panel thing
    public void openFavorites() {

        Intent intentFavorite = new Intent(this, FavoriteColors.class);
        startActivity(intentFavorite);
    }

    public void addColorToFavorites() {
        //write current color data to file to be read later
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                makeToast("Creating directory " + dir);
            }
        }

        Writer output;

        if (!inFile.exists()) {
            try {
                if (inFile.createNewFile()) {
                    makeToast("Creating file " + inFile);
                }
            } catch (IOException e) {
                Log.e("Exception", "File creation failed: " + e.toString());
            }
        }

        try {
            output = new BufferedWriter(new FileWriter(inFile, true));
            output.write(hex + "\n");
            output.close();
            makeToast(hex + " added to favorites");
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Handle presses on the action bar items
    switch (item.getItemId()) {
        case R.id.action_favorite:
            openFavorites();
            return true;

        case R.id.action_addToFavorite:
            addColorToFavorites();
            return true;

        default:
            return super.onOptionsItemSelected(item);
    }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.color_tester, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(RED_VALUE, r);
        savedInstanceState.putInt(GREEN_VALUE, g);
        savedInstanceState.putInt(BLUE_VALUE, b);
    }

    public void makeToast(String message) {
        Toast.makeText(ColorTester.this, message, Toast.LENGTH_SHORT).show();
    }
}


