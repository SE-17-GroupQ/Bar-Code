package com.example.pooja.exampleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtResult;
    private int REQUEST_CODE_SIMPLE_SCANNER_ACTVITY = 1234;
    private Button btnScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = (Button) findViewById(R.id.btn_scan);
        txtResult = (TextView) findViewById(R.id.txt_result);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SimpleScannerActivity.class);
                startActivityForResult(intent,REQUEST_CODE_SIMPLE_SCANNER_ACTVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SIMPLE_SCANNER_ACTVITY && data!=null){
            String result = data.getStringExtra(SimpleScannerActivity.KEY_BARCODE_RESULT);
            txtResult.setText(result);
        }
    }
}
