package com.TestApp.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.TestApp.app.MESSAGE";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Tells the app what to display
        setContentView(R.layout.activity_main);

    }

    //Called when the button if pressed
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessage.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void displayClock(View view) {
        Intent intentClock = new Intent(this, FullscreenClock.class);
        startActivity(intentClock);
    }

    public void testColor(View view) {
        Intent intentColor = new Intent(this, ColorTester.class);
        int selectedColorR = 0;
        int selectedColorG = 0;
        int selectedColorB = 0;

        intentColor.putExtra("VALUE_RED", selectedColorR);
        intentColor.putExtra("VALUE_GREEN", selectedColorG);
        intentColor.putExtra("VALUE_BLUE", selectedColorB);
        startActivity(intentColor);
    }

    public void testSeekbar(View view) {
        Intent intentSeekbar = new Intent(this, SeekbarTest.class);
        startActivity(intentSeekbar);
    }
}
