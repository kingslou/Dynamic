package com.luoji.dynamic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.luoji.dynamic.databinding.ActivityMainBinding;
import com.luoji.pluginlibary.IBean;

import java.io.File;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    //apk名称
    private String apkName = "plugin-debug.apk";

    private DexClassLoader dexClassLoader;

    private AssetManager mAssetManager;
    private Resources mResources;
    private Resources.Theme mTheme;
    private String dexpath = null;    //apk文件地址
    private File fileRelease = null;  //释放目录
    private ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(activityMainBinding.getRoot());

        File extractFile = this.getFileStreamPath(apkName);
        dexpath = extractFile.getPath();

        fileRelease = getDir("dex", 0); //0 表示Context.MODE_PRIVATE

        Log.d("DEMO", "dexpath:" + dexpath);
        Log.d("DEMO", "fileRelease.getAbsolutePath():" +
                fileRelease.getAbsolutePath());
        dexClassLoader = new DexClassLoader(dexpath,fileRelease.getAbsolutePath(),null,getClassLoader());
        activityMainBinding.btnDynamic.setOnClickListener(v->{
            Class mClassBean = null;
            try{
                mClassBean = dexClassLoader.loadClass("com.luoji.plugin.BeanTest");
                if(mClassBean!=null){
                    Object mObject = mClassBean.newInstance();
                    IBean bean = (IBean) mObject;
                    bean.setName("我是另一个插件里面的类");
                    Log.e("打印",bean.getName());
                    activityMainBinding.btnDynamic.setText(bean.getName());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        Utils.extractAssets(this,apkName);
    }
}
