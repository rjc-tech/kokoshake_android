package jp.co.rjc.kokoshake.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import jp.co.rjc.kokoshake.provider.KokoshakeDatabase;

/**
 * 設定情報のデータアクセスオブジェクトを提供します.
 */
public final class SettingDao implements KokoshakeDatabase.Tables, KokoshakeDatabase.SettingColumns, KokoshakeDatabase.BaseColumns {

    private Context mContext;

    public SettingDao(final Context context) {
        mContext = context;
    }

    /**
     * 設定情報を取得します.
     *
     * @return
     */
    public Cursor select() {
        KokoshakeDatabase db = new KokoshakeDatabase(mContext);
        String[] cols = {_ID, ADDRESS_TO, SUBJECT, LETTER_BODY};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        return db.getReadableDatabase().query(SETTING_INFO, cols, selection, selectionArgs, groupBy, having, orderBy);
    }

    /**
     * 設定情報を更新します.
     *
     * @param addressTo
     * @param subject
     * @param letterBody
     * @return
     */
    public int update(final String addressTo,
                      final String subject,
                      final String letterBody) {
        KokoshakeDatabase db = new KokoshakeDatabase(mContext);

        ContentValues values = new ContentValues();
        values.put(ADDRESS_TO, addressTo);
        values.put(SUBJECT, subject);
        values.put(LETTER_BODY, letterBody);

        String whereClause = _ID + " = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(1);
        try {
            return db.getWritableDatabase().update(SETTING_INFO, values, whereClause, whereArgs);
        } finally {
            db.close();
        }
    }
    /**
     * 設定情報を削除します.
     *
     * @param addressTo
     * @return
     */
    public int delete(final String addressTo) {
        KokoshakeDatabase db = new KokoshakeDatabase(mContext);

        ContentValues values = new ContentValues();
        values.put(ADDRESS_TO, addressTo);

        String whereClause = _ID + " = ?";
        try {
            return db.getWritableDatabase().delete(SETTING_INFO, whereClause, null);
        } finally {
            db.close();
        }
    }
}