package com.xrd.photopicture;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BigPicActivity extends AppCompatActivity {
    private final String tag = getClass().getSimpleName();
    @BindView(R.id.vp_pic)
    ViewPager vpPic;
    @BindView(R.id.tv_indicator)
    TextView tvIndicator;
    private BigPicAdapter bigPicAdapter;
    private List<ImageView>  imageViews=new ArrayList<>();
    private ArrayList<String> pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_big_pic);
        ButterKnife.bind(this);
        initView();
        getData();

    }

    private void initView() {
        bigPicAdapter = new BigPicAdapter();
        vpPic.setAdapter(bigPicAdapter);
        vpPic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvIndicator.setText(position+1+"/"+pic.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static void startAct(Context mContext, View view, ArrayList<String> pics, int position) {
        Intent intent = new Intent(mContext, BigPicActivity.class);
        intent.putExtra("pic", pics);
        intent.putExtra("position", position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) mContext, view, "transition_animation_news_photos");
            mContext.startActivity(intent, options.toBundle());
        } else {

            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
        }
//        mContext.startActivity(intent);
    }

    public void getData() {
        Intent intent = getIntent();
        pic = (ArrayList<String>) intent.getSerializableExtra("pic");
        int position = intent.getIntExtra("position", 0);
        Log.e(tag, pic.size() + "   position   :" + position);
        imageViews.clear();
        for (int i = 0; i < pic.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageViews.add(imageView);
        }
        tvIndicator.setText(position+1+"/"+pic.size());
        bigPicAdapter.setPic(pic,imageViews);
        vpPic.setCurrentItem(position);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
