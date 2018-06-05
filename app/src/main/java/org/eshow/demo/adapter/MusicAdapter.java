package org.eshow.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangqu.greendao.entity.Person;
import com.bangqu.lib.base.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;

import org.eshow.demo.R;
import org.eshow.demo.model.MusicModel;
import org.eshow.demo.util.MusicUtils;

import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-5-23 0023.
 */

public class MusicAdapter extends BaseRecyclerAdapter<MusicModel> {

    public MusicAdapter(Context mContext, List<MusicModel> mData) {
        super(mContext, mData);
    }

    @Override
    protected RecyclerView.ViewHolder initViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_music, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void bindingViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        MusicModel model = mData.get(position);
        viewHolder.song.setText(model.song.substring(0, model.song.lastIndexOf(".")));
        viewHolder.singer.setText(model.singer);
        Glide.with(mContext).load(MusicUtils.getAlbumArt(mContext, model.album_id))
                .into(viewHolder.cover);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.music_cover)
        ImageView cover;
        @BindView(R.id.music_song)
        TextView song;
        @BindView(R.id.music_singer)
        TextView singer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
