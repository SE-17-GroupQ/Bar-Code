package com.se17.attendancesystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarStudentActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private Button btn;
    private ZXingScannerView scannerView;
    public static final String KEY_BARCODE_RESULT = "key_barcode_result";
    private String TAG = BarStudentActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 321;

    private BarcodeDataSource dataSource;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_barstudent);

        btn = (Button) findViewById(R.id.btn);
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(this);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setContentView(scannerView);
                scannerView.startCamera();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);


        } else {

            scannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            scannerView.startCamera();          // Start camera on resume
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                    scannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
                    scannerView.startCamera();          // Start camera on resume


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this,"Need camera permission to use scanner funcationality",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

        }
    }
    @Override
    public void handleResult(Result result) {
        Log.v(TAG, result.getText()); // Prints scan results
        Log.v(TAG, result.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        dataSource = new BarcodeDataSource(this);
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Crea intent and send result to calling activity
        Intent intent = getIntent();
        intent.putExtra(KEY_BARCODE_RESULT, result.getText());
        setResult(0, intent);

        scannerView.stopCamera();           // Stop camera on pause
        finish();


        //Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_LONG).show();

        List<BarcodeData> data = dataSource.getAll();
       // Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();
        String s1 = result.getText().trim();
        boolean bb=true;
        for (BarcodeData bd : data) {
            if (bd.getBarcodeString().equalsIgnoreCase(s1)) {
                Toast.makeText(getApplicationContext(), "Attendance Marked", Toast.LENGTH_LONG).show();
                bb=false;
            }
        }
if(bb){
            Toast.makeText(getApplicationContext(), "Attendance Not Marked", Toast.LENGTH_LONG).show();
        }
    }
}
