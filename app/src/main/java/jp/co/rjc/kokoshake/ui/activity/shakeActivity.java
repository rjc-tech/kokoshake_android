package jp.co.rjc.kokoshake.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import jp.co.rjc.kokoshake.R;

/**
 * kokoシェイク 初期画面用フラグメント.
 */
public class ShakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);


//        // Fragmentを作成します
//        TutorialFragment fragment = new TutorialFragment();
//        // Fragmentの追加や削除といった変更を行う際は、Transactionを利用します
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        // 新しく追加を行うのでaddを使用します
//        // 他にも、メソッドにはreplace removeがあります
//        // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment
//        transaction.add(R.id.container, fragment);
//        // 最後にcommitを使用することで変更を反映します
//        transaction.commit();

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
                intent = new Intent(ShakeActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            // 「about」アイコンがクリックされた場合
            case R.id.menu_about_id:
                // チュートリアル画面へ遷移
                intent = new Intent(ShakeActivity.this, TutorialActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
