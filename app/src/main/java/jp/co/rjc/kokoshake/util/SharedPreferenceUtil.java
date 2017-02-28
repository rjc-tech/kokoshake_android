package jp.co.rjc.kokoshake.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferenceに関する機能を提供します.
 */
public class SharedPreferenceUtil {

    private SharedPreferenceUtil() {
    }

    ///////////////////////////////////////////////////////////////////////////

    // メール送信情報

    // メールアドレス
    public static final String PREF_KEY_SEND_ADDRESS = "pref_key_send_address";

    public static void saveSendAddress(final Context context, final String sendAddress) {
        SharedPreferences data = context.getSharedPreferences(null, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putString(PREF_KEY_SEND_ADDRESS, sendAddress);
        editor.apply();
    }

    public static String getSendAddress(final Context context) {
        SharedPreferences data = context.getSharedPreferences(null, Context.MODE_PRIVATE);
        return data.getString(PREF_KEY_SEND_ADDRESS, null);
    }

    // メール件名
    public static final String PREF_KEY_MAIL_SUBJECT = "pref_key_mail_subject";

    public static void saveMailSubject(final Context context, final String mailSubject) {
        SharedPreferences data = context.getSharedPreferences(null, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putString(PREF_KEY_MAIL_SUBJECT, mailSubject);
        editor.apply();
    }

    public static String getMailSubject(final Context context) {
        SharedPreferences data = context.getSharedPreferences(null, Context.MODE_PRIVATE);
        return data.getString(PREF_KEY_MAIL_SUBJECT, null);
    }

    // メール本文
    public static final String PREF_KEY_MAIL_CONTENT = "pref_key_mail_content";

    public static void saveMailContent(final Context context, final String mailContent) {
        SharedPreferences data = context.getSharedPreferences(null, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putString(PREF_KEY_MAIL_CONTENT, mailContent);
        editor.apply();
    }

    public static String getMailContent(final Context context) {
        SharedPreferences data = context.getSharedPreferences(null, Context.MODE_PRIVATE);
        return data.getString(PREF_KEY_MAIL_CONTENT, null);
    }
}
