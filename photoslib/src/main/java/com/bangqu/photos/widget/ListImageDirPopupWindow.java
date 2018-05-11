package com.bangqu.photos.widget;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.List;

import com.bangqu.photos.R;
import com.bangqu.photos.adapter.CommonAdapter;
import com.bangqu.photos.bean.ImageFloder;
import com.bangqu.photos.util.ViewHolder;

public class ListImageDirPopupWindow extends BasePopupWindowForListView<ImageFloder> {
    private String selectDir = "all";
    private ListView mListDir;

    public ListImageDirPopupWindow(int width, int height, List<ImageFloder> datas, View convertView) {
        super(convertView, width, height, true, datas);
    }

    @Override
    public void initViews() {
        mListDir = (ListView) findViewById(R.id.id_list_dir);
        mListDir.setAdapter(new CommonAdapter<ImageFloder>(context, mDatas, R.layout.item_list_floder) {
            @Override
            public void convert(ViewHolder helper, ImageFloder item) {
                helper.setText(R.id.id_dir_item_name, item.getName());
                helper.setText(R.id.id_dir_item_count, item.getCount() + "张");
                helper.setImageByUrl(R.id.id_dir_item_image, item.getFirstImagePath());
                if (selectDir.equals(item.getDir())) {
                    helper.setVisibility(R.id.id_dir_item_checked, View.VISIBLE);
                } else {
                    helper.setVisibility(R.id.id_dir_item_checked, View.GONE);
                }
            }
        });
    }

    public interface OnImageDirSelected {
        void selected(ImageFloder floder);
    }

    private OnImageDirSelected mImageDirSelected;

    public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
        this.mImageDirSelected = mImageDirSelected;
    }

    @Override
    public void initEvents() {
        mListDir.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mImageDirSelected != null) {
                    mImageDirSelected.selected(mDatas.get(position));
                    selectDir = mDatas.get(position).getDir();
                }
            }
        });
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {
        // TODO Auto-generated method stub
    }

}
