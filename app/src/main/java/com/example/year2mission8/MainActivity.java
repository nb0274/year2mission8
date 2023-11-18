package com.example.year2mission8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    TextView display;
    EditText input;
    Intent in;
    private final String FILENAME = "internalTextFile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = (TextView) findViewById(R.id.displayTV);
        input = (EditText) findViewById(R.id.inputET);

        String internalFileText = readFile();
        display.setText(internalFileText);
    }

    /**
     * This func writes to the file the text we got as a parameter
     * @param strwr the given text to the file
     */
    public void writeToFile(String strwr) {
        try {
            FileOutputStream fOS = openFileOutput(FILENAME, MODE_PRIVATE);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            BufferedWriter bW = new BufferedWriter(oSW);
            bW.write(strwr);
            bW.close();
            oSW.close();
            fOS.close();
        }
        catch (IOException e) {
            Toast.makeText(this, "Error, couldn't write file", Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "Error, couldn't write file");
        }
    }

    /**
     * This func reads the content from the file
     * @return a string that represents the content of the file
     */
    public String readFile() {
        String text = "";
        try {
            FileInputStream fIS = openFileInput(FILENAME);
            InputStreamReader iSR = new InputStreamReader(fIS);
            BufferedReader bR = new BufferedReader(iSR);
            StringBuilder sB = new StringBuilder();
            String line = bR.readLine();
            while (line != null) {
                sB.append(line + '\n');
                line = bR.readLine();
            }
            bR.close();
            iSR.close();
            fIS.close();
            text = sB.toString();
        }
        catch (IOException e) {
            Toast.makeText(this, "Error, couldn't read file", Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "Error, couldn't read file");
        }
        return text;
    }

    /**
     * This func saves to the file the input the user has entered
     * @param view the "save" button
     */
    public void save(View view) {
        String text = readFile();
        text += input.getText().toString();
        writeToFile(text);
        display.setText(text);
    }

    /**
     * This function reset the file and the display
     * @param view the "reset" button
     */
    public void reset(View view) {
        writeToFile("");
        display.setText("");
    }

    /**
     * This method saves the data and exits the app
     * @param view the "exit" button
     */
    public void exit(View view) {
        save(view);
        finish();
    }

    /**
     * This method is called when the user clicks on the menu, it inflates the menu onto the screen
     * @param menu the menu to move between activities
     * @return true or false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activities,menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method is called when the credits option on the menu is called and it goes to the credits activity
     * @param item the option from the menu that presents the activity the user wants to move to
     * @return true or false
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.Credits){
            in = new Intent(this, Credits.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }
}