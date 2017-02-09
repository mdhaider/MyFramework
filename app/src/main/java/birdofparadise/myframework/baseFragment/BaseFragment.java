package birdofparadise.myframework.baseFragment;
/*
Copyright © 2016 Ariba. All rights reserved.
*/

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import birdofparadise.myframework.baseDataContainer.BaseDataContainer;
import birdofparadise.myframework.fragmentHelper.FragmentMessageContainer;
import birdofparadise.myframework.interfaces.OnDashboardActivityToFragmentCommunication;
import birdofparadise.myframework.interfaces.OnFragmentToDashboardInteractionListener;
import birdofparadise.myframework.utilService.GlobalConstant;


public abstract class BaseFragment extends Fragment implements OnDashboardActivityToFragmentCommunication {

    protected OnFragmentToDashboardInteractionListener mListener;
    protected Handler handler;
    protected FragmentMessageContainer fragmentMessageContainer;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.setOnDashboardActivityToFragmentCommunication(this);
        try {
            fragmentMessageContainer = getArguments().getParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey());
        } catch (Exception e) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentToDashboardInteractionListener) {
            mListener = (OnFragmentToDashboardInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        if (context instanceof OnFragmentToDashboardInteractionListener) {
            mListener = (OnFragmentToDashboardInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    @Override
    public BaseDataContainer getComingData() {
        return null;
    }

}
