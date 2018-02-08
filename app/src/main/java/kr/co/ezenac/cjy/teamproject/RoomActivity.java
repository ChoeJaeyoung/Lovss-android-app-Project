package kr.co.ezenac.cjy.teamproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.adapter.Room_adapter;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomActivity extends AppCompatActivity {
    Room_adapter roomAdapter;
    @BindView(R.id.grid_room_gv) GridView grid_room_gv;
    @BindView(R.id.img_room_Back) ImageView img_room_Back;
    @BindView(R.id.img_roomImg) ImageView img_roomImg;
    @BindView(R.id.text_room_name) TextView text_room_name;
    @BindView(R.id.in_room_img_add) Button in_room_img_add;
    Integer room_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        room_id = intent.getIntExtra("room_id", 0);
        String room_name = intent.getStringExtra("room_name");
        String room_img = intent.getStringExtra("room_img");


        Log.d("room", "room_info : " + room_name + " / " + room_img +"/"+ room_id);
        text_room_name.setText(room_name);
        Glide.with(RoomActivity.this).load(room_img).centerCrop().
                into(img_roomImg);
        callImgInfo(room_id);
    }

    @OnClick(R.id.in_room_img_add)
    public void onClick_in_room_img_add(View view){
        Intent intent = new Intent(RoomActivity.this, upload_imgActivity.class);
        intent.putExtra("room_id", room_id);
        Log.d("kkk","room_id" + room_id);
        startActivity(intent);
    }

    public void callImgInfo(Integer room_id){
        Call<ArrayList<Img>> observ = RetrofitService.getInstance().getRetrofitRequest().
                callRoomImg(room_id);
        observ.enqueue(new Callback<ArrayList<Img>>() {
            @Override
            public void onResponse(Call<ArrayList<Img>> call, Response<ArrayList<Img>> response) {
                if (response.isSuccessful()) {
                    final ArrayList<Img> items = response.body();

                    Log.d("uuu", items.toString());


                    roomAdapter = new Room_adapter(items, RoomActivity.this);
                    grid_room_gv.setAdapter(roomAdapter);
                    grid_room_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(RoomActivity.this, DetailActivity.class);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                    });

                } else {
                    Log.d("uuu", "1");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Img>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
