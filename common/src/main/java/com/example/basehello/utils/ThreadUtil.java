package com.example.basehello.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具类
 */
public class ThreadUtil {
    private static final String TAG = "ThreadUtil";
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static ExecutorService mExecutorService = null;

    public static void runOnMainThread(Runnable task) {
        mHandler.post(task);
    }

    public static void runOnMainThread(Runnable task, long delay) {
        mHandler.postDelayed(task, delay);
    }

    public static void runOnWorkThread(Runnable task) {
        getExecutorService().execute(task);
    }

    private static ExecutorService getExecutorService() {
        if (mExecutorService == null) {
            synchronized (ThreadUtil.class) {
                if (mExecutorService == null) {
                    int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
                    int KEEP_ALIVE_TIME = 1;
                    TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
                    BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
                    mExecutorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2,
                            KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, taskQueue,
                            Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        return mExecutorService;
    }
}
