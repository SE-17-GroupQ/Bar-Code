package com.se17.attendancesystem;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;


public class BarcodeProfessorActivity extends AppCompatActivity {

    private EditText barInput;
    private Button btn;
    private ImageView barImg;
    private BarcodeDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barprofessor);
   dataSource = new BarcodeDataSource(this);
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        barInput = (EditText) findViewById(R.id.input);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String barText = barInput.getText().toString();

                barImg = (ImageView) findViewById(R.id.imageView);

                if(barText == null || barText.length() == 0){
                    Toast.makeText(getApplicationContext(),"No Text Entered!!",Toast.LENGTH_LONG).show();
                    barImg.setImageResource(0);
                }
                else{
                    /*
                    store text in back end database
                    */
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        int minDimension = Math.min(barImg.getWidth(), barImg.getHeight());
                        BitMatrix bitMatrix = multiFormatWriter.encode(barText, BarcodeFormat.CODE_128,minDimension,minDimension);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        barImg.setImageBitmap(bitmap);

                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                        byte[] b = byteArray.toByteArray();
                        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                       dataSource.insert(barText);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
}
