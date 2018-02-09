package kr.co.ezenac.cjy.teamproject.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemLongClick;
import butterknife.OnLongClick;
import kr.co.ezenac.cjy.teamproject.MainActivity;
import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.SearchActivity;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Join;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-02-02.
 */

public class Profile_adapter extends BaseAdapter{
    ArrayList<Room> items = new ArrayList<>();
    Context context;
    private Integer room_id;

    public Profile_adapter(ArrayList<Room> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.profile_gridadapter, parent, false);
            holder.img_proGridRoom = convertView.findViewById(R.id.img_proGridRoom);
            holder.text_proGridRoom = convertView.findViewById(R.id.text_proGridRoom);
            holder.btn_proDelete = convertView.findViewById(R.id.btn_proDelete);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        ButterKnife.bind(this,convertView);


        Room item = (Room) getItem(position);
        holder.text_proGridRoom.setText(item.getName());
        Glide.with(context).load(item.getRoom_img()).centerCrop().into(holder.img_proGridRoom);
        Log.d("ttt", item.getName() + " / " + item.getRoom_img().toString());
        room_id = item.getId();
        Integer tmpId= LoginInfo.getInstance().getMember().getId();

        return convertView;
    }

    private class Holder {
        ImageView img_proGridRoom;
        TextView text_proGridRoom;
        Button btn_proDelete;
    }

    @OnLongClick(R.id.img_proGridRoom)
    public boolean viewDelete(View view){
        Log.d("ppp", "longclick");
        if (view.getVisibility() == View.GONE){
            view.setVisibility(View.VISIBLE);
        }
        return true;
    }

    @OnClick(R.id.btn_proDelete)
    public void deleteItem(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("이 방을 삭제하시겠습니까?");
        alertDialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer member_id = LoginInfo.getInstance().getMember().getId();
                Call<Void> obser = RetrofitService.getInstance().getRetrofitRequest()
                        .deleteRoom(member_id, room_id);
                obser.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Log.d("ppp", response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


    }
}
