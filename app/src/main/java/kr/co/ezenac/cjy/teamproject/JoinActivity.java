package kr.co.ezenac.cjy.teamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.model.Member;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {
    @BindView(R.id.edit_joinId) EditText edit_joinId;
    @BindView(R.id.edit_joinPw) EditText edit_joinPw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
    }




    @OnClick(R.id.btn_join)
    public void onClickJoinOk(View view){
        String member_id = edit_joinId.getText().toString();
        String pw = edit_joinPw.getText().toString();
        Call<Integer> obser = RetrofitService.getInstance().getRetrofitRequest().join(member_id,pw);
        obser.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()){
                    Integer backInt = response.body();
                    Log.d("ksj","backInt : " + backInt);
                    idOverlapCheck(backInt);
                } else {
                    Log.d("ksj","1 : " );

                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void idOverlapCheck(Integer backInt){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(JoinActivity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("이미 아이디가 존재합니다.");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        if (backInt == 1){
            alertDialog.show();
        } else {
            Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

}
