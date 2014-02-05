package com.TestApp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class FavoriteColors extends Activity {

    int selectedColorR = 0;
    int selectedColorG = 0;
    int selectedColorB = 0;
    int r;
    int g;
    int b;

    String ret = "";
    List<Map<String, String>> colorsList = new ArrayList<Map<String,String>>();

    File root = android.os.Environment.getExternalStorageDirectory();
    File dir = new File (root.getAbsolutePath() + "/colortester");

    File inFile = new File(dir, "config.txt");
    File tempFile = new File(dir, "configTemp.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_favorite_colors);

        initList();

        final ListView colorList = (ListView) findViewById(R.id.colorList);
        final SimpleAdapter simpleAdpt = new SimpleAdapter(this, colorsList, android.R.layout.simple_list_item_1, new String[] {"color"}, new int[] {android.R.id.text1});
        colorList.setAdapter(simpleAdpt);

        //listen for when the user presses an item in the list
        colorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, final int position, long id) {

                final TextView clickedView = (TextView) view;
                final String hexval = clickedView.getText().toString();

                PopupMenu popup = new PopupMenu(FavoriteColors.this, clickedView, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
                popup.getMenuInflater().inflate(R.menu.popup_menu_favoritecolor, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.favcolor_use: {
                                makeToast("Using color " + hexval);
                                calculateRGB(hexval);
                                sendColorToMain();
                                break;
                            }
                            case R.id.favcolor_delete: {
                                removeLineFromFile(hexval);
                                colorsList.remove(simpleAdpt.getItem(position));
                                simpleAdpt.notifyDataSetChanged();
                                break;
                            }
                            case R.id.favcolor_info: {
                                makeToast("Color Info:");
                                calculateRGB(hexval);
                                sendColorToInfo();
                                break;
                            }
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    private String initList() {

        BufferedReader input;

        if (!inFile.exists()) {
            makeToast("File " + inFile + " does not exist, creating...");
            try {
                if(!inFile.createNewFile()) {
                    makeToast("File creation failed.");
                }
            } catch (IOException e) {
                Log.e("Exception", "File creation failed: " + e.toString());
            }
        }

        try {
            input = new BufferedReader(new FileReader(inFile));
            String line;
            while ((line = input.readLine()) != null) {
                colorsList.add(0, createColor("color", line));
            }

                input.close();
                makeToast("File " + inFile + " closed");

        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    private HashMap<String, String> createColor(String key, String name) {
        HashMap<String, String> color = new HashMap<String, String>();
        color.put(key, name);

        return color;
    }

    public void removeLineFromFile(String lineToRemove) {

        if (!inFile.exists()) {
            makeToast("File " + inFile + " does not exist, creating...");
            try {
                if(!inFile.createNewFile()) {
                    makeToast("File creation failed.");
                }
            } catch (IOException e) {
                Log.e("Exception", "File creation failed: " + e.toString());
            }
        }

        if (!tempFile.exists()) {
            makeToast("File " + tempFile + " does not exist, creating...");
            try {
                if(!tempFile.createNewFile()) {
                    makeToast("File creation failed.");
                }
            } catch (IOException e) {
                Log.e("Exception", "File creation failed: " + e.toString());
            }
        }

        try {

                BufferedReader input;
                input = new BufferedReader(new FileReader(inFile));

                String receiveString;
                BufferedWriter output;
                output = new BufferedWriter(new FileWriter(tempFile, true));
                while ((receiveString = input.readLine()) != null ) {
                    if (!receiveString.equals(lineToRemove)) {

                        output.write(receiveString + "\n");
                    }
                }
                //inputStream.close();
                output.close();
            //Delete the original file
            try {

                if (!inFile.exists()) {
                    Toast.makeText(FavoriteColors.this, "File " + inFile + " does not exist", Toast.LENGTH_SHORT).show();
                }
                boolean fileDeleted = inFile.delete();

                Toast.makeText(FavoriteColors.this, "Delete " + inFile + " = " + fileDeleted, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                System.out.println("Could not delete file");
                Toast.makeText(FavoriteColors.this, "Failed to Delete " + inFile, Toast.LENGTH_SHORT).show();
            }

            //Rename the new file to the filename the original file had.
            try {

                if (!tempFile.exists()) {
                    Toast.makeText(FavoriteColors.this, "File " + tempFile + " does not exist", Toast.LENGTH_SHORT).show();
                }
                boolean fileRenamed = tempFile.renameTo(inFile);

                Toast.makeText(FavoriteColors.this, "Rename " + tempFile + " to " + inFile + " = " + fileRenamed, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                System.out.println("Could not rename file");
                Toast.makeText(FavoriteColors.this, "Failed to Rename " + tempFile + " to " + inFile, Toast.LENGTH_SHORT).show();
            }
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favorite_colors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void makeToast(String message) {
        Toast.makeText(FavoriteColors.this, message, Toast.LENGTH_SHORT).show();
    }

    public void sendColorToMain() {

        Intent intent = new Intent(this, ColorTester.class);
        selectedColorR = r;
        selectedColorG = g;
        selectedColorB = b;

        intent.putExtra("VALUE_RED", selectedColorR);
        intent.putExtra("VALUE_GREEN", selectedColorG);
        intent.putExtra("VALUE_BLUE", selectedColorB);
        startActivity(intent);

    }

    public void sendColorToInfo() {

        Intent intent = new Intent(this, ColorInfo.class);
        selectedColorR = r;
        selectedColorG = g;
        selectedColorB = b;

        intent.putExtra("VALUE_RED", selectedColorR);
        intent.putExtra("VALUE_GREEN", selectedColorG);
        intent.putExtra("VALUE_BLUE", selectedColorB);
        startActivity(intent);

    }

    public void calculateRGB(String hex) {

        r = Integer.valueOf(hex.substring(1, 3), 16);
        g = Integer.valueOf(hex.substring(3, 5), 16);
        b = Integer.valueOf(hex.substring(5, 7), 16);
        makeToast("Calculated RGB of" + hex);
        //int color = Color.parseColor(hex);
    }
}
