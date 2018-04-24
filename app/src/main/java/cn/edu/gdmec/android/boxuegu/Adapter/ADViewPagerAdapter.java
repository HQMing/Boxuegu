package cn.edu.gdmec.android.boxuegu.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by apple on 18/4/24.
 */

public class ADViewPagerAdapter extends PagerAdapter{
    private Context context;
    private List<ImageView> list;

    public ADViewPagerAdapter(Context context, List<ImageView> list){
        this.context = context;
        this.list = list;
    }

    public int getCount(){
        return list.size();
    }

    public boolean isViewFromObject(View view, Object object){
        return view==object;
    }

    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView(list.get(position));
    }

    public Object instantiateItem(ViewGroup containner,int position){
        containner.addView(list.get(position));
        return list.get(position);
    }
}
