package kr.co.ezenac.cjy.teamproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.model.Img;

/**
 * Created by Administrator on 2018-02-02.
 */

public class Profile_adapter extends BaseAdapter{
    ArrayList<Img> imgs = new ArrayList<>();
    Context context;
    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object getItem(int position) {
        return imgs.get(position);
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
            holder.img_proGridRoom = convertView.findViewById(R.id.img_gridProfile);
            holder.text_proGridRoom = convertView.findViewById(R.id.text_gridRoomName);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Img item = (Img) getItem(position);
        holder.text_proGridRoom.setText(item.getContent());
        Glide.with(context).load(item.getPath()).centerCrop().into(holder.img_proGridRoom);

        return convertView;
    }

    private class Holder {
        ImageView img_proGridRoom;
        TextView text_proGridRoom;
    }
}
