package com.example.a0717bank_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    private double dollarNTD  = 1000; // 帳戶餘額，預設1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intentActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // 寫另一個Activity回傳後, 得到回傳的資料之後的方法
                        if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                            dollarNTD = result.getData().getDoubleExtra("NTD", -1);

                            updateUI();
                        }
                    }
                }
        );
    }

    public void GotoDeposit(View view) {
        Intent intent = new Intent(this, DepositActivity.class);
        intent.putExtra("NTD", dollarNTD);
//        startActivity(intent);
        intentActivityResultLauncher.launch(intent);
    }

    public void GotoYenExchange(View view) {
        Intent intent = new Intent(this, ExchangeActivity.class);
        startActivity(intent);
    }


    // 更新台幣餘額
    public void updateUI() {
        TextView textResult = (TextView) findViewById(R.id.showNTD);
        textResult.setText(String.valueOf(dollarNTD));

    }


//    public void SentNTD(View view){
//
//        // 傳送NTD餘額
//        String dollarNTD;
//        TextView NTD = (TextView) findViewById(R.id.showNTD);
//        dollarNTD = NTD.toString();
//
//        // 建立Intent
//        Intent intent = new Intent(this, DepositActivity.class);
//
//        // 設定一個bundle來放資料
//        Bundle bundle = new Bundle();
//        bundle.putString("NTD", dollarNTD);
//
//        // 利用intent攜帶bundle資料
//        intent.putExtras(bundle);
//        startActivity(intent);
//
//    }
}