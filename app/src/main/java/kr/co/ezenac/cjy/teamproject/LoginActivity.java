package kr.co.ezenac.cjy.teamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.model.Member;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edit_loginID) EditText edit_loginID;
    @BindView(R.id.edit_loginPw) EditText edit_loginPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.text_loginJoin)
    public void onClickJoin(View view){
        Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    public void onClickBtnLogin(View view){
        String member_id = edit_loginID.getText().toString();
        String pw = edit_loginPw.getText().toString();
        checkBlank(member_id, pw);
        Call<Member> obser = RetrofitService.getInstance().getRetrofitRequest().login(member_id, pw);
        obser.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if(response.isSuccessful()){
                    Log.d("ttt", "2");
                    Member member = response.body();
                    Integer tmpId = member.getId();
                    Log.d("ttt", member.toString() + "tmpId " + tmpId);
                    isExistId(member);

                } else {
                    Log.d("ttt", "1 : error");
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void isAgreeId(Member member){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("계정과 비밀번호가 일치하지 않습니다.");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        if (member.getId() == -1){
            alertDialog.show();
        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void isExistId(Member member){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("계정이 존재하지 않습니다. 회원가입을 하세요");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        if (member.getId() == 0){
            alertDialog.show();
        } else {
            isAgreeId(member);
        }
    }

    public void checkBlank(String check_id, String check_pw){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("모든 칸을 채워 주세요");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        if (check_id.equals("")){
            alertDialog.show();
        } else if(check_pw.equals("")){
            alertDialog.show();
        } else {

        }
    }
}
