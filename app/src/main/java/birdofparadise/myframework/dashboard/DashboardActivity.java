package birdofparadise.myframework.dashboard;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.ArrayList;

import birdofparadise.myframework.R;
import birdofparadise.myframework.baseDataContainer.BaseDataContainer;
import birdofparadise.myframework.baseFragment.BaseFragment;
import birdofparadise.myframework.enums.FragmentEnum;
import birdofparadise.myframework.fragmentHelper.FragmentMessageContainer;
import birdofparadise.myframework.fragments.DashboardFragment;
import birdofparadise.myframework.fragments.OneTimePasswordFragment;
import birdofparadise.myframework.interfaces.OnDashboardActivityToFragmentCommunication;
import birdofparadise.myframework.interfaces.OnFragmentToDashboardInteractionListener;
import birdofparadise.myframework.utilService.GlobalConstant;

public class DashboardActivity extends AppCompatActivity implements OnFragmentToDashboardInteractionListener {
    private Toolbar mToolbar;
    private FrameLayout fragmentContainer;
    private AppCompatTextView toolBarTitle;
    private Handler handler;
    private OnDashboardActivityToFragmentCommunication onDashboardActivityToFragmentCommunication;
    private FragmentMessageContainer defaultFragmentMessageContainer;
    ArrayList<FragmentMessageContainer> fragmentMessageContainerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();

        setContentView(R.layout.activity_dashboard);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setSupportActionBar(mToolbar);
        initializeViews();

        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            addDefaultFragmentInPlace();
        } else {
            fragmentMessageContainerList = savedInstanceState.getParcelableArrayList(GlobalConstant.getInstance().getFragmentMessageConatinerKey());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainerList);
    }

    private void initializeViews() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        fragmentContainer = (FrameLayout) findViewById(R.id.content_dashboard_framelayout_fragmentcontainer);
        toolBarTitle = (AppCompatTextView) mToolbar.findViewById(R.id.actionbar_layout_textview);
    }

    private void addDefaultFragmentInPlace() {
        defaultFragmentMessageContainer = new FragmentMessageContainer(
                FragmentEnum.Dashboard,
                FragmentEnum.Dashboard,
                null,
                false,
                null
        );
        Bundle bundle = new Bundle();
        bundle.putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), defaultFragmentMessageContainer);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        DashboardFragment dashboardFragment = new DashboardFragment();
        dashboardFragment.setArguments(bundle);
        fragmentTransaction.add(fragmentContainer.getId(), dashboardFragment, FragmentEnum.Dashboard.toString());
        fragmentTransaction.addToBackStack(FragmentEnum.Dashboard.toString());
        fragmentTransaction.commit();
        fragmentMessageContainerList.add(defaultFragmentMessageContainer);
    }


    @Override
    public void onBackPressed() {

        if (onDashboardActivityToFragmentCommunication.shouldDoNormalOperationOnBackPressed()) {
            int currentFragment = fragmentMessageContainerList.size() - 1;
            if (currentFragment > 0) {
                FragmentMessageContainer fragmentMessageContainer = fragmentMessageContainerList.get(currentFragment - 1);
                BaseDataContainer baseDataContainer = onDashboardActivityToFragmentCommunication.getComingData();
                if (baseDataContainer != null) {
                    fragmentMessageContainer.setComeback(true);
                    fragmentMessageContainer.setComingData(baseDataContainer);
                }
                fragmentMessageContainerList.remove(currentFragment);
                setFragmentBack(fragmentMessageContainer);
            } else {
                finish();
            }
        }
    }


    private void setFragmentBack(FragmentMessageContainer fragmentMessageContainer) {
        switch (fragmentMessageContainer.getGoingTo()) {
            case OneTimePassword:
            case Dashboard:
                setToolbarDefaultSetting();
                break;
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BaseFragment baseFragment = (BaseFragment) getFragmentManager().findFragmentByTag(fragmentMessageContainer.getGoingTo().toString());
        if (baseFragment != null) {
            Bundle bundle = baseFragment.getArguments();
            if (bundle != null) {
                bundle.putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainer);
            } else {
                bundle = new Bundle();
                bundle.putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainer);
                baseFragment.setArguments(bundle);
            }
        }
        fragmentTransaction.replace(fragmentContainer.getId(), baseFragment, fragmentMessageContainer.getGoingTo().toString());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    FragmentEnum comingFrom;

    @Override
    public void setToolbarTitle(final String s, final FragmentEnum comingFromm) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                comingFrom = comingFromm;
                toolBarTitle.setOnClickListener(null);
                toolBarTitle.setClickable(false);
                switch (comingFromm) {
                    case Dashboard:
                        toolBarTitle.setText(s);
                        break;
                    case OneTimePassword:
                        toolBarTitle.setText(s);
                        break;
                }
            }
        });
    }

    @Override
    public void setOnDashboardActivityToFragmentCommunication(OnDashboardActivityToFragmentCommunication onDashboardActivityToFragmentCommunication) {
        this.onDashboardActivityToFragmentCommunication = onDashboardActivityToFragmentCommunication;
    }


    @Override
    public void openDesiredFragment(FragmentMessageContainer fragmentMessageContainer) {
        setToolbarDefaultSetting();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainer);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        BaseFragment baseFragment = (BaseFragment) getFragmentManager().findFragmentByTag(fragmentMessageContainer.getGoingTo().toString());
        if (baseFragment == null) {
            switch (fragmentMessageContainer.getGoingTo()) {
                case Dashboard:
                    break;
                case OneTimePassword:
                    baseFragment = new OneTimePasswordFragment();
                    break;

            }
            if (baseFragment != null) {
                baseFragment.setArguments(bundle);
                fragmentTransaction.replace(fragmentContainer.getId(), baseFragment, fragmentMessageContainer.getGoingTo().toString());
            }
        } else {
            baseFragment.getArguments().putParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey(), fragmentMessageContainer);
            fragmentTransaction.replace(fragmentContainer.getId(), baseFragment, fragmentMessageContainer.getGoingTo().toString());
        }

        fragmentTransaction.addToBackStack(fragmentMessageContainer.getGoingTo().toString());
        maintainFragmentStack(fragmentMessageContainer);
        fragmentTransaction.commit();
    }

    private void setToolbarDefaultSetting() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mToolbar.setVisibility(View.VISIBLE);
            }
        });
    }


    private void maintainFragmentStack(FragmentMessageContainer fragmentMessageContainer) {
        switch (fragmentMessageContainer.getGoingTo()) {
            case Dashboard:
                break;
            case OneTimePassword:
                break;
        }
    }


    @Override
    public void setToolbarInvisible() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mToolbar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void backButtonClicked() {
        handler.post(new Runnable() {
            public void run() {
                onBackPressed();
            }
        });
    }


    @Override
    public void setToolbarVisible() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mToolbar.setVisibility(View.VISIBLE);
            }
        });
    }


}
