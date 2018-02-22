package kr.co.ezenac.cjy.teamproject.customview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import kr.co.ezenac.cjy.teamproject.R;


/**
 * Created by Administrator on 2017-11-28.
 */

public class CustomDialog extends Dialog {
    Button btn_dialog_send;
    EditText et_ps;


    // MemberInputDialog - Exam18Activity
    // CustomDialog - DialogActivity

    Callbacks callbacks = null;

    public interface Callbacks {
        void onClickSend(String password); // 이것이 형식포인트!
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    public CustomDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_input);
        btn_dialog_send = findViewById(R.id.btn_dialog_send);
        et_ps = findViewById(R.id.et_ps);


        btn_dialog_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = et_ps.getText().toString();


                if (callbacks != null) {
                    callbacks.onClickSend(password); // 이것이 형식포인트!
                }

                dismiss();
            }
        });
    }
}
