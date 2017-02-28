package jp.co.rjc.kokoshake.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.co.rjc.kokoshake.R;
import jp.co.rjc.kokoshake.util.SharedPreferenceUtil;

/**
 * 初期画面用フラグメント.
 */
public class ShakeFragment extends Fragment {

    private TextView mRemindRegistration;

    public ShakeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shake, container, false);
        mRemindRegistration = (TextView) view.findViewById(R.id.remind_registration_text);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(SharedPreferenceUtil.getSendAddress(getContext()))) {
            mRemindRegistration.setVisibility(View.VISIBLE);
        } else {
            mRemindRegistration.setVisibility(View.INVISIBLE);
        }
    }
}
