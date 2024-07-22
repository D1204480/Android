package com.example.a0722bankee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ExchangeActivity extends AppCompatActivity {
    private String coin;

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

        // 接受資料
        Bundle bundle = this.getIntent().getExtras();

        // 點到USD_btn Textview顯示"美金換匯", 點到JPY_btn TextView顯示"日圓換匯"
        if (bundle != null) {
            coin = String.format(bundle.getString("COIN"));

            // set name
            TextView title = (TextView) findViewById(R.id.title);
            Button toNTDBtn = (Button) findViewById(R.id.toNTD);
            Button NTDtoBtn = (Button) findViewById(R.id.NTDto);

            if (coin.equals("USD")) {
                title.setText("美金換匯");
                toNTDBtn.setText("美肌換新台幣");
                NTDtoBtn.setText("新台幣換美金");

            } else {
                title.setText("日圓換匯");
                toNTDBtn.setText("日圓換新台幣");
                NTDtoBtn.setText("新台幣換日圓");
            }

        }
    }

    public void changeCoin(View view) {

        EditText edit_amount = (EditText) findViewById(R.id.edit_amount);
        EditText edit_rate = (EditText) findViewById(R.id.edit_rate);

        Intent intent = new Intent();

        if (view.getId() == R.id.toNTD){  // 判斷是哪顆按鈕
            intent.putExtra("ACTION", "toNTD");
        }else{
            intent.putExtra("ACTION", "NTDto");
        }

        // 資料傳回MainActivity
        intent.putExtra("COIN", coin);
        intent.putExtra("AMOUNT", Double.parseDouble(edit_amount.getText().toString()));
        intent.putExtra("RATE", Double.parseDouble(edit_rate.getText().toString()));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
