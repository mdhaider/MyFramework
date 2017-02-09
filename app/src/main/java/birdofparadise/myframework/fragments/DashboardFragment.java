package birdofparadise.myframework.fragments;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import birdofparadise.myframework.R;
import birdofparadise.myframework.baseFragment.BaseFragment;
import birdofparadise.myframework.enums.FragmentEnum;
import birdofparadise.myframework.fragmentHelper.FragmentMessageContainer;

public class DashboardFragment extends BaseFragment {
    private AppCompatButton btn1;

    private View.OnClickListener sendToFrag1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mListener.openDesiredFragment(new FragmentMessageContainer(
                    FragmentEnum.OneTimePassword,
                    FragmentEnum.Dashboard,
                    null,
                    true,
                    null));
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListener.setToolbarTitle(getString(R.string.dashboard_fragment), FragmentEnum.Dashboard);

        btn1=(AppCompatButton) view.findViewById(R.id.fragment_dashboard_gotofrag1);

        btn1.setOnClickListener(sendToFrag1);

    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public boolean shouldDoNormalOperationOnBackPressed() {
        return false;
    }

}
