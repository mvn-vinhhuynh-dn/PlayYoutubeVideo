package com.example.asiantech.playyoutubevideo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asiantech.playyoutubevideo.core.ApiClient;
import com.example.asiantech.playyoutubevideo.core.ApiConfig;
import com.example.asiantech.playyoutubevideo.core.api.MySession;
import com.example.asiantech.playyoutubevideo.fragment.HomeFragment_;
import com.example.asiantech.playyoutubevideo.fragment.LikeFragment_;
import com.example.asiantech.playyoutubevideo.fragment.SettingFragment_;
import com.example.asiantech.playyoutubevideo.fragment.TimerFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class Main extends FragmentActivity {

    @ViewById(R.id.frLayout)
    FrameLayout mFrameLayout;
    // linearlayout home
    @ViewById(R.id.llHome)
    LinearLayout llHome;
    @ViewById(R.id.imgHome)
    ImageView mImgHome;
    @ViewById(R.id.tvHome)
    TextView mTvHome;
    // LinearLayout like
    @ViewById(R.id.llLike)
    LinearLayout llLike;
    @ViewById(R.id.imgLike)
    ImageView mImgLike;
    @ViewById(R.id.tvLike)
    TextView mTvLike;
    // LinearLayout Timer
    @ViewById(R.id.llTimer)
    LinearLayout llTimer;
    @ViewById(R.id.imgTimer)
    ImageView mImgTimer;
    @ViewById(R.id.tvTimer)
    TextView mTvTimer;
    //LinearLayout setting
    @ViewById(R.id.llSetting)
    LinearLayout llSetting;
    @ViewById(R.id.imgSetting)
    ImageView mImgSetting;
    @ViewById(R.id.tvSetting)
    TextView mTvSetting;

    // bien libary
    private int mCount;
    private Fragment mFragment;

    @AfterViews
    void contentLayout() {
     // getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        ApiConfig apiConfig = ApiConfig.builder(getApplicationContext())
                .baseUrl(getResources().getString(R.string.url_topnew))
                .sessionStore(new MySession())
                .build();
        ApiClient.getInstance().init(apiConfig);
        selectHome();
        changeFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void selectHome() {
        if (mCount == 0) {
            mImgHome.setImageResource(R.drawable.choose_selected);
            mImgLike.setImageResource(R.drawable.mychannel_unselected);
            mImgSetting.setImageResource(R.drawable.setting_unselected);
            mImgTimer.setImageResource(R.drawable.timetable_unselected);
            mTvHome.setTextColor(getResources().getColor(R.color.selecttext));
            mTvLike.setTextColor(getResources().getColor(R.color.white));
            mTvTimer.setTextColor(getResources().getColor(R.color.white));
            mTvSetting.setTextColor(getResources().getColor(R.color.white));
            mFragment = new HomeFragment_().builder().build();
        }
    }

    public void changeFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frLayout, mFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Click(R.id.llHome)
    void clickHome() {
        mCount = 0;
        mImgHome.setImageResource(R.drawable.choose_selected);
        mImgLike.setImageResource(R.drawable.mychannel_unselected);
        mImgSetting.setImageResource(R.drawable.setting_unselected);
        mImgTimer.setImageResource(R.drawable.timetable_unselected);
        mTvHome.setTextColor(getResources().getColor(R.color.selecttext));
        mTvLike.setTextColor(getResources().getColor(R.color.white));
        mTvTimer.setTextColor(getResources().getColor(R.color.white));
        mTvSetting.setTextColor(getResources().getColor(R.color.white));
        mFragment = new HomeFragment_().builder().build();
        changeFragment();
    }

    @Click(R.id.llLike)
    void clickLike() {
        mCount = 1;
        mImgHome.setImageResource(R.drawable.choose_unselected);
        mImgLike.setImageResource(R.drawable.mychannel_selected);
        mImgTimer.setImageResource(R.drawable.timetable_unselected);
        mImgSetting.setImageResource(R.drawable.setting_unselected);
        mTvLike.setTextColor(getResources().getColor(R.color.selecttext));
        mTvHome.setTextColor(getResources().getColor(R.color.white));
        mTvTimer.setTextColor(getResources().getColor(R.color.white));
        mTvSetting.setTextColor(getResources().getColor(R.color.white));
        mFragment = new LikeFragment_().builder().build();
        changeFragment();
    }

    @Click(R.id.llTimer)
    void clickTimer() {
        mCount = 2;
        mImgLike.setImageResource(R.drawable.mychannel_unselected);
        mImgTimer.setImageResource(R.drawable.timetable_selected);
        mImgSetting.setImageResource(R.drawable.setting_unselected);
        mImgHome.setImageResource(R.drawable.choose_unselected);
        mTvTimer.setTextColor(getResources().getColor(R.color.selecttext));
        mTvLike.setTextColor(getResources().getColor(R.color.white));
        mTvHome.setTextColor(getResources().getColor(R.color.white));
        mTvSetting.setTextColor(getResources().getColor(R.color.white));
        mFragment = new TimerFragment_().builder().build();
        changeFragment();
    }

    @Click(R.id.llSetting)
    void clickSetting() {
        mCount = 3;
        mImgTimer.setImageResource(R.drawable.timetable_unselected);
        mImgLike.setImageResource(R.drawable.mychannel_unselected);
        mImgSetting.setImageResource(R.drawable.setting_selected);
        mImgHome.setImageResource(R.drawable.choose_unselected);
        mTvSetting.setTextColor(getResources().getColor(R.color.selecttext));
        mTvLike.setTextColor(getResources().getColor(R.color.white));
        mTvTimer.setTextColor(getResources().getColor(R.color.white));
        mTvHome.setTextColor(getResources().getColor(R.color.white));
        mFragment = new SettingFragment_().builder().build();
        changeFragment();
    }

}
