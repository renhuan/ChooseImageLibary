package com.example.rhchooseimagelibary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

import static android.app.Activity.RESULT_OK;

/***
 *  @author Remond
 *  on 2018/9/29
 *  作用：图片上传  类似九宫格
 *
 *  用法：
 *  1. upLoadImage.setAdapter(4, list, new UploadImage.DeleteCallBack() {
 *    @Override public void delete(List<String> list) {
 *      //此处是删除时候返回的list地址
 *   }
 *  2. @Override
 *     protected void onActivityResult(int requestCode, int resultCode, Intent data)
 *     {
 *        super.onActivityResult(requestCode, resultCode, data);
 *       List<String> list = upLoadImage.onActivityResult(requestCode, resultCode, data);
 *      //此处是选择图片时候返回的list地址
 *     }
});
 */
public class UploadImage extends RelativeLayout {


    //默认数量
    public static final int NUM = 9;

    private RecyclerView recyclerView;

    private List<String> list;
    private BaseQuickAdapter<String, BaseViewHolder> adapter;
    //最多九张照片
    public static int IMAGE_NUM = 10;
    public static final String PLACEHOLDER_IMAGE = "file:///android_asset/fabu_anniu.png";

    private Context context;

    public UploadImage(Context context) {
        this(context, null);
    }

    public UploadImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_recyclerview, (ViewGroup) getRootView(), true);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setNestedScrollingEnabled(false);
    }

    /**
     * 第一步
     *
     * @param num
     * @param list
     * @param callBack
     */
    public void setAdapter(int num, List<String> list, DeleteCallBack callBack) {
        this.list = list;
        IMAGE_NUM = num + 1;
        list.add(list.size(), UploadImage.PLACEHOLDER_IMAGE);
        recyclerView.setAdapter(adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_img_delet, list) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                if (PLACEHOLDER_IMAGE.equals(item)) {
                    helper.setVisible(R.id.iv_del, false);
                } else {
                    helper.setVisible(R.id.iv_del, true);
                }
                helper.addOnClickListener(R.id.iv_del);
                Glide.with(context).load(item).into((ImageView) helper.getView(R.id.iv));
            }
        });
        /**
         * 删除回调
         */
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            list.remove(position);
            notifyDataSetChanged();
            callBack.delete(getRealList());
        });
        adapter.setOnItemClickListener((adapter, view, position) -> {
            if (PLACEHOLDER_IMAGE.equals(list.get(position))) {
                //选择图片
                PhotoPicker.builder()
                        .setPhotoCount(IMAGE_NUM - list.size())
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(true)
                        .start((Activity) context, PhotoPicker.REQUEST_CODE);
            }

        });
    }

    /**
     * 返回已经选取图片的地址  第二步
     */
    public List<String> onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                for (String s :
                        photos) {
                    list.add(list.size() - 1, s);
                }
                notifyDataSetChanged();

                return getRealList();
            }
        }
        return getRealList();
    }

    @NonNull
    private List<String> getRealList() {
        List<String> temp = new ArrayList<>();
        for (String s :
                list) {
            if (!PLACEHOLDER_IMAGE.equals(s)) {
                temp.add(s);
            }
        }
        return temp;
    }

    public void notifyDataSetChanged() {
        boolean is = false;
        for (String s :
                list) {
            if (PLACEHOLDER_IMAGE.equals(s)) {
                is = true;
                break;
            }
        }
        if (!is) {
            addPlaceHoldeImage();
        }
        if (list.size() >= IMAGE_NUM) {
            removePlaceHoldeImage();
        }
        adapter.notifyDataSetChanged();
    }

    public void removePlaceHoldeImage() {
        list.remove(list.size() - 1);
    }

    public void addPlaceHoldeImage() {
        list.add(list.size(), PLACEHOLDER_IMAGE);
    }


    public interface DeleteCallBack {
        void delete(List<String> list);
    }
}
