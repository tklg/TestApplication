package com.TestApp.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class ColorTester extends Activity {

    private SeekBar seekBarRed;
    private SeekBar seekBarGreen;
    private SeekBar seekBarBlue;

    private TextView textViewRed;
    private TextView textViewGreen;
    private TextView textViewBlue;

    public int rgbValueRed;
    public int rgbValueGreen;
    public int rgbValueBlue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_tester);

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

        final LinearLayout layoutMain = (LinearLayout)findViewById(R.id.colorTesterLinearLayout);

        //Seekbar Change Listeners
        seekBarRed.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBarRed, int progressValue, boolean fromUser) {
                        rgbValueRed = progressValue;
                        textViewRed.setText(rgbValueRed + "/" + seekBarRed.getMax());
                        textViewRed.setTextColor(Color.rgb(255-rgbValueRed, 255-rgbValueGreen, 255-rgbValueBlue));
                        textViewGreen.setTextColor(Color.rgb(255-rgbValueRed, 255-rgbValueGreen, 255-rgbValueBlue));
                        textViewBlue.setTextColor(Color.rgb(255-rgbValueRed, 255-rgbValueGreen, 255-rgbValueBlue));

                        layoutMain.setBackgroundColor(Color.rgb(rgbValueRed, rgbValueGreen, rgbValueBlue));
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
                        rgbValueGreen = progressValue;
                        textViewGreen.setText(rgbValueGreen + "/" + seekBarGreen.getMax());
                        textViewRed.setTextColor(Color.rgb(255-rgbValueRed, 255-rgbValueGreen, 255-rgbValueBlue));
                        textViewGreen.setTextColor(Color.rgb(255-rgbValueRed, 255-rgbValueGreen, 255-rgbValueBlue));
                        textViewBlue.setTextColor(Color.rgb(255-rgbValueRed, 255-rgbValueGreen, 255-rgbValueBlue));

                        layoutMain.setBackgroundColor(Color.rgb(rgbValueRed, rgbValueGreen, rgbValueBlue));
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
                        rgbValueBlue = progressValue;
                        textViewBlue.setText(rgbValueBlue + "/" + seekBarBlue.getMax());
                        textViewRed.setTextColor(Color.rgb(255-rgbValueRed, 255-rgbValueGreen, 255-rgbValueBlue));
                        textViewGreen.setTextColor(Color.rgb(255-rgbValueRed, 255-rgbValueGreen, 255-rgbValueBlue));
                        textViewBlue.setTextColor(Color.rgb(255-rgbValueRed, 255-rgbValueGreen, 255-rgbValueBlue));

                        layoutMain.setBackgroundColor(Color.rgb(rgbValueRed, rgbValueGreen, rgbValueBlue));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBarBlue) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBarRed) {

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
