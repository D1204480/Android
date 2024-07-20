package com.example.a0717bank_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DepositActivity extends AppCompatActivity {

    private double dollarNTD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deposit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


//        // 接受資料
//        Bundle bundle = this.getIntent().getExtras();
//
//        // 取得台幣餘額
//        if (bundle != null) {
//            String NTD = String.format(bundle.getString("NTD"));
//            dollarNTD = Double.parseDouble(NTD);
//        }


        // 接收來自MainActivity的台幣於惡資料
        Intent intent = getIntent();
        dollarNTD = intent.getDoubleExtra("NTD", 0);


    }


    // 取款、存款
    public void handleConfirm(View view) {

        // 讀取輸入金額
        EditText inputMoneyText = (EditText) findViewById(R.id.input_rate);
        String inputMoneyStr = inputMoneyText.getText().toString();
        double inputMoney = Double.parseDouble(inputMoneyStr);

        // 建立Intent物件, 回傳資料
        Intent returnIntent = new Intent();  // 回傳給MainActivity

        if (!inputMoneyStr.isEmpty() && inputMoney > 0) {  // 輸入金額不為空, 且大於0

            // 取款
            if (view.getId() == R.id.withdraw_btn) {

                // 餘額足夠提款
                if (dollarNTD >= inputMoney) {
                    dollarNTD = dollarNTD - inputMoney;

                } else {  // 餘額不足

                    // 資料傳回main  // 餘額不足
                    returnIntent.putExtra("NTD", -1);
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                    return;
                }

            // 存款
            } else if (view.getId() == R.id.deposit_btn) {
                dollarNTD = dollarNTD + inputMoney;
            }

            // 操作結果, 傳回main
            returnIntent.putExtra("NTD", dollarNTD);
            setResult(Activity.RESULT_OK, returnIntent);

            finish();
        }
    }


}