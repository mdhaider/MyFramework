package birdofparadise.myframework.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import birdofparadise.myframework.R;
import birdofparadise.myframework.baseFragment.BaseFragment;
import birdofparadise.myframework.enums.FragmentEnum;

/**
 * Created by haider on 09-02-2017.
 */

public class OneTimePasswordFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_otp, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListener.setToolbarTitle(getString(R.string.one_time_password), FragmentEnum.OneTimePassword);


    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public boolean shouldDoNormalOperationOnBackPressed() {
        return false;
    }
}