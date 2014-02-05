package com.TestApp.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;

public class FavoriteColors extends Activity {

    String ret = "";
    List<Map<String, String>> colorsList = new ArrayList<Map<String,String>>();

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

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {

                // We know the View is a TextView so we can cast it
                final TextView clickedView = (TextView) view;
/*
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(colorList,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView colorList, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {

                                    removeLineFromFile("config.txt", clickedView.getText().toString());
                                    colorsList.remove(simpleAdpt.getItem(position));
                                    //remove the entry from the "database" file here as well

                                }
                                simpleAdpt.notifyDataSetChanged();
                            }
                        });
                colorList.setOnTouchListener(touchListener);
                // Setting this scroll listener is required to ensure that during ListView scrolling, we don't look for swipes.
                colorList.setOnScrollListener(touchListener.makeScrollListener());
*/
                removeLineFromFile("config.txt", clickedView.getText().toString());
                colorsList.remove(simpleAdpt.getItem(position));
                simpleAdpt.notifyDataSetChanged();
                Toast.makeText(FavoriteColors.this, "Item with id [" + id + "] - Position [" + position + "] - Color [" + clickedView.getText() + "]", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String initList() {

        try {
            InputStream inputStream = openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                    colorsList.add(0, createColor("color", receiveString));
                }

                inputStream.close();
                ret = stringBuilder.toString();
                Toast.makeText(FavoriteColors.this, "File 'config.txt' closed", Toast.LENGTH_SHORT).show();
            }
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

    public void removeLineFromFile(String file, String lineToRemove) {

        File inFile = new File("config.txt");
        File tempFile = new File("configTemp.txt");

        try {
            //tempFile.mkdir();
            //tempFile.createNewFile();
            InputStream inputStream = openFileInput(file);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                //BufferedWriter output = new BufferedWriter(new FileWriter(tempFile));
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("configTemp.txt", Context.MODE_APPEND));
                //Writer writer;
                //writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "utf-8"));

                String receiveString;

                while ((receiveString = bufferedReader.readLine()) != null ) {
                    if (!receiveString.equals(lineToRemove)) {
                    outputStreamWriter.write(receiveString + "\n");
                        //output.write(receiveString + "\n");
                        //writer.write(receiveString + "\n");
                    }
                }

                inputStream.close();
                outputStreamWriter.close();
                //output.close();
                //writer.close();
                //ret = stringBuilder.toString();
            }

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
}
