package com.bangqu.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bangqu.lib.R;

/**
 * Created by Administrator on 2016/7/4.
 * 底部弹出菜单选择框
 */
public class MenuDialog extends Dialog {

    private Context context;
    private TextView cancel;
    private UnScrollListView menuList;
    private String[] menuItems;

    public MenuDialog(Context context) {
        super(context, R.style.menu_dialog_style);
        this.context = context;
        setContentView(R.layout.layout_menu_dialog);
        getWindow().setGravity(Gravity.BOTTOM);
        cancel = findViewById(R.id.dialog_menu_cancel);
        menuList = findViewById(R.id.dialog_menu_list);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public MenuDialog setMenuItems(final String[] items, final OnMenuItemClickedListener listener) {
        this.menuItems = items;
        menuList.setAdapter(new ArrayAdapter(context, R.layout.layout_menu_dialog_item, items));
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.MenuItemClicked(menuItems[position], position);
                dismiss();
            }
        });
        return this;
    }

    public interface OnMenuItemClickedListener {
        void MenuItemClicked(String value, int position);
    }

}
