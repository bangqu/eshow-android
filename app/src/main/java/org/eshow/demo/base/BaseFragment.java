package org.eshow.demo.base;

import android.content.Context;
import android.view.View;

import com.bangqu.lib.base.EshowFragment;

import org.eshow.demo.util.SharedPref;

import butterknife.Unbinder;


public abstract class BaseFragment extends EshowFragment {

    protected SharedPref sharedPref;
    protected Unbinder unbinder;
    protected View rootView;

    @Override
    public void onAttach(Context activity) {
        sharedPref = new SharedPref(getActivity());
        super.onAttach(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(null!=unbinder){
            unbinder.unbind();
        }
    }

}