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

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
import kr.co.ezenac.cjy.teamproject.util.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class upload_imgActivity extends AppCompatActivity {

    @BindView(R.id.upload_img_btn_upload) TextView upload_img_btn_upload; // ok
    @BindView(R.id.upload_img_text_title) EditText upload_img_text_title; // ok
    @BindView(R.id.upload_img_img_add) ImageView upload_img_img_add; // ok
    @BindView(R.id.img_room_home) ImageView img_room_home;
    @BindView(R.id.img_room_search) ImageView img_room_search;
    @BindView(R.id.img_room_input) ImageView img_room_input;
    @BindView(R.id.img_room_option) ImageView img_room_option;
    @BindView(R.id.upload_img_btn_titleImg) ImageView upload_img_btn_titleImg;
    @BindView(R.id.linearLayout_upload_img) LinearLayout linearLayout_upload_img;
    @BindView(R.id.btn_logout) ImageView btn_logout;



    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.upload_img_btn_upload)
    public void onClickBtnAdd(View view) {

        if (file == null || upload_img_text_title.getText().toString().equals("")) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(upload_imgActivity.this);
            alertDialog.setTitle("!");
            alertDialog.setMessage("사진과 글을 채워주세요");
            alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            alertDialog.show();

        } else {
            final MultipartBody.Part filePart =
                    MultipartBody.Part.createFormData("file",
                            file.getName(),
                            RequestBody.create(MediaType.parse("image/*"), file));
            Log.d("bjh", "file : " + file.toString());
            String content = upload_img_text_title.getText().toString();

            Intent intent = getIntent();
            Integer troom_id = intent.getIntExtra("room_id", 0);


            String room_id = troom_id.toString();

            final RequestBody contentBody =
                    RequestBody.create(MediaType.parse("text/plain"),
                            content);
            final RequestBody room_numberBody =
                    RequestBody.create(MediaType.parse("text/plain"),
                            room_id); // 수정해야됩니다. ☆☆★


            Call<Room> obserV = RetrofitService.getInstance().getRetrofitRequest().makeImg(filePart, contentBody, room_numberBody);
            obserV.enqueue(new Callback<Room>() {
                @Override
                public void onResponse(Call<Room> call, Response<Room> response) {
                    if (response.isSuccessful()) {
                        Log.d("bjh", "suc");
                        Room item = response.body();
                        RoomInfo.getInstance().setRoom(item);
                        Log.d("upup", item.toString());
                        Log.d("ccc7", "" + item.toString());
                        Intent intent = new Intent(upload_imgActivity.this, RoomActivity.class);
                        intent.putExtra("room_id", item.getId());
                        intent.putExtra("room_name", item.getName());
                        intent.putExtra("room_img", item.getRoom_img());
                        startActivity(intent);

                        //finish();
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
    }

    @OnClick(R.id.upload_img_btn_titleImg)
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

        TedPermission.with(upload_imgActivity.this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Glide.with(upload_imgActivity.this).load(data.getData()).into(upload_img_img_add);
        //String room_Img = img_add.getDrawable().toString();

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                file = new File(
                        RealPathUtil.getRealPath(upload_imgActivity.this,
                                data.getData()));
                if (file.exists()) {
                    Log.d("bjh", "exist");
                }
            }
        }
    }
    @OnClick(R.id.img_room_input)
    public void onClickChange(View view){
        initDialog();
    }

    private  void  initDialog(){
        ChooseDialog dialog = new ChooseDialog(this, new ChooseDialog.ChooseListener() {
            @Override
            public void choosePhoto() {

                Intent intent = new Intent(upload_imgActivity.this,upload_Activity.class);
                startActivity(intent);
                Log.d("bjh","re: " + 3);
            }

            @Override
            public void chooseCamer() {


                Intent intent = new Intent(upload_imgActivity.this,upload_btn_photo_activity.class);

                startActivity(intent);
                Log.d("bjh","re: " + 55);
            }
        });
        dialog.show();
    }
    @OnClick(R.id.img_room_home)
    public void onReturnHome(View view) {
        Intent intent = new Intent(upload_imgActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_room_search)
    public void onReturnSearch(View view) {
        Intent intent = new Intent(upload_imgActivity.this, SearchActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_room_option)
    public void onReturnOption(View view) {
        Intent intent = new Intent(upload_imgActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.linearLayout_upload_img)
    public void onClickMain(View view){

    }
    @OnClick(R.id.btn_logout)
    public void onClickLogout(View view){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(upload_imgActivity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("로그아웃 하시겠습니까?");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(upload_imgActivity.this, LoginActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
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