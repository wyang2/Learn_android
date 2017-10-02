/* By Wenhan Yang, communicated with yxiong4, and thanks to the ideas given by all my team members
Online resources includes google.ca, developer.android.com, baidu.com, youtube.ca and lab source code were used
addActivity is an activity that is used to add a counter to array
infoActivity is used to update already existed counter
Count is used to handling calculation and return the info of counters
*/
package com.example.wyang.wyang2_countbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private ListView counters;
    private ArrayAdapter<Count> adapter;
    private ArrayList<Count> counts;
    private TextView total;
    private int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addCounts = (Button) findViewById(R.id.addCount);
        counts = new ArrayList<Count>();
        counters = (ListView) findViewById(R.id.countsList);
        total = (TextView) findViewById(R.id.total_num);
        loadFromFile();
        total.setText("Total: "+Integer.toString(counts.size()));

        addCounts.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent addIntent = new Intent(MainActivity.this,addActivity.class);
                startActivityForResult(addIntent,1);

            }
        });
        //track the position on the list
        counters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                p = position;
                Count chosenCount = (Count) adapter.getItem(position);
                Intent infoIntent = new Intent(MainActivity.this,infoActivity.class);
                Bundle info = new Bundle();
                info.putSerializable("info_here",chosenCount);
                infoIntent.putExtra("info",info);
                startActivityForResult(infoIntent,2);
            }
        });
    }
    //track the list using adapter
    protected void onStart() {
        super.onStart();
        adapter = new ArrayAdapter<Count>(this, R.layout.list,counts);
        counters.setAdapter(adapter);
    }
    @Override
    //onActivityResult method to get info sent by other activities
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Count oneCount = (Count) data.getBundleExtra("result").getSerializable("count_here");
                counts.add(oneCount);
                saveInFile();
                total.setText("Total: " + Integer.toString(counts.size()));
                adapter.notifyDataSetChanged();
            }
        }
        else if(requestCode == 2){
            if(resultCode == RESULT_OK) {

                Count updatedCount = (Count) data.getBundleExtra("update").getSerializable("update_here");
                counts.set(p, updatedCount);
                saveInFile();
                adapter.notifyDataSetChanged();
            }else if(resultCode == -11){

                counts.remove(p);
                saveInFile();
                total.setText("Total: "+Integer.toString(counts.size()));
                adapter.notifyDataSetChanged();
            }
        }
    };
    //save and load data (file name file.sav) using gson
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Count>>() {
            }.getType();
            counts = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            counts = new ArrayList<Count>();
        }
    }
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(counts, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}



