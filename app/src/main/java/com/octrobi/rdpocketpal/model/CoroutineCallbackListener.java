package com.octrobi.rdpocketpal.model;

public interface CoroutineCallbackListener {
    /**
     * Called when the coroutine holding this listener is finished.
     *
     * @param result result of the coroutine
     * @param <T> data stored in the result
     */
    <T> void onCoroutineFinished(QueryResult<T> result);
}
