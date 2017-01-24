package jp.co.rjc.kokoshake.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import jp.co.rjc.kokoshake.R;
import jp.co.rjc.kokoshake.ui.activity.SettingsActivity;

public class InitFragment extends Fragment {

    private ImageView mSettingBtn;

    public InitFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mSettingBtn = (ImageView) view.findViewById(R.id.setting_button);

        mSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });

        return view;
}

}
