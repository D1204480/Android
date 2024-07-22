package com.example.a0722bankee;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        // 點到BtnA Textview顯示Apple, 點到BtnB TextView顯示Banana
        if (bundle != null) {
            String coin = String.format(bundle.getString("COIN"));

            // set name
            TextView name = (TextView) findViewById(R.id.title);
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

    }
}
