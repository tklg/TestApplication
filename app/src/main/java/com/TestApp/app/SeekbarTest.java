package com.TestApp.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SeekbarTest extends Activity {

    /**
     * Android SeekBar example
     * @author Prabu Dhanapal
     * @version 1.0
     * @since SEP 02 2013
     *
     */
        private SeekBar seekBar;
        private TextView textView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_seekbar_test);
            seekBar = (SeekBar) findViewById(R.id.seekBar1);
            textView = (TextView) findViewById(R.id.textView1);
            // Initialize the textview with '0'
            textView.setText(seekBar.getProgress() + "/" + seekBar.getMax());
            seekBar.setOnSeekBarChangeListener(
                    new OnSeekBarChangeListener() {
                        int progress = 0;
                        @Override
                        public void onProgressChanged(SeekBar seekBar,
                                                      int progresValue, boolean fromUser) {
                            progress = progresValue;
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            // Do something here,
                            //if you want to do anything at the start of
                            // touching the seekbar
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            // Display the value in textview
                            textView.setText(progress + "/" + seekBar.getMax());
                        }
                    });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu;
            //this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.seekbar_test, menu);
            return true;
        }

    }
