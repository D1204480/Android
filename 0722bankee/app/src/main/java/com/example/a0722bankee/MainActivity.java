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
    private double amount;

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

                            amount = result.getData().getDoubleExtra("AMOUNT", 0);  // 存提頁面回來的輸入金額
                            String action = result.getData().getStringExtra("ACTION");  // 外幣換台幣 or 台幣換外幣
                            String coin = result.getData().getStringExtra("COIN");  // 換匯幣別

                            TextView showNTDText = (TextView) findViewById(R.id.ntdBalance);  // 顯示台幣餘額區塊
                            TextView tv_result = (TextView) findViewById(R.id.result);  // 操作結果

                            // MainActivity頁面的台幣餘額
                            double ntd_balance = Double.parseDouble(showNTDText.getText().toString());

                            tv_result.setText("交易成功！");
                            tv_result.setTextColor(Color.parseColor("33AA33"));

                            // 新台幣存提款
                            if (action.equals("deposit")) {   // 存錢
                                showNTDText.setText(String.valueOf(amount + ntd_balance));

                            } else if (action.equals("withdraw")) {   // 領錢
                                if (ntd_balance >= amount) {
                                    showNTDText.setText(String.valueOf(ntd_balance - amount));

                                } else {
                                    tv_result.setText("餘額不足, 交易失敗！");
                                    tv_result.setTextColor(Color.parseColor("AA3333"));
                                }

                                // 外幣換匯
                            } else if (action.equals("toNTD")) {  // 換成台幣
                                double rate = result.getData().getDoubleExtra("RATE", 0);  // 取得輸入匯率

                                if (coin.equals("USD")) {   // 米金換匯
                                    TextView usdBalanceText = (TextView) findViewById(R.id.usdBalabce);  // 顯示米金餘額區塊
                                    double usdBalance = Double.parseDouble(usdBalanceText.getText().toString());

                                    if (usdBalance >= amount) {  // 米金餘額足夠
                                        double changeMoney = (usdBalance - amount) * rate;
                                        showNTDText.setText(String.valueOf(ntd_balance + changeMoney)); // 台幣增加
                                        usdBalanceText.setText(String.valueOf(usdBalance - amount));  // 米金減少

                                    } else {
                                        tv_result.setText("餘額不足, 交易失敗！");
                                        tv_result.setTextColor(Color.parseColor("AA3333"));
                                    }

                                } else {  // 日圓換匯
                                    TextView jpyBalanceText = (TextView) findViewById(R.id.jpyBalance);  // 顯示日圓餘額區塊
                                    double jpyBalance = Double.parseDouble(jpyBalanceText.getText().toString());

                                    if (jpyBalance >= amount) {  // 日圓餘額足夠
                                        double changeMoney = amount * rate;
                                        showNTDText.setText(String.valueOf(ntd_balance + changeMoney));  // 台幣增加
                                        jpyBalanceText.setText(String.valueOf(jpyBalance - amount));  // 日幣減少

                                    } else {
                                        tv_result.setText("餘額不足, 交易失敗！");
                                        tv_result.setTextColor(Color.parseColor("AA3333"));
                                    }
                                }

                            } else {  // 換成外幣
                                double rate = result.getData().getDoubleExtra("RATE", 0);  // 取得輸入匯率

                                if (coin.equals("USD")) {   // 米金換匯
                                    TextView usdBalanceText = (TextView) findViewById(R.id.usdBalabce);  // 顯示米金餘額區塊
                                    double usdBalance = Double.parseDouble(usdBalanceText.getText().toString());

                                    if (ntd_balance >= amount) {  // 台幣餘額足夠
                                        double changeMoney = amount / rate;
                                        showNTDText.setText(String.valueOf(ntd_balance - amount)); // 台幣減少
                                        usdBalanceText.setText(String.valueOf(usdBalance + changeMoney));  // 米金增加

                                    } else {
                                        tv_result.setText("餘額不足, 交易失敗！");
                                        tv_result.setTextColor(Color.parseColor("AA3333"));
                                    }

                                } else {  // 日圓換匯
                                    TextView jpyBalanceText = (TextView) findViewById(R.id.jpyBalance);  // 顯示日圓餘額區塊
                                    double jpyBalance = Double.parseDouble(jpyBalanceText.getText().toString());

                                    if (ntd_balance >= amount) {  // 台幣餘額足夠
                                        double changeMoney = amount / rate;
                                        showNTDText.setText(String.valueOf(ntd_balance - amount));  // 台幣增加
                                        jpyBalanceText.setText(String.valueOf(jpyBalance + changeMoney));  // 日幣減少

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
        if (view.getId() == R.id.usdBtn) {
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