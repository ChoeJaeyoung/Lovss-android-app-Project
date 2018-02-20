package kr.co.ezenac.cjy.teamproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import kr.co.ezenac.cjy.teamproject.Fragment.CollectionFragment;
import kr.co.ezenac.cjy.teamproject.Fragment.RoomFragment;

/**
 * Created by Administrator on 2018-02-20.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> items = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        items.add(new RoomFragment());
        items.add(new CollectionFragment());
    }

    @Override
    public Fragment getItem(int position) {
       if(position == 0){
           return items.get(0);
       }else{
           return items.get(1);
       }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public Fragment getFragmentIndex(int position) {
        return items.get(position);
    }

}
