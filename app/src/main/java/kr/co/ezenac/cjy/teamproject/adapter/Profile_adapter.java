package kr.co.ezenac.cjy.teamproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ezenac.cjy.teamproject.MainActivity;
import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Join;
import kr.co.ezenac.cjy.teamproject.model.Room;

/**
 * Created by Administrator on 2018-02-02.
 */

public class Profile_adapter extends BaseAdapter{
    ArrayList<Room> items = new ArrayList<>();
    Context context;

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

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Room item = (Room) getItem(position);
        holder.text_proGridRoom.setText(item.getName());
        Glide.with(context).load(item.getRoom_img()).centerCrop().into(holder.img_proGridRoom);
        Log.d("ttt", item.getName() + " / " + item.getRoom_img().toString());
        return convertView;
    }

    private class Holder {
        ImageView img_proGridRoom;
        TextView text_proGridRoom;
    }
}
