package jp.co.rjc.kokoshake.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import jp.co.rjc.kokoshake.R;

/**
 * About画面を提供します.
 */
public class AboutActivity extends AppCompatActivity {

    protected Button mOSSBtn;
    protected Button mTopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        mOSSBtn = (Button) findViewById(R.id.btn_oss);
        mOSSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, LicensesActivity.class);
                startActivity(intent);
            }
        });

        mTopBtn = (Button) findViewById(R.id.btn_top);
        mTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, ShakeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}