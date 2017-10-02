package com.example.wyang.wyang2_countbook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class addActivity extends AppCompatActivity {
    private static Count count;
    private String name;
    private Integer value;
    private String text;
    private EditText Name;
    private EditText Value;
    private EditText Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Name = (EditText) findViewById(R.id.addName);
        Value = (EditText) findViewById(R.id.addValue);
        Text = (EditText) findViewById(R.id.addText);
        Button add = (Button) findViewById(R.id.addCount);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent resultIntent = new Intent();
                name = Name.getText().toString().replaceAll("^\\s+", "");//replace spaces
                value = Integer.parseInt(Value.getText().toString());
                text = Text.getText().toString();

                count = new Count(name, value, text);
                Bundle newBundle = new Bundle();
                newBundle.putSerializable("count_here",count);
                resultIntent.putExtra("result",newBundle);
                if (name ==""||value == -1 ){
                    setResult(-1,resultIntent);
                }else{
                    setResult(RESULT_OK,resultIntent);
                }
                finish();

            }
        });

    }

}
