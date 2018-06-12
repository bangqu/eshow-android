package org.eshow.demo.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bangqu.lib.widget.DividerItemDecoration;

import org.eshow.demo.R;
import org.eshow.demo.adapter.ContactsAdapter;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.model.ContactItem;
import org.eshow.demo.util.LogInfo;
import org.eshow.demo.widget.LetterNavigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class ContactsActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.contacts_list)
    RecyclerView contactsList;
    @BindView(R.id.letter_navi)
    LetterNavigation letterNavi;
    @BindView(R.id.letter_notice)
    TextView letterNotice;

    private List<ContactItem> contactItemList = new ArrayList<>();
    private ContactsAdapter contactsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private HashMap<String, Integer> listIndex = new HashMap<>();
    private boolean move;
    private int mIndex;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_contacts);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("联系人");
        linearLayoutManager = new LinearLayoutManager(this);
        contactsAdapter = new ContactsAdapter(this, contactItemList);
        contactsList.setLayoutManager(linearLayoutManager);
        contactsList.setAdapter(contactsAdapter);
//        contactsList.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL_LIST, 1f, Color.TRANSPARENT));
        getContactsList();
    }

    @Override
    protected void addViewListener() {
        super.addViewListener();
        letterNavi.setOnLetterChangeListener(new LetterNavigation.OnLetterChangeListener() {
            @Override
            public void letterChanged(String letter) {
                showLetterNotice(letter);
                if (listIndex.keySet().contains(letter)) {
                    moveToPosition(listIndex.get(letter));
                } else {
                    moveToPosition(contactItemList.size() - 1);
                }
            }
        });
        contactsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int fir = linearLayoutManager.findFirstVisibleItemPosition();
                letterNavi.setLetterFocused(String.valueOf(contactItemList.get(fir).index));
                showLetterNotice(String.valueOf(contactItemList.get(fir).index));
                if (move) {
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = mIndex - linearLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < contactsList.getChildCount()) {
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = contactsList.getChildAt(n).getTop();
                        //最后的移动
                        contactsList.scrollBy(0, top);
                    }
                }
            }
        });
    }

    private void moveToPosition(int index) {
        //获取当前recycleView屏幕可见的第一项和最后一项的Position
        int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = linearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (index <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            contactsList.scrollToPosition(index);
        } else if (index <= lastItem) {
            //当要置顶的项已经在屏幕上显示时，计算它离屏幕原点的距离
            int top = contactsList.getChildAt(index - firstItem).getTop();
            contactsList.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            contactsList.scrollToPosition(index);
            //记录当前需要在RecyclerView滚动监听里面继续第二次滚动
            move = true;
        }
    }

    private void showLetterNotice(String letter) {
        letterNotice.setText(letter);
        letterNotice.setVisibility(View.VISIBLE);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                letterNotice.setVisibility(View.GONE);
            }
        }, 500);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Collections.sort(contactItemList);
            dealListIndex();
            contactsAdapter.notifyDataSetChanged();
        }
    };

    private void dealListIndex() {
        String words[] = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        int i = 0;
        String iw = words[0];
        for (int j = 0; j < contactItemList.size(); j++) {
            String id = String.valueOf(contactItemList.get(j).index).toUpperCase();
            if (iw.equals(id)) {
                if (!listIndex.keySet().contains(words[i])) {
                    listIndex.put(words[i], j);
                }
            } else {
                i++;
                for (; i < words.length; i++) {
                    iw = words[i];
                    if (iw.equals(id)) {
                        if (!listIndex.keySet().contains(words[i])) {
                            listIndex.put(words[i], j);
                        }
                        break;
                    } else {
                        listIndex.put(words[i], j);
                    }
                }
            }
        }
    }

    private void getContactsList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
                ContentResolver cr = getContentResolver();
                Cursor cs = cr.query(uri, null, null, null, null, null);
                while (cs.moveToNext()) {
                    //拿到联系人id 跟name
                    int id = cs.getInt(cs.getColumnIndex("_id"));
                    String name = cs.getString(cs.getColumnIndex("display_name"));
                    //得到这个id的所有数据（data表）
                    Uri uri1 = Uri.parse("content://com.android.contacts/raw_contacts/" + id + "/data");
                    Cursor cs2 = cr.query(uri1, null, null, null, null, null);
                    ContactItem item = new ContactItem();
                    while (cs2.moveToNext()) {
                        //得到data这一列 ，包括很多字段
                        String data1 = cs2.getString(cs2.getColumnIndex("data1"));
                        //得到data中的类型
                        String type = cs2.getString(cs2.getColumnIndex("mimetype"));
                        String str = type.substring(type.indexOf("/") + 1, type.length());//截取得到最后的类型
                        if ("name".equals(str)) {//匹配是否为联系人名字
                            item.name = data1;
                            item.setPinyin(data1);
                        }
                        if ("phone_v2".equals(str)) {//匹配是否为电话
                            item.mobile = data1;
                        }
                    }
                    if (!TextUtils.isEmpty(item.mobile)) {
                        contactItemList.add(item);
                    }
                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

}
