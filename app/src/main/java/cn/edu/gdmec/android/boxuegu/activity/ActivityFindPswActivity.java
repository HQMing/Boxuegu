package cn.edu.gdmec.android.boxuegu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.MD5Utils;

public class ActivityFindPswActivity extends AppCompatActivity {
    private EditText et_validate_name,et_user_name,et_validate_reset_name;
    private Button btn_validate,btn_validate_set;
    private TextView tv_main_title;
    private TextView tv_back;
    private String from;
    private TextView tv_reset_psw,tv_user_name,tv_reset_psw2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        from=getIntent().getStringExtra("from");
        init();
    }

    private void init() {
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_back=findViewById(R.id.tv_back);
        et_validate_name=findViewById(R.id.et_validate_name);
        btn_validate=findViewById(R.id.btn_validate);
        tv_reset_psw=findViewById(R.id.tv_reset_psw);
        et_user_name=findViewById(R.id.et_user_name);
        tv_user_name=findViewById(R.id.tv_user_name);
        et_validate_reset_name=findViewById(R.id.et_validate_reset_name);
        tv_reset_psw2=findViewById(R.id.tv_reset_psw);
        if ("security".equals(from)){//设置密保
            tv_main_title.setText("设置密保");
            btn_validate.setText("设置");
        }else {
            tv_main_title.setText("找回密码");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
        }
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityFindPswActivity.this.finish();
            }
        });
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validateName=et_validate_name.getText().toString().trim();
                if ("security".equals(from)){//设置密保
                    if (TextUtils.isEmpty(validateName)){
                        Toast.makeText(ActivityFindPswActivity.this,"请输入要验证的姓名",Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        Toast.makeText(ActivityFindPswActivity.this,"密保设置成功",Toast.LENGTH_LONG).show();
                        saveSecurity(validateName);
                        ActivityFindPswActivity.this.finish();
                    }
                }else{//找回密码
                    String userName=et_user_name.getText().toString().trim();
                    String sp_security=readSecurity(userName);
                    if (TextUtils.isEmpty(userName)){
                        Toast.makeText(ActivityFindPswActivity.this,"请输入您的用户名",Toast.LENGTH_LONG).show();
                        return;
                    }else if (!isExistUserName(userName)){
                        Toast.makeText(ActivityFindPswActivity.this,"您输入的用户名不存在",Toast.LENGTH_LONG).show();
                        return;
                    }else if (TextUtils.isEmpty(validateName)){
                        Toast.makeText(ActivityFindPswActivity.this,"请输入要验证的姓名",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!validateName.equals(sp_security)){
                        Toast.makeText(ActivityFindPswActivity.this,"输入的密保不正确",Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        tv_reset_psw2.setVisibility(View.VISIBLE);
                        et_validate_reset_name.setVisibility(View.VISIBLE);
                        btn_validate.setText("确认修改");
                        String newPsw=et_validate_reset_name.getText().toString().trim();
                        if (!TextUtils.isEmpty(newPsw)){
                            savePsw(userName,newPsw);
                        }

                    }
                }
            }
        });
    }
    private void saveSecurity(String validateName){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(AnalysisUtils.readLoginUserName(this)+"_security",validateName);
        editor.commit();
    }
    private String readSecurity(String userName){
        SharedPreferences sp=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String security=sp.getString(userName+"_security","");
        return security;
    }
    private boolean isExistUserName(String userName){
        boolean hasUserName=false;
        SharedPreferences sp=getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String spPsw=sp.getString(userName,"");
        if (!TextUtils.isEmpty(spPsw)){
            hasUserName=true;
        }
        return hasUserName;
    }
    private void savePsw(String userName,String newPsw){
        String md5Psw= MD5Utils.md5(newPsw);
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(userName,md5Psw);
        editor.commit();
        ActivityFindPswActivity.this.finish();
    }
}
