package kr.co.ezenac.cjy.teamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.adapter.Profile_adapter;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Join;
import kr.co.ezenac.cjy.teamproject.model.Member;
import kr.co.ezenac.cjy.teamproject.model.Member_img;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.img_mainProfile) ImageView img_mainProfile;
    @BindView(R.id.gird_main) GridView grid_main;
    @BindView(R.id.text_mainId) TextView text_mainId;
    Profile_adapter profileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Integer tmpId = LoginInfo.getInstance().getMember().getId();

        callJoinInfo(tmpId);
        callMemberInfo(tmpId);



        text_mainId.setText(tmpId);
    }

    @OnClick(R.id.img_mainBack)
    public void onClickImgBack(View view){
        finish();
    }

    public void callJoinInfo(Integer tmp_memberId){
        Call<ArrayList<Room>> observ = RetrofitService.getInstance().getRetrofitRequest().callProfile(tmp_memberId);
       observ.enqueue(new Callback<ArrayList<Room>>() {
           @Override
           public void onResponse(Call<ArrayList<Room>> call, Response<ArrayList<Room>> response) {
               final ArrayList<Room> items = response.body();

               profileAdapter = new Profile_adapter(items, MainActivity.this);
               grid_main.setAdapter(profileAdapter);
               grid_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Room item = items.get(position);
                   }
               });
           }

           @Override
           public void onFailure(Call<ArrayList<Room>> call, Throwable t) {

           }
       });
    }

    public void callMemberInfo(Integer tmp_memberId) {
        Call<Member_img> observ = RetrofitService.getInstance().getRetrofitRequest().callMemberInfo(tmp_memberId);
        observ.enqueue(new Callback<Member_img>() {
            @Override
            public void onResponse(Call<Member_img> call, Response<Member_img> response) {
                Member_img member_img = response.body();
                Glide.with(MainActivity.this).load(member_img.getMember_img_path()).centerCrop().
                        into(img_mainProfile);
                Log.d("ttt", member_img.toString());
            }

            @Override
            public void onFailure(Call<Member_img> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
