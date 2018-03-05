package kr.co.ezenac.cjy.teamproject.customview;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.Toast;

import kr.co.ezenac.cjy.teamproject.ClearActivity;

/**
 * Created by Administrator on 2018-02-28.
 */

public class BackPressCloseHandler {
    private long backKeypressedTime = 0;
    private Toast toast;

    private Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public BackPressCloseHandler(Activity context){
        this.activity = context;
    }

    public void onBackPressed(){
        if(backKeypressedTime == 0){
            activity.finish();
            backKeypressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int)(System.currentTimeMillis() - backKeypressedTime);

            if (seconds < 2000){
                showGuide();
                int thirds = (int)(System.currentTimeMillis() - (seconds + backKeypressedTime));
                if (thirds < 2000){
                    Intent intent = new Intent(getActivity(), ClearActivity.class);
                    intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    activity.startActivity(intent);
                }
                else {
                    backKeypressedTime = 0;
                }

            }
            else {
                backKeypressedTime = 0;
            }
        }

        /*if (System.currentTimeMillis() > backKeypressedTime + 2000){
            backKeypressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeypressedTime + 2000){
            activity.finish();
            toast.cancel();
        }*/
    }

    public void showGuide(){
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
