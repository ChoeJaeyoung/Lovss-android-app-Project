package kr.co.ezenac.cjy.teamproject;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.customview.CustomDialog;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import kr.co.ezenac.cjy.teamproject.util.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class upload_Activity extends AppCompatActivity {

    @BindView(R.id.btn_upload) TextView btn_upload; //ok
    @BindView(R.id.text_title) EditText text_title; //ok
    @BindView(R.id.  btn_open_room) Button  btn_open_room; //ok
    @BindView(R.id.  btn_hidden_room) Button  btn_hidden_room; //ok
    @BindView(R.id.img_add) ImageView img_add;
    @BindView(R.id.img_room_home) ImageView img_room_home; //ok
    @BindView(R.id.img_room_search) ImageView img_room_search; //ok
    @BindView(R.id.img_room_input) ImageView img_room_input; //ok
    @BindView(R.id.img_room_option) ImageView img_room_option; //ok
    @BindView(R.id.btn_logout) ImageView btn_logout;
    String password;






    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);

    }




    @OnClick(R.id.btn_hidden_room)
    public void onClickbtn_hidden_room(View view){
        password = "000000";
        btn_hidden_room.setBackgroundResource(R.color.colorBTN);
        btn_open_room.setBackgroundResource(R.color.colorGray);
    }


    @OnClick(R.id.btn_open_room)
    public void onClickbtn_open_room(View view){
        btn_open_room.setBackgroundResource(R.color.colorBTN);
        btn_hidden_room.setBackgroundResource(R.color.colorGray);
        CustomDialog dialog = new CustomDialog(upload_Activity.this);
        dialog.setCallbacks(new CustomDialog.Callbacks() {
            @Override
            public void onClickSend(String name) {
                password = name;
            }
        });
        dialog.show();
    }


    @OnClick(R.id.img_room_input)
    public void onClickChange(View view){
        initDialog();
    }

    private  void  initDialog(){
        ChooseDialog dialog = new ChooseDialog(this, new ChooseDialog.ChooseListener() {
            @Override
            public void choosePhoto() {

                Intent intent = new Intent(upload_Activity.this,upload_Activity.class);
                startActivity(intent);
                Log.d("bjh","re: " + 3);
            }

            @Override
            public void chooseCamer() {


                Intent intent = new Intent(upload_Activity.this,upload_btn_photo_activity.class);

                startActivity(intent);
                Log.d("bjh","re: " + 55);
            }
        });
        dialog.show();
    }
    @OnClick(R.id.img_room_home)
    public void onReturnHome(View view) {
        Intent intent = new Intent(upload_Activity.this, HomeActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_room_search)
    public void onReturnSearch(View view) {
        Intent intent = new Intent(upload_Activity.this, SearchActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_room_option)
    public void onReturnOption(View view) {
        Intent intent = new Intent(upload_Activity.this, MainActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btn_upload)
    public void onClickBtnAdd(View view) {


        if (file == null || text_title.getText().toString().equals("")) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(upload_Activity.this);
            alertDialog.setTitle("!");
            alertDialog.setMessage("사진과 글을 채워주세요");
            alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            alertDialog.show();


        } else {

            Log.d("test11", "111 : " + file.getName());


            final MultipartBody.Part filePart =
                    MultipartBody.Part.createFormData("file",
                            file.getName(),
                            RequestBody.create(MediaType.parse("image/*"), file));
            Log.d("bjh", "file : " + file.toString());
            final String name = text_title.getText().toString();
            final Integer member_id = LoginInfo.getInstance().getMember().getId();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(upload_Activity.this);
            alertDialog.setTitle("Q.");
            alertDialog.setMessage("이 방 이름이 확실합니까?");
            alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("jjj", "" + filePart);
                    Log.d("jjj", "" + text_title.toString());


                    Log.d("bjh", "tete" + text_title.toString());

                    final RequestBody nameBody =
                            RequestBody.create(MediaType.parse("text/plain"), name);

                    final RequestBody passwordBody =
                            RequestBody.create(MediaType.parse("text/plain"), password);

                    final RequestBody idBody =
                            RequestBody.create(MediaType.parse("text/plain"), String.valueOf(member_id));

                    Call<Room> obserV = RetrofitService.getInstance().getRetrofitRequest().makeRoom(filePart, nameBody, idBody, passwordBody);
                    obserV.enqueue(new Callback<Room>() {
                        @Override
                        public void onResponse(Call<Room> call, Response<Room> response) {
                            if (response.isSuccessful()) {
                                Log.d("bjh", "suc");

                                final Room room = response.body();

                                Log.d("bjh", "re:" + room.getId());

                                if (room.getId() == 1) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(upload_Activity.this);
                                    alertDialog.setTitle("경고");
                                    alertDialog.setMessage("이미 동일한 방이 존재합니다.");
                                    alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {


                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });

                                    alertDialog.show();
                                } else {
                                    Intent intent = new Intent(upload_Activity.this, RoomActivity.class);
                                    intent.putExtra("room_id", room.getId());
                                    intent.putExtra("room_name", room.getName());
                                    intent.putExtra("room_img", room.getRoom_img());
                                    startActivity(intent);

                                }
                            } else {
                                Log.d("bjh", "fail");
                            }
                        }

                        @Override
                        public void onFailure(Call<Room> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            });
            alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
        }
    }




    @OnClick(R.id.btn_titleImg)
    public void onClickBtnTitleImg(View view) {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Select picture"), 0);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        TedPermission.with(upload_Activity.this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Glide.with(upload_Activity.this).load(data.getData()).centerCrop().into(img_add);
        //String room_Img = img_add.getDrawable().toString();

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                file = new File(
                        RealPathUtil.getRealPath(upload_Activity.this,
                                data.getData()));
                if (file.exists()) {
                    Log.d("bjh", "exist");
                }
            }
        }
    }
    @OnClick(R.id.btn_logout)
    public void onClickLogout(View view){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(upload_Activity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("로그아웃 하시겠습니까?");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(upload_Activity.this, LoginActivity.class);
                startActivity(intent);
            }

        });
        alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

}