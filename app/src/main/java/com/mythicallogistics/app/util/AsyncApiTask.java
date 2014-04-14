package com.mythicallogistics.app.util;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by ole on 8/25/13.
 */
public abstract class AsyncApiTask extends AsyncTask<Void, Void, Void> {

    private Exception taskException;
    private Activity activity;

    public AsyncApiTask(Activity activity) {
        this.activity = activity;
    }

    protected Void doInBackground(Void... params) {
        try {
            doTask();

        } catch (Exception e) {

            taskException = e;
        }

        return null;
    }

    abstract public void doTask() throws Exception;

    public void afterTask() {
    }

    @Override
    protected void onPostExecute(Void v) {

        onPostExecuteOrCancelled();

        if (activity.isFinishing())
            return;

        if (taskException != null) {
            afterException(taskException);
        } else {
            afterTask();
            afterPostExecute();
        }
    }

    public void afterPostExecute() {
    }

    public void afterException(Exception e) {
//        Utils.showError(activity, Utils.getApiExceptionMessage(e), e, new Runnable() {
//            @Override
//            public void run() {
//                afterPostExecute();
//            }
//        });
    }

    public void onPostExecuteOrCancelled() {

    }

    @Override
    protected void onCancelled() {
        onPostExecuteOrCancelled();
    }

    public Exception getTaskException() {
        return taskException;
    }

    public Activity getActivity() {
        return activity;
    }
}
