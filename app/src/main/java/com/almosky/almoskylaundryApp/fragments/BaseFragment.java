package com.almosky.almoskylaundryApp.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;


public class BaseFragment extends Fragment {
//    public BaseActivity baseActivity;

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        baseActivity = (BaseActivity) getActivity();
    }
}
