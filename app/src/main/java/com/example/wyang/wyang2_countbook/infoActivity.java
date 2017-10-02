package com.example.wyang.wyang2_countbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class infoActivity extends AppCompatActivity {
    private static Count theCount;
    private EditText Value, newName, newValue, newText;
    private TextView Date;
    private String name,comment;
    private Integer initial,Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Button increase = (Button) findViewById(R.id.increase);
        Button decrease = (Button) findViewById(R.id.decrease);
        Button delete = (Button) findViewById(R.id.delete);
        Button reset = (Button) findViewById(R.id.reset);
        Button update = (Button) findViewById(R.id.update);
        newName = (EditText) findViewById(R.id.editName);
        newValue = (EditText) findViewById(R.id.editValue);
        newText = (EditText) findViewById(R.id.editText);
        Value = (EditText) findViewById(R.id.currentValue);
        Date = (TextView) findViewById(R.id.date);

        try{
            theCount = (Count) getIntent().getBundleExtra("info").getSerializable("info_here");
        }catch (Exception e){
            theCount = new Count("",0);
        }

        newName.setText(theCount.getName());
        newValue.setHint("Initial Value: "+Integer.toString(theCount.getInitialValue()));
        newText.setText(theCount.getText());
        Date.setText("Date: "+theCount.getDate());
        Value.setHint("Current Value: "+Integer.toString(theCount.getCurrentValue()));



        increase.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                theCount.increaseValue();
                Value.setText("Current Value: "+Integer.toString(theCount.getCurrentValue()));
            }
        });

        decrease.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                theCount.decreaseValue();
                Value.setText("Current Value: "+Integer.toString(theCount.getCurrentValue()));
            }
        });

        reset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                theCount.resetValue();
                Value.setText("Current Value: "+Integer.toString(theCount.getCurrentValue()));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    name = newName.getText().toString().replaceAll("^\\s+", "");
                    if (name == "") {
                        name = theCount.getName();
                    } else {
                        theCount.updateName(name);
                    }
                } catch (Exception e) {
                    name = theCount.getName();
                }
                try {
                    initial = Integer.parseInt(newValue.getText().toString());
                    theCount.updateValue(initial);
                } catch (Exception e) {
                    initial = theCount.getInitialValue();
                }
                try {
                    comment = newText.getText().toString();
                    theCount.updateComment(comment);
                } catch (Exception e) {
                    comment = theCount.getText();
                }
                try {
                    Time = Integer.parseInt(Value.getText().toString());
                    theCount.updateTime(Time);
                } catch (Exception e) {
                    Time = theCount.getCurrentValue();
                }
                Intent newIntent = new Intent();
                Bundle result = new Bundle();
                result.putSerializable("update_here", theCount);
                newIntent.putExtra("update", result);
                ;
                setResult(RESULT_OK, newIntent);
                finish();

            }
        });
    }

}
