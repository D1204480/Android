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
    private double balance;  // 存(提)款後的餘額
    private double NTDtoJPY; // 台幣轉日幣
    private double JPYtoNTD; // 日幣轉台幣


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

        // 為了可以在傳送端接收返回結果
        intentActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // 寫另一個Activity回傳後, 得到回傳的資料之後的方法
                        if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            if (data.hasExtra("NTD")) {
                                dollarNTD = data.getDoubleExtra("NTD", -1);
                            }

                            if (data.hasExtra("NTDtoJPY")) {
                                NTDtoJPY = data.getDoubleExtra("NTDtoJPY", -1);
                            }

                            if (data.hasExtra("JPYtoNTD")) {
                                JPYtoNTD = data.getDoubleExtra("JPYtoNTD", -1);
                            }
//                            balance = result.getData().getDoubleExtra("NTD", -1);
//                            NTDtoJPY = result.getData().getDoubleExtra("NTDtoJPY", -1);
//                            JPYtoNTD = result.getData().getDoubleExtra("JPYtoNTD", -1);

                            updateUI();
                        } else {
                            // Handle the case when the user didn't complete the operation (e.g. pressed back)
                            TextView showResult = (TextView) findViewById(R.id.result);
                            showResult.setText("交易失敗");
                        }
                    }
                }
        );
    }

    public void GotoDeposit(View view) {
        Intent intent = new Intent(this, DepositActivity.class);
        intent.putExtra("NTD", dollarNTD);
//        startActivity(intent);   // 傳遞資料到另一個Activity
        intentActivityResultLauncher.launch(intent); // 啟動 DepositActivity 並等待結果
    }

    public void GotoYenExchange(View view) {
        Intent intent = new Intent(this, ExchangeActivity.class);
//        startActivity(intent);
        intent.putExtra("NTD", dollarNTD);
        intent.putExtra("JPY", JPYtoNTD);
        intentActivityResultLauncher.launch(intent); // 啟動 DepositActivity 並等待結果
    }


    // 更新台幣餘額
    public void updateUI() {
        TextView textResult = (TextView) findViewById(R.id.showNTD);
        textResult.setText(String.valueOf(dollarNTD));

        TextView showResult = (TextView) findViewById(R.id.result);
        showResult.setText("交易成功");

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
} // end of MainActivity