package com.TestApp.app;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ColorInfo extends Activity {

    int r = 0;
    int g = 0;
    int b = 0;
    int calculatedH = 0;
    int calculatedS = 0;
    int calculatedV = 0;
    String hex;

    private TextView colorInfo_red;
    private TextView colorInfo_green;
    private TextView colorInfo_blue;
    private TextView colorInfo_hue;
    private TextView colorInfo_sat;
    private TextView colorInfo_val;

    float[] hsv = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_color_info);

        LinearLayout layoutMain = (LinearLayout) findViewById(R.id.colorInfoFrame);
        LinearLayout breakerBar_1 = (LinearLayout) findViewById(R.id.colorInfo_breakerBar_1);
        colorInfo_red = (TextView) findViewById(R.id.colorInfo_color_red);
        colorInfo_green = (TextView) findViewById(R.id.colorInfo_color_green);
        colorInfo_blue = (TextView) findViewById(R.id.colorInfo_color_blue);
        colorInfo_hue = (TextView) findViewById(R.id.colorInfo_color_hue);
        colorInfo_sat = (TextView) findViewById(R.id.colorInfo_color_saturation);
        colorInfo_val = (TextView) findViewById(R.id.colorInfo_color_value);

        try {
        Bundle extras = getIntent().getExtras();
        r = extras.getInt("VALUE_RED");
        g = extras.getInt("VALUE_GREEN");
        b = extras.getInt("VALUE_BLUE");
        } catch (Exception e) {
            e.printStackTrace();
        }

        layoutMain.setBackgroundColor(Color.rgb(r, g, b));
        //breakerBar_1.setBackgroundColor(Color.rgb(255-r, 255-g, 255-b));

        //getHSV();
        RGBtoHSV();
        setHEX();

        colorInfo_red.setText(r + ""); //it likes string
        colorInfo_green.setText(g + "");
        colorInfo_blue.setText(b + "");
        colorInfo_hue.setText(calculatedH + "");
        colorInfo_sat.setText(calculatedS + "");
        colorInfo_val.setText(calculatedV + "");
        //colorInfo_hue.setText(hsv[1] + "");
        //colorInfo_sat.setText(hsv[2] + "%");
        //colorInfo_val.setText(hsv[3] + "%");

    }

    public void getHSV() {
        int red = r/255;
        int green = g/255;
        int blue = b/255;

        int minRGB = Math.min(red, Math.min(green, blue));
        int maxRGB = Math.max(red, Math.max(green, blue));

        //Gray, black, white
        if (minRGB == maxRGB) {
            calculatedH = 0;
            calculatedS = 0;
            calculatedV = minRGB;
        }
        //other colors
            int d = (red == minRGB) ? green - blue : ((blue == minRGB) ? red - green : blue - red);
            int h = (red == minRGB) ? 3 : ((blue == minRGB) ? 1 : 5);
            calculatedH = 60 * (h - d /(maxRGB - minRGB));
            calculatedS = (maxRGB - minRGB) / maxRGB;
            calculatedV = maxRGB;

    }

    public void RGBtoHSV() {
        int red = r;
        int green = g;
        int blue = b;
        Color.RGBToHSV(red, green, blue, hsv);

    }
    public void setHEX() {

        TextView TextHEX = (TextView) findViewById(R.id.colorInfo_color);
        hex = String.format("#%02x%02x%02x", r, g, b);
        TextHEX.setTextColor(Color.rgb(r, g, b));
        TextHEX.setText(hex);

    }
}
