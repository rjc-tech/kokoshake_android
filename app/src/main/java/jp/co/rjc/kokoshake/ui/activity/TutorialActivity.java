package jp.co.rjc.kokoshake.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import jp.co.rjc.kokoshake.R;

/**
 * 初回起動チュートリアル用フラグメント.
 */
public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
    }

    @Override
    /**
     * オプションメニュー作成処理.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューXMLからメニューを生成
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    /**
     * オプションメニューアイコン選択時の処理.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        // クリックされたアイコンによって処理分岐
        switch (item.getItemId()) {
            // 「設定」アイコンがクリックされた場合
            case R.id.menu_setting_id:
                // 設定画面へ遷移
                intent = new Intent(TutorialActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            // 「about」アイコンがクリックされた場合
            case R.id.menu_about_id:
                // 処理無し
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
