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

import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-02-23.
 */

public class Search_adapter extends BaseAdapter {
    ArrayList<Room> items = new ArrayList<>();
    Integer mode = 0;
    Context context;

    public  Search_adapter(ArrayList<Room> items, Context context){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.sarch_adapter, parent, false);
            holder.img_search_lock = convertView.findViewById(R.id.img_search_lock);
            holder.img_search_room = convertView.findViewById(R.id.img_search_room);
            holder.text_search_Room = convertView.findViewById(R.id.text_search_Room);



            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }




        Room item = (Room) getItem(position);
        holder.text_search_Room.setText(item.getName());
        Glide.with(context).load(item.getRoom_img()).centerCrop().into(holder.img_search_room);
        final Integer room_id = item.getId();

        if (item.getRoom_ps().equals("0")){
            holder.img_search_lock.setVisibility(View.GONE);
        } else {
            holder.img_search_lock.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    private class Holder {
        ImageView img_search_lock;
        TextView text_search_Room;
        ImageView img_search_room;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }
}
