package com.icaopan.clearing.service;

/**
 * Created by Administrator on 2017/3/12.
 */

public interface ClearingService {

    void clearingProcess(boolean deleteEmptyPosition);

    void clearingProcessTask();

    void cancelPlacement();

    void checkCashAndPosition();

    void  deleteEmptySecurityPosition();
}
