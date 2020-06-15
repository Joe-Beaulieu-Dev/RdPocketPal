package com.example.rdpocketpal2.util;

import com.example.rdpocketpal2.model.QueryResult;

public interface CoroutineCallbackListener {
    /**
     * Called when the coroutine holding this listener is finished.
     *
     * @param result result of the coroutine
     * @param <T> data stored in the result
     */
    <T> void onCoroutineFinished(QueryResult<T> result);
}
