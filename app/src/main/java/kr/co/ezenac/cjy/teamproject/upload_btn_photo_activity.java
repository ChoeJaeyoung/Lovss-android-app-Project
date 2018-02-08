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
import kr.co.ezenac.cjy.teamproject.adapter.Profile_adapter;
import kr.co.ezenac.cjy.teamproject.adapter.Room_adapter;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class upload_btn_photo_activity extends AppCompatActivity {

    Room_adapter roomAdapter;
    @BindView(R.id.grid_room_gv1) GridView grid_room_gv1;
    Profile_adapter profileAdapter;


    Integer room_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        ButterKnife.bind(this);
        Integer tmpId = LoginInfo.getInstance().getMember().getId();
        String tmpMember_id = LoginInfo.getInstance().getMember().getLogin_id().toString();
        String tmpMember_img = LoginInfo.getInstance().getMember().getMember_img();

        Intent intent = getIntent();
        room_id = intent.getIntExtra("room_id", 0);

        callLoginInfo(tmpId);


    }

    public void callLoginInfo(Integer tmp_memberId){
        Call<ArrayList<Room>> observ = RetrofitService.getInstance().getRetrofitRequest().profileRoomInfo(tmp_memberId);
        observ.enqueue(new Callback<ArrayList<Room>>() {
            @Override
            public void onResponse(Call<ArrayList<Room>> call, Response<ArrayList<Room>> response) {
                if (response.isSuccessful()){
                    final ArrayList<Room> items = response.body();

                    profileAdapter = new Profile_adapter(items, upload_btn_photo_activity.this);
                    grid_room_gv1.setAdapter(profileAdapter);
                    for (int i = 0; i < items.size(); i++) {
                        Log.d("profile", "profile : " + items.get(i).toString());
                    }
                    grid_room_gv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Room item = items.get(position);

                            Intent intent = new Intent(upload_btn_photo_activity.this, upload_Activity.class);
                            intent.putExtra("room_id", item.getId());
                            intent.putExtra("room_name", item.getName());
                            intent.putExtra("room_img", item.getRoom_img());
                            RoomInfo.getInstance().setRoom(item);
                            Log.d("kkk", item.toString());
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
