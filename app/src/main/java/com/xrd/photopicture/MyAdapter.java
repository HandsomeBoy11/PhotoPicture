package com.xrd.photopicture;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018/9/28.
 */

public class MyAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<String> pictures = new ArrayList<>();
    private final LayoutInflater from;
    private boolean showAdd=true;

    public MyAdapter(Context mContext) {
        this.mContext = mContext;
        from = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = from.inflate(R.layout.pic_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        ImageView ivDelete = holder1.ivDelete;
        final ImageView ivPic = holder1.ivPic;
        final String path = pictures.get(position);
        if(TextUtils.isEmpty(path)&&showAdd){
            Glide.with(mContext).load(R.drawable.addphoto).into(ivPic);
            ivDelete.setVisibility(View.GONE);
        }else{
            Glide.with(mContext).load(path).into(ivPic);
            ivDelete.setVisibility(View.VISIBLE);
        }
        //点击删除
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictures.remove(position);
                if(pictures.size()<=9&&!showAdd){//没有显示加添，让显示添加
                    pictures.add("");
                    showAdd=true;
                }
                if(mCallBack!=null){
                    mCallBack.deletePosition(position);
                }
                notifyDataSetChanged();

            }
        });
        ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(path)&&showAdd){//添加图片
                    if(mCallBack!=null){
                        mCallBack.addPic();
                    }
                }else{//进入放大图片
                    Toast.makeText(mContext, "进入放大图片", Toast.LENGTH_SHORT).show();
                    if(mCallBack!=null){
                        mCallBack.enterBigPic(mContext,ivPic,position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }
    public ArrayList<String> getPictures(){
        return pictures;
    }
    public int getRellPicSize(){
        return showAdd?pictures.size()-1:pictures.size();
    }

    public void setPicture(ArrayList<String> picture) {
        pictures.clear();
        pictures.addAll(picture);
        if(pictures.size()<9){
            pictures.add("");
            showAdd = true;
        }else{
            showAdd=false;
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    private AddCallBack mCallBack;

    public void setmCallBack(AddCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public interface AddCallBack{
        void addPic();
        void deletePosition(int position);
        void enterBigPic(Context mContext,View view,int position);
    }
}
