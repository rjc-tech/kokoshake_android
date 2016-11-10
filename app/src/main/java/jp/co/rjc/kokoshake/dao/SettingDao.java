package jp.co.rjc.mypassword.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * 設定情報のデータアクセスオブジェクトを提供します.
 */
public final class SettingDao implements KokoshakeDatabase.Tables, KokoshakeDatabase.SettingColumns, KokoshakeDatabase.BaseColumns {

    private Context mContext;

    public SettingDao(final Context context) {
        mContext = context;
    }

    /**
     * 設定情報を返却します.
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
     * @param subjecty
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
        whereArgs[0] = Integer.toString(“1”);
        try {
            return db.getWritableDatabase().update(SETTING_INFO, values, whereClause, whereArgs);
        } finally {
            db.close();
        }
    }
}