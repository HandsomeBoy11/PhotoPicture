package com.xrd.photopicture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.iwf.photopicker.PhotoPicker;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.nsrv)
    RecyclerView nsrv;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    private Unbinder bind;
    private MyAdapter myAdapter;
    private ArrayList<String> pics = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        MyGridLayoutManager manager = new MyGridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        manager.setCanScroll(false);
        nsrv.setLayoutManager(manager);
        myAdapter = new MyAdapter(this);
        nsrv.setAdapter(myAdapter);
        myAdapter.setPicture(pics);
        myAdapter.setmCallBack(new MyAdapter.AddCallBack() {
            @Override
            public void addPic() {
                PhotoPicker.builder()
                        .setPhotoCount(9 - myAdapter.getRellPicSize())
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(MainActivity.this, PhotoPicker.REQUEST_CODE);
            }

            @Override
            public void deletePosition(int position) {
                pics.remove(position);
            }

            @Override
            public void enterBigPic(Context mContext, View view, int position) {
                BigPicActivity.startAct(mContext, view, pics, position);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                if (photos.size() > 0) {
                    pics.addAll(photos);
                    myAdapter.setPicture(pics);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @OnClick(R.id.bt_submit)
    public void onViewClicked() {
        String msg = etText.getText().toString().trim();
        tv.setText(msg) ;
    }
}
