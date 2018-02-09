package kr.co.ezenac.cjy.teamproject;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.adapter.Profile_adapter;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.search_test) EditText search_test;
    @BindView(R.id.btn_searh) Button btn_searh;
    Profile_adapter profileAdapter;
    @BindView(R.id.grid_picture) GridView grid_picture;
    @BindView(R.id.img_home) ImageView img_home;
    @BindView(R.id.img_search) ImageView img_search;
    @BindView(R.id.img_input) ImageView img_input;
    @BindView(R.id.img_option) ImageView img_option;
    @BindView(R.id.linearLayout_search) LinearLayout linearLayout_search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchpage_layout);
        ButterKnife.bind(this);

    }



    @OnClick(R.id.btn_searh)
    public void onClickJoinOk(View view){
        String name = search_test.getText().toString();
        callLoginInfo(name);


    }


    public void callLoginInfo(String name){
        Call<ArrayList<Room>> observ = RetrofitService.getInstance().getRetrofitRequest().searchRoom(name);
        observ.enqueue(new Callback<ArrayList<Room>>() {
            @Override
            public void onResponse(Call<ArrayList<Room>> call, Response<ArrayList<Room>> response) {
                if (response.isSuccessful()){
                    final ArrayList<Room> items = response.body();

                    profileAdapter = new Profile_adapter(items, SearchActivity.this);
                    grid_picture.setAdapter(profileAdapter);
                    for (int i = 0; i < items.size(); i++) {
                        Log.d("profile", "profile : " + items.get(i).toString());
                    }
                    grid_picture.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Room item = items.get(position);

                            Intent intent = new Intent(SearchActivity.this, RoomActivity.class);
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
    @OnClick(R.id.img_input)
    public void onClickChange(View view){
        initDialog();
    }

    private  void  initDialog(){
        ChooseDialog dialog = new ChooseDialog(this, new ChooseDialog.ChooseListener() {
            @Override
            public void choosePhoto() {

                Intent intent = new Intent(SearchActivity.this,upload_Activity.class);
                startActivity(intent);
                Log.d("bjh","re: " + 3);
            }

            @Override
            public void chooseCamer() {


                Intent intent = new Intent(SearchActivity.this,upload_btn_photo_activity.class);

                startActivity(intent);
                Log.d("bjh","re: " + 55);
            }
        });
        dialog.show();
    }
    @OnClick(R.id.img_home)
    public void onReturnHome(View view) {
        Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_option)
    public void onReturnOption(View view) {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.linearLayout_search)
    public void onClickMain(View view){

    }

}
