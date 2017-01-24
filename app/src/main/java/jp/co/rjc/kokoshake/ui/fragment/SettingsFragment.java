package jp.co.rjc.kokoshake.ui.fragment;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.co.rjc.kokoshake.R;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {

    static final int OPEN_CONTACT_REQUEST = 1;

    protected static TextView sSendAddress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // 送信先アドレス
        sSendAddress = (TextView) view.findViewById(R.id.send_address);
        sSendAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContact();
            }
        });

        // キャンセルボタン
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }

    /**
     * 電話帳アプリを開きます.
     */
    private void openContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, OPEN_CONTACT_REQUEST);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        switch (requestCode) {
            case OPEN_CONTACT_REQUEST:
                if (resultCode == RESULT_OK) {
                    onMailAddressAddressBookResult(data);
                } else {
                    final AlertDialog.Builder alertDlg = new AlertDialog.Builder(getContext());
                    alertDlg.setMessage(R.string.message_err_open_contact);
                    alertDlg.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // ignore
                                }
                            });
                    alertDlg.create().show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * アドレス帳（メールアドレス）の表示.
     */
    private void onMailAddressAddressBookResult(Intent data) {
        String[] mailAddresses = new String[0];
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor c = contentResolver.query(data.getData(), null, null, null, null);
        String id = "";
        if (c.moveToFirst()) {
            id = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID));

            // 選択された人のメールアドレスをすべて取得
            Cursor mailC = contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id,
                    null,
                    null);
            if (mailC.moveToFirst()) {
                mailAddresses = new String[mailC.getCount()];
                int count = 0;
                do {
                    mailAddresses[count] = mailC.getString(
                            mailC.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    count++;
                } while (mailC.moveToNext());
            }
            mailC.close();
        }
        c.close();

        // メールアドレスを持っていない連絡先を選択した場合はエラー
        if (mailAddresses.length <= 0) {
            final AlertDialog.Builder alertDlg = new AlertDialog.Builder(getContext());
            alertDlg.setMessage(R.string.message_err_no_mail_address);
            alertDlg.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // ignore
                        }
                    });
            alertDlg.create().show();
            return;
        }

        // メールアドレスが１つの場合そのまま設定
        if (mailAddresses.length == 1) {
            sSendAddress.setText(mailAddresses[0]);
            return;
        }

        // メールアドレスが複数ある場合、シングル選択ダイアログフラグメントを表示
        SingleSelectDialogFragment fragment = SingleSelectDialogFragment.newInstance(
                R.string.select_address, mailAddresses, 1);
        fragment.show(getFragmentManager(), null);
    }

    /**
     * シングルセレクトダイアログフラグメント.
     */
    public static class SingleSelectDialogFragment extends DialogFragment {

        private static final String TITLE = "title";
        private static final String ITEMS = "items";

        private String selectedItem = "";

        public static SingleSelectDialogFragment newInstance(int title, String[] items, int selectKind) {
            SingleSelectDialogFragment fragment = new SingleSelectDialogFragment();
            Bundle args = new Bundle();
            args.putInt(TITLE, title);
            args.putStringArray(ITEMS, items);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle safedInstanceState) {
            int title = getArguments().getInt(TITLE);
            final String[] items = getArguments().getStringArray(ITEMS);
            selectedItem = items[0];

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title);
            builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    selectedItem = items[item];
                }
            })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            sSendAddress.setText(selectedItem);
                        }
                    })
                    .setNegativeButton("Cancel", null);
            return builder.create();
        }
    }
}
