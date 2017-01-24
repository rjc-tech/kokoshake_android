package jp.co.rjc.kokoshake.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import jp.co.rjc.kokoshake.R;
import android.widget.Button;
import android.app.AlertDialog;

import static android.R.attr.id;

/**
 * 設定画面のフラグメント
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // 登録ボタン処理
        Button button = (Button)getActivity().findViewById(R.id.button2);
        // ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリック時の処理
                // ボタン登録メイン処理を呼ぶ
                setMain(v);
            }
        });
    }

    // 登録ボタンメイン処理
    private void setMain(View v) {
        // 処理
        // 未入力チェックを呼ぶ
        in‎putCheck(v);

        // 登録処理を呼ぶ
    }

    // 入力チェック処理
    private void in‎putCheck(View v) {
        // 処理
        //　未入力チェック

        // 必須チェック有無
        boolean required = false;

        // 必須チェックエラー時のエラーメッセージ
        String required_message = "が未入力です";

        // エラー時にメッセージ表示

        //  メーラー
        //  送信先アドレス
        //　件名
        //  本文

        // エラー時にメッセージ表示
        if (required == false) {
//            if (getText().length() == 0) {
//                // 何も入力されていないのでエラーを表示する
//                Toast.makeText(context, required_message, Toast.LENGTH_LONG).show();
//            }
            new AlertDialog.Builder(getActivity())
                    .setTitle("エラー")
                    .setMessage(required_message)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 画面表示
        // まだデータないので仮値
        final View view = inflater.inflate(R.layout.fragment_settings, container);
        EditText addressTo = (EditText) view.findViewById(R.id.addressTo);
        addressTo.setText("セットした送信先");
        EditText subject = (EditText) view.findViewById(R.id.subject);
        subject.setText("セットした件名");
        EditText letterBody = (EditText) view.findViewById(R.id.letterBody);
        letterBody.setText("セットした件名");
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * 設定画面の表示項目の定義。外出しにする？
     */
    private class Settings {

        /**
         * 送信先アドレス
         */
        EditText addressTo;

        /**
         * 件名
         */
        EditText subject;

        /**
         * 本文
         */
        EditText letterBody;

        /**
         * 送信先アドレスを取得
         */
        public EditText getAddressTo() {
            return addressTo;
        }

        public void setAddressTo(EditText addressTo) {
            this.addressTo = addressTo;
        }

        public EditText getSubject() {
            return subject;
        }

        public void setSubject(EditText subject) {
            this.subject = subject;
        }

        public EditText getLetterBody() {
            return letterBody;
        }

        public void setLetterBody(EditText letterBody) {
            this.letterBody = letterBody;
        }
    }
}
