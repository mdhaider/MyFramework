package birdofparadise.myframework.interfaces;

import birdofparadise.myframework.enums.FragmentEnum;
import birdofparadise.myframework.fragmentHelper.FragmentMessageContainer;

/**
 * Created by haider on 09-02-2017.
 */

public interface OnFragmentToDashboardInteractionListener {
    void setToolbarTitle(String s, FragmentEnum comingFrom);

    void setOnDashboardActivityToFragmentCommunication(OnDashboardActivityToFragmentCommunication onDashboardActivityToFragmentCommunication);

    void openDesiredFragment(FragmentMessageContainer fragmentMessageContainer);

    void setToolbarInvisible();

    void backButtonClicked();

    void setToolbarVisible();
}
