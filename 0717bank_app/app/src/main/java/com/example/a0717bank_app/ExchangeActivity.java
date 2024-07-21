package com.example.a0717bank_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ExchangeActivity extends AppCompatActivity {

    private double dollarNTD;
    private double dollarJPY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exchange);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 接收來自MainActivity的台幣餘額資料
        Intent intent = getIntent();
        dollarNTD = intent.getDoubleExtra("NTD", 0);

        // 接收來自MainActivity的日幣餘額資料
        dollarJPY = intent.getDoubleExtra("JPY", 0);
    }


    // 兌換
    public void handleConfirm(View view) {

        //讀取輸入金額
        TextView inputMoneyText = (TextView) findViewById(R.id.input_money);
        String inputMoneyStr = inputMoneyText.getText().toString();
        double inputMoney = Double.parseDouble(inputMoneyStr);

        // 讀取匯率
        TextView inputRateText = (TextView) findViewById(R.id.input_rate);
        String inputRateStr = inputRateText.getText().toString();
        double inputRate = Double.parseDouble(inputRateStr);

        // 建立Intent物件回傳資料
        Intent returnIntent = new Intent();

        // 計算兌換金額
        if (!inputMoneyStr.isEmpty() && !inputRateStr.isEmpty() && inputMoney > 0 && inputRate > 0) {
            double exchange;

            // 計算匯兌 (eg. 1NTD = 0.2YEN)
            if (view.getId() == R.id.NTDtoJPY_btn) {   // 台幣換日幣
                exchange = inputMoney / inputRate;
                dollarNTD -= inputMoney;  // 台幣餘額減少
                dollarJPY += exchange;  // 日幣餘額增加

                // 操作結果, 傳回MainActivity
                returnIntent.putExtra("NTDtoJPY", dollarNTD);
                returnIntent.putExtra("JPYtoNTD", dollarJPY);
                setResult(Activity.RESULT_OK, returnIntent);


            } else if (view.getId() == R.id.JPYToNTD_btn) {  // 計算日圓兌台幣
                exchange = inputMoney * inputRate;
                dollarJPY -= inputMoney;  // 日幣餘額減少
                dollarNTD += exchange;  // 台幣餘額增加

                // 操作結果, 傳回MainActivity
                returnIntent.putExtra("NTDtoJPY", dollarNTD);
                returnIntent.putExtra("JPYtoNTD", dollarJPY);
                setResult(Activity.RESULT_OK, returnIntent);

            }

        } else {
            // 資料傳回main  // 餘額不足
            returnIntent.putExtra("NTDtoJpY", -1);
            returnIntent.putExtra("JPYtoNTD", -1);
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
            return;
        }

        finish();
    }
}