package com.example.pl.testhello.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.example.basehello.utils.FileUtil;
import com.example.basehello.utils.SystemUtil;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * UncaughtException处理类，当程序发生Uncaught异常的时候，由该类来接管程序，并记录发送错误报告。
 * 需要在Application中注册，为了要在程序启动器就监控整个程序。
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private final String TAG = "CrashHandler";

    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    @SuppressLint("StaticFieldLeak")
    private static CrashHandler instance;
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    /**
     * 私有化构造函数，保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例，单例模式
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            ActivityCollector.finishAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理，收集错误信息、发送错误报告等操作均在此完成。
     *
     * @return 如果处理了该异常信息返回true；否则返回false。
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 把crash发送到服务器
        sendCrashToServer(ex);

        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出。",
                        Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        // 保存日志文件
        saveCrashInfoToFile(ex);

        return true;
    }

    /**
     * 保存错误信息到文件中
     *
     * @return 返回文件名称，便于将文件传送到服务器
     */
    private String saveCrashInfoToFile(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA).format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                FileOutputStream fos = new FileOutputStream(FileUtil.getCrashFilePath(fileName));
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 把crash发送到服务器
     */
    public void sendCrashToServer(Throwable ex) {
        String versionName = SystemUtil.getVersionName(mContext);
        versionName = versionName == null ? "null" : versionName;
        String versionCode = SystemUtil.getVersionCode(mContext) + "";
        infos.put("versionName", versionName);
        infos.put("versionCode", versionCode);

//        HashMap<String, String> exceptionInfo = new HashMap<String, String>();
//
//        try {
//            ActivityManager am = (ActivityManager) mContext
//                    .getSystemService(Context.ACTIVITY_SERVICE);
//            String pageName = mContext.getClass().getName();
//            ActivityManager.MemoryInfo mi = new MemoryInfo();
//            am.getMemoryInfo(mi);
//            String memoryInfo = "Memory info:" + mi.availMem + ",app holds:"
//                    + mi.threshold + ",Low Memory:" + mi.lowMemory;
//
//            ApplicationInfo appInfo = mContext.getPackageManager().getApplicationInfo(
//                    mContext.getPackageName(), PackageManager.GET_META_DATA);
//            String channelId = appInfo.metaData.getString("UMENG_CHANNEL");
//
//            String version = mContext.getPackageManager().getPackageInfo(
//                    mContext.getPackageName(), 0).versionName;
//
//            exceptionInfo.put("PageName", pageName);
//            exceptionInfo.put("ExceptionName", ex.getClass().getName());
//            exceptionInfo.put("ExceptionType", "1");
//            exceptionInfo.put("ExceptionsStackDetail", getStackTrace(ex));
//            exceptionInfo.put("AppVersion", version);
//            exceptionInfo.put("OSVersion", android.os.Build.VERSION.RELEASE);
//            exceptionInfo.put("DeviceModel", android.os.Build.MODEL);
//            exceptionInfo.put("DeviceId", SystemUtil.getDeviceID(mContext));
//            exceptionInfo.put("NetWorkType", String.valueOf(SystemUtil.isWifi(mContext) ? 1 : 0));
//            exceptionInfo.put("ChannelId", channelId);
//            exceptionInfo.put("ClientType", "100");
//            exceptionInfo.put("MemoryInfo", memoryInfo);
//
//            new Thread() {
//                @Override
//                public void run() {
//
//                }
//            }.start();
//        } catch (Exception e) {
//            LogUtil.e(TAG, e.getMessage());
//        }
    }

//    private String getStackTrace(Throwable th) {
//        final Writer result = new StringWriter();
//        final PrintWriter printWriter = new PrintWriter(result);
//        Throwable cause = th;
//        while (cause != null) {
//            cause.printStackTrace(printWriter);
//            cause = cause.getCause();
//        }
//        final String stacktraceAsString = result.toString();
//        printWriter.close();
//        return stacktraceAsString;
//    }

}