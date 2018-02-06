package kr.co.ezenac.cjy.teamproject;

import android.content.Intent;
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
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
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
        String tmpMember_id = LoginInfo.getInstance().getMember().getLogin_id().toString();
        String tmpMember_path = LoginInfo.getInstance().getMember().getMember_img();

        callLoginInfo(tmpId);
        Glide.with(MainActivity.this).load(tmpMember_path).centerCrop().
                into(img_mainProfile);
        text_mainId.setText(tmpMember_id);


    }

    @OnClick(R.id.img_mainBack)
    public void onClickImgBack(View view){
        finish();
    }

    public void callLoginInfo(Integer tmp_memberId){
        Call<ArrayList<Room>> observ = RetrofitService.getInstance().getRetrofitRequest().profileRoomInfo(tmp_memberId);
       observ.enqueue(new Callback<ArrayList<Room>>() {
           @Override
           public void onResponse(Call<ArrayList<Room>> call, Response<ArrayList<Room>> response) {
               if (response.isSuccessful()){
               final ArrayList<Room> items = response.body();

               profileAdapter = new Profile_adapter(items, MainActivity.this);
               grid_main.setAdapter(profileAdapter);
               grid_main.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Room item = items.get(position);

                       Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                       intent.putExtra("room_id", item.getId());
                       intent.putExtra("room_name", item.getName());
                       intent.putExtra("room_img", item.getRoom_img());
                       startActivity(intent);
                   }
               });

               Log.d("ttt", items.toString());
               }
           }

           @Override
           public void onFailure(Call<ArrayList<Room>> call, Throwable t) {
                t.printStackTrace();
           }
       });
    }
}
