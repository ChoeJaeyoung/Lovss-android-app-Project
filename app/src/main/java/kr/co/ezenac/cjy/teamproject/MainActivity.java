package kr.co.ezenac.cjy.teamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Member;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.img_mainProfile) ImageView img_mainProfile;
    @BindView(R.id.gird_main) GridView grid_main;
    @BindView(R.id.text_mainId) TextView text_mainId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String tmpMember_id = LoginInfo.getInstance().getMember().getMember_id();

        text_mainId.setText(tmpMember_id);

    }

    @OnClick(R.id.img_mainBack)
    public void onClickImgBack(View view){
        finish();
    }
}
