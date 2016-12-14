package jp.co.rjc.kokoshake.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB接続のヘルパクラス.
 */
final public class KokoshakeDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "kokoshake_android.db";
    private static final int DB_VERSION = 1;

    /**
     * テーブル名定義.
     */
    public interface Tables {

        /**
         * 設定情報のテーブル名です.
         */
        String SETTING_INFO = "setting_info";
    }

    /**
     * 標準列名定義.
     */
    public interface BaseColumns {

        /**
         * 標準のユニークID.
         */
        String _ID = "_id";
    }

    /**
     * 設定情報の列定義
     */
    public interface SettingColumns {

        /**
         * 送信先アドレスの列名です.
         */
        String ADDRESS_TO = "address_to";

        /**
         * 件名の列名です.
         */
        String SUBJECT = "subject";

        /**
         * 本文の列名です.
         */
        String LETTER_BODY = "letter_body";
    }

    public KokoshakeDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // データベースを新規作成する処理
        createSettingInfo(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // データベースが oldVersion から newVersion(CURRENT_SCHEMA_VERSION) にアップグレードされた場合に呼ばれる
    }

    /**
     * 設定情報テーブルを作成します.
     *
     * @param db
     */
    private void createSettingInfo(final SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + Tables.SETTING_INFO
                        + "("
                        + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                        + SettingColumns.ADDRESS_TO + " TEXT"
                        + SettingColumns.SUBJECT + " TEXT"
                        + SettingColumns.LETTER_BODY + " TEXT"
                        + ");"
        );
    }
}