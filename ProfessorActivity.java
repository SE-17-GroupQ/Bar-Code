package com.se17.attendancesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfessorActivity extends AppCompatActivity {

    private Button btnBarCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        btnBarCode = (Button) findViewById(R.id.btnBarcode);

        btnBarCode.setOnClickListener(btnBarCodeOnclickListener);


    }

    private View.OnClickListener btnBarCodeOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), BarcodeProfessorActivity.class);
            startActivity(intent);
        }
    };
}
