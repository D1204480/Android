package com.example.a0722bankee;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
    private double amount; // 新台幣餘額

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
                            amount = result.getData().getDoubleExtra("AMOUNT", 0);
                            String action = result.getData().getStringExtra("ACTION");
                            String coin = result.getData().getStringExtra("COIN");

                            TextView showNTDText = (TextView) findViewById(R.id.showNTD);
                            TextView tv_result = (TextView) findViewById(R.id.result);

                            double ntd_balance = Double.parseDouble(showNTDText.getText().toString());

                            tv_result.setText("交易成功！");
                            tv_result.setTextColor(Color.parseColor("33AA33"));

                            // 新台幣存提款
                            if (action.equals("deposit")) {   // 存錢
                                showNTDText.setText(String.valueOf(amount + ntd_balance));

                            } else if (action.equals("withdraw")) {
                                if (ntd_balance >= amount) {   // 領錢
                                    showNTDText.setText(String.valueOf(ntd_balance - amount));
                                } else {
                                    tv_result.setText("餘額不足, 交易失敗！");
                                    tv_result.setTextColor(Color.parseColor("AA3333"));
                                }
                            } else if (action.equals("toNTD")) {  // 美金換匯
                                if (coin.equals("USD")) {
                                    double rate = result.getData().getDoubleExtra("RATE", 0);
                                    TextView usdBalanceText = (TextView) findViewById(R.id.usdBalabce);
                                    double usdBalance = Double.parseDouble(usdBalanceText.getText().toString());

                                    if (usdBalance >= amount) {
                                        double r = (usdBalance - amount) * rate;
                                        showNTDText.setText(String.valueOf(ntd_balance));
                                    } else {
                                        tv_result.setText("餘額不足, 交易失敗！");
                                        tv_result.setTextColor(Color.parseColor("AA3333"));
                                    }
                                } else {
                                    double rate = result.getData().getDoubleExtra("RATE", 0);
                                    TextView usdBalanceText = (TextView) findViewById(R.id.usdBalabce);
                                    double usdBalance = Double.parseDouble(usdBalanceText.getText().toString());

                                    if (ntd_balance >= amount) {
                                        double r = amount * rate;
                                        showNTDText.setText(String.valueOf(ntd_balance - amount));
                                        usdBalanceText.setText(String.valueOf(usdBalance));
                                    } else {
                                        tv_result.setText("餘額不足, 交易失敗！");
                                        tv_result.setTextColor(Color.parseColor("AA3333"));
                                    }
                                }
                            }



//                            showNTD.setText(String.valueOf(amount));

                        }
                    }
                }
        );


    }


    public void GotoNTD(View view) {
        Intent intent = new Intent(this, NTDActivity.class);
        intentActivityResultLauncher.launch(intent);  // 為了接收回傳回來的資料
    }

    public void Exchange(View view) {

// 1.按下按鈕Ａ,傳送USD 2.按下按鈕Ｂ,傳送JPY
        String coin;
        if (view.getId() == R.id.toNTD) {
            coin = "USD";
        } else {
            coin = "JPY";
        }

        Intent intent = new Intent(this, ExchangeActivity.class);

        // 設定一個bundle來放資料
        Bundle bundle = new Bundle();
        bundle.putString("COIN", coin);

        // 利用intent攜帶bundle資料
        intent.putExtras(bundle);
//        startActivity(intent);
        intentActivityResultLauncher.launch(intent);  // 為了接收回傳回來的資料
    }


}