package com.crane.mockapp.core;

/**
 * Created by azhuravlev on 2/14/2017.
 */

public interface OperationCallback<T> {
    void onSuccess(T result);

    void onFail(String message);
}
