package kr.co.ezenac.cjy.teamproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.adapter.Home_adapter;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Main;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.grid_home_gv) GridView grid_home_gv;
    Home_adapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Integer tmpId = LoginInfo.getInstance().getMember().getId();
        callHomeImg(tmpId);
    }

    public void callHomeImg(Integer member_id){
        Call<ArrayList<Main>> observ = RetrofitService.getInstance().getRetrofitRequest()
                .callMain(member_id);
        observ.enqueue(new Callback<ArrayList<Main>>() {
            @Override
            public void onResponse(Call<ArrayList<Main>> call, Response<ArrayList<Main>> response) {
                if (response.isSuccessful()){
                    ArrayList<Main> imgs = response.body();

                    homeAdapter = new Home_adapter(imgs, HomeActivity.this);
                    grid_home_gv.setAdapter(homeAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Main>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}