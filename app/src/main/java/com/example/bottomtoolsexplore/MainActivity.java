package com.example.bottomtoolsexplore;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomtools.BasePagerAdapter;
import com.example.bottomtools.BottomTools;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = new TextView(this);
        textView.setText("hahah");
        textView.setTextSize(50);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.WHITE);

        TextView textView2 = new TextView(this);
        textView2.setText("BGQ");
        textView2.setTextSize(50);
        textView2.setGravity(Gravity.CENTER);
        textView2.setBackgroundColor(Color.WHITE);

//        final BottomTools.Builder builder = new BottomTools.Builder(this, textView)
//                .setFixSoftInputHeight(true);

        final BottomTools.Builder builder = new BottomTools.Builder(this, new BasePagerAdapter(new View[]{textView, textView2}))
                .setFixSoftInputHeight(false)
                .setBottomToolsHeight(1000);

        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.show();
            }
        });

        findViewById(R.id.btn_close_with).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss(false);
            }
        });

    }
}
