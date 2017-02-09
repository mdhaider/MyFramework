package birdofparadise.myframework.interfaces;

import birdofparadise.myframework.baseDataContainer.BaseDataContainer;

/**
 * Created by haider on 09-02-2017.
 */

public interface OnDashboardActivityToFragmentCommunication {

    BaseDataContainer getComingData();
    boolean shouldDoNormalOperationOnBackPressed();
}
