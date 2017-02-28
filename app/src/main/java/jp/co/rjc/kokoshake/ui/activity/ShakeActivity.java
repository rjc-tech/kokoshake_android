package jp.co.rjc.kokoshake.ui.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import java.util.List;

import jp.co.rjc.kokoshake.R;
import jp.co.rjc.kokoshake.util.SharedPreferenceUtil;

/**
 * kokoシェイク 初期画面用フラグメント.
 */
public class ShakeActivity extends AppCompatActivity implements SensorEventListener {

    private static final int SPEED_THRESHOLD = 3000;  //1カウントに必要な端末の最低速度
    private static final int SHAKE_TIMEOUT = 1000;    //端末が最低速度で振られるまでの時間
    private static final int SHAKE_DURATION = 1000;    //端末の加速を検知するまでの時間
    private static final int SHAKE_COUNT = 5; //端末が振るのに必要なカウント数
    private int mShakeCount = 0;  //端末が振られたカウント数
    private long mLastTime = 0;   //一番最後に端末の加速を検知したときの時間
    private long mLastAccel = 0;    //一番最後に端末がSPEED_THRESHOLDの数値を超えた速度で振られた時間
    private long mLastShake = 0;    //一番最後に端末が振られたときの時間
    private float mXDimen = 0;  //端末が一番最後に振られたときのX座標の位置
    private float mYDimen = 0;  //端末が一番最後に振られたときのY座標の位置
    private float mZDimen = 0;  //端末が一番最後に振られたときのZ座標の位置
    private SensorManager mManager;  //センサーマネージャーオブジェクト
    private RelativeLayout relativeLayout;  //リレーティブレイアウトオブジェクト
    private Vibrator mVibrator;  //バイブレーションオブジェクト

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

        //センサーサービスを利用する
        mManager = (SensorManager) getSystemService(SENSOR_SERVICE);
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

    @Override
    protected void onResume() {
        super.onResume();

        //加速度センサーの値を取得する
        List<Sensor> sensors = mManager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        if (sensors.size() > 0) {
            Sensor s = sensors.get(0);
            //リスナーの登録
            mManager.registerListener(this, s, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //メモリの解除
        mVibrator = null;

        //イベントリスナーの登録解除
        if (mManager != null) {
            mManager.unregisterListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //レイアウトを開放
        mManager = null;
        if (relativeLayout != null) {
            relativeLayout.setBackground(null);
        }
    }

    /*
     * 端末が動いたときに呼び出される処理
     *  @param event : 端末の座標
     *  @return; なし
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        // シェイク時間のチェック
        long now = System.currentTimeMillis();
        if (mLastTime == 0) {
            mLastTime = now;
        }
        // SHAKE_TIMEOUT までに次の加速を検知しなかったら mShakeCount をリセット
        if (now - mLastAccel > SHAKE_TIMEOUT) {
            mShakeCount = 0;
        }
        // 速度を算出する
        long diff = now - mLastTime;

        //端末の座標位置を取得する
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        //振られている端末の縦の速度を取得
        float speed = Math.abs(x + y + z - mXDimen - mYDimen - mZDimen) / diff * 10000;

        if (speed > SPEED_THRESHOLD) {
            mShakeCount++;

            /*
             * mShakeCount の加算、SHAKE_COUNT を超えている　または
             * 最後のシェイク時間から SHAKE_DURATION 経過している場合
             * SwipeActivityへ遷移
             */
            if (mShakeCount >= SHAKE_COUNT && now - mLastShake > SHAKE_DURATION) {
                mLastShake = now;
                mShakeCount = 0;
                callMailer();
            }
            // SPEED_THRESHOLD を超える速度を検出した時刻をセット
            mLastAccel = now;
        }

        //一番最後に端末が振られたときの時間と位置をセット
        mLastTime = now;
        mXDimen = x;
        mYDimen = y;
        mZDimen = z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void callMailer() {
        final String sendAddress = SharedPreferenceUtil.getSendAddress(getApplicationContext());
        if (!TextUtils.isEmpty(sendAddress)) {

            mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            mVibrator.vibrate(500);

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);

            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:".concat(sendAddress)));

            // 件名
            final String subject = SharedPreferenceUtil.getMailSubject(getApplicationContext());
            if (TextUtils.isEmpty(subject)) {
                intent.putExtra(Intent.EXTRA_SUBJECT, "仮のタイトル");
            } else {
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            }

            // 本文
            final String content = SharedPreferenceUtil.getMailContent(getApplicationContext());
            if (TextUtils.isEmpty(content)) {
                intent.putExtra(Intent.EXTRA_TEXT, "仮の本文です。");
            } else {
                intent.putExtra(Intent.EXTRA_TEXT, content);
            }
            startActivity(Intent.createChooser(intent, null));
        }

    }
}
