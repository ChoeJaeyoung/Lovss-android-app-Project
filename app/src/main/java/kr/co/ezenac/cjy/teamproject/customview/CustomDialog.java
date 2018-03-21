package kr.co.ezenac.cjy.teamproject.customview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import kr.co.ezenac.cjy.teamproject.R;


/**
 * Created by Administrator on 2017-11-28.
 */

public class CustomDialog extends Dialog {
    Button btn_dialog_send;
    EditText et_ps;
    Button btn_cancel_Icon;


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
        btn_cancel_Icon = findViewById(R.id.btn_cancel_Icon);


        btn_dialog_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( et_ps.length() < 4 ) {
                    Toast
                            .makeText(getContext(),"4자리 이하는 입력하실 수 없습니다." , Toast.LENGTH_LONG)
                            .show();

                }else{

                String password = et_ps.getText().toString();


                if (callbacks != null) {
                    callbacks.onClickSend(password); // 이것이 형식포인트!
                }



                dismiss();
            }
        }

        });
        btn_cancel_Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
