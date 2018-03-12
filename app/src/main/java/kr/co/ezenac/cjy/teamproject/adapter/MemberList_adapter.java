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
import java.util.HashMap;

import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.model.Member;

/**
 * Created by Administrator on 2018-02-23.
 */

public class MemberList_adapter extends BaseAdapter {
    Member item ;
    ArrayList<Member> items = new ArrayList<>();
    Context context;

    public MemberList_adapter(ArrayList<Member> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public MemberList_adapter(Member item, Context context) {
        this.item = item;
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
    public View getView(int position, View convertView1, ViewGroup parent) {
        Holder holder = new Holder();

        if (convertView1 == null){
            convertView1 = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.activity_memberlist, parent, false);
            holder.memberList_img = convertView1.findViewById(R.id.memberList_img);
            holder.memberList_id = convertView1.findViewById(R.id.memberList_id);

            Log.d("bjh3", "0: " + convertView1);
            Log.d("bjh3","1:" + holder.memberList_img);
            Log.d("bjh3","2:" + holder.memberList_id);

            convertView1.setTag(holder);
        } else {
            holder = (Holder) convertView1.getTag();
        }

        Member item = (Member) getItem(position);
        Log.d("ddd78", item.toString());
        Glide.with(context).load(item.getMember_img()).centerCrop().into(holder.memberList_img);
        holder.memberList_id.setText(item.getLogin_id());



        return convertView1;
    }

    private class Holder{
        ImageView memberList_img;
        TextView memberList_id;
    }
}
