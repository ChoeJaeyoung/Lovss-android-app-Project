package kr.co.ezenac.cjy.teamproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Room;

/**
 * Created by Administrator on 2018-02-05.
 */

public class Room_adapter extends BaseAdapter {
    ArrayList<Img> items = new ArrayList<>();
    Context context;

    public Room_adapter(ArrayList<Img> items, Context context) {
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
                    inflate(R.layout.room_adapter, parent, false);
            holder.img_roomAdapter = convertView.findViewById(R.id.img_roomAdapter);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

            Img item = (Img) getItem(position);
            Glide.with(context).load(item.getPath()).centerCrop().into(holder.img_roomAdapter);



        return convertView;
    }

    private class Holder{
        ImageView img_roomAdapter;
    }
}
