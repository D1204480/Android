package com.example.a0717bank_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
        EditText inputMoneyText = (EditText) findViewById(R.id.input_money);
        String inputMoneyStr = inputMoneyText.getText().toString();

        if (!inputMoneyStr.isEmpty()) {
            double inputMoney = Double.parseDouble(inputMoneyText.getText().toString());

            if (view.getId() == R.id.withdraw_btn) {   // 取款

                if (dollarNTD >= inputMoney) {
                    dollarNTD = dollarNTD - inputMoney;
                }

            } else if (view.getId() == R.id.deposit_btn) {   // 存款

                dollarNTD = dollarNTD + inputMoney;
            }

            // 回傳更新後的餘額
            Intent resultIntent = new Intent();  // 跳轉回mainActivity
            resultIntent.putExtra("NTD", dollarNTD);  // 資料傳回main
            setResult(Activity.RESULT_OK, resultIntent);

            // 餘額不足
            if (dollarNTD < 0) {
                setResult(Activity.RESULT_CANCELED, resultIntent);
            }

            finish();
        }
    }


}