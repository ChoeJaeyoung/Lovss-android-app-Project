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

import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Room;

/**
 * Created by Administrator on 2018-02-08.
 */

public class Detail_adapter extends BaseAdapter {
    ArrayList<Img> items = new ArrayList<>();
    Context context;

    public Detail_adapter(ArrayList<Img> items, Context context) {
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
                    inflate(R.layout.detail_girdadapter, parent, false);
            holder.img_detailAdapter = convertView.findViewById(R.id.img_detailAdapter);
            holder.text_detailAdapter = convertView.findViewById(R.id.text_detailAdapter);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Img item = (Img) getItem(position);
        Log.d("ddd", item.toString());
        Glide.with(context).load(item.getPath()).centerCrop().into(holder.img_detailAdapter);
        holder.text_detailAdapter.setText(item.getContent());



        return convertView;
    }

    private class Holder{
        ImageView img_detailAdapter;
        TextView text_detailAdapter;
    }
}
