package kr.co.ezenac.cjy.teamproject;

import android.Manifest;
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
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.adapter.Profile_adapter;
import kr.co.ezenac.cjy.teamproject.model.Member;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
import kr.co.ezenac.cjy.teamproject.util.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.img_mainProfile) ImageView img_mainProfile;
    @BindView(R.id.gird_main) GridView grid_main;
    @BindView(R.id.text_mainId) TextView text_mainId;
    Profile_adapter profileAdapter;
    @BindView(R.id.img_home) ImageView img_home;
    @BindView(R.id.img_search) ImageView img_search;
    @BindView(R.id.img_input) ImageView img_input;
    @BindView(R.id.img_option) ImageView img_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Integer tmpId = LoginInfo.getInstance().getMember().getId();
        String tmpMember_id = LoginInfo.getInstance().getMember().getLogin_id().toString();
        String tmpMember_img = LoginInfo.getInstance().getMember().getMember_img();


        callLoginInfo(tmpId);
        Glide.with(MainActivity.this).load(tmpMember_img).centerCrop().
                into(img_mainProfile);
        text_mainId.setText(tmpMember_id);

        Log.d("img", "img : " + LoginInfo.getInstance().getMember().getMember_img());
    }

    @OnClick(R.id.img_mainProfile)
    public void onClickProfileImg(View view){
        PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"),0);
            }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

        }
    };

        TedPermission.with(MainActivity.this)
            .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
}

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == 0){
                File file = new File(RealPathUtil.getRealPath(MainActivity.this, data.getData()));
                if (file.exists()){
                    Log.d("fff", "exist");
                }
                MultipartBody.Part filePart =
                        MultipartBody.Part.createFormData("file", file.getName(),
                                RequestBody.create(MediaType.parse("image/*"),file));
                Integer id = LoginInfo.getInstance().getMember().getId();

                RequestBody loginIdBody =
                        RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id));

                Call<Member> observ = RetrofitService.getInstance().getRetrofitRequest().updateProfile(filePart, loginIdBody);
                observ.enqueue(new Callback<Member>() {
                    @Override
                    public void onResponse(Call<Member> call, Response<Member> response) {
                        if (response.isSuccessful()){
                            Member member = response.body();
                            LoginInfo.getInstance().setMember(member);
                            Log.d("yyy", member.toString());
                            Log.d("yyy", LoginInfo.getInstance().getMember().getMember_img());

                            String updateImg = LoginInfo.getInstance().getMember().getMember_img();
                            Glide.with(MainActivity.this).load(updateImg).centerCrop().
                                    into(img_mainProfile);
                            Log.d("profile", "success : " + member.toString());


                        } else {
                            Log.d("profile", "fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<Member> call, Throwable t) {

                    }
                });
            }
        }
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
               for (int i = 0; i < items.size(); i++) {
                   Log.d("profile", "profile : " + items.get(i).toString());
               }
               grid_main.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Room item = items.get(position);

                       Intent intent = new Intent(MainActivity.this, RoomActivity.class);
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

                Intent intent = new Intent(MainActivity.this,upload_Activity.class);
                startActivity(intent);
                Log.d("bjh","re: " + 3);
            }

            @Override
            public void chooseCamer() {


                Intent intent = new Intent(MainActivity.this,upload_btn_photo_activity.class);

                startActivity(intent);
                Log.d("bjh","re: " + 55);
            }
        });
        dialog.show();
    }
    @OnClick(R.id.img_home)
    public void onReturnHome(View view) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_search)
    public void onReturnSearch(View view) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_option)
    public void onReturnOption(View view) {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
