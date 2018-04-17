package cn.edu.gdmec.android.boxuegu.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.boxuegu.Adapter.ExercisesDetailListItemAdapter;
import cn.edu.gdmec.android.boxuegu.Bean.ExercisesBean;
import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;

public class ActivityExercisesDetailActivity extends Activity  {

    private RecyclerView rv_list;
    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private int id;
    private String title;
    private List<ExercisesBean> eb1;
    private ExercisesDetailListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_detail);
        id = getIntent().getIntExtra("id",0);
        title = getIntent().getStringExtra("title");
        eb1 = new ArrayList<>();
        initData();
        initView();

    }
    private  void initData(){
        try {
            InputStream is = getResources().getAssets().open("chaper" + id + ".xml");
            eb1 = AnalysisUtils.getExercisesInfos(is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initView(){
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_main_title.setText(title);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityExercisesDetailActivity.this.finish();
            }
        });

    }

}
