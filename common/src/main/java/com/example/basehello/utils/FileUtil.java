package com.example.basehello.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;

/**
 * 文件工具类
 */
public class FileUtil {
    public static final String TAG = "FileUtil";

    /**
     * 获取sd卡根目录
     *
     * @return sd卡根目录
     */
    public static File getSDCardDir() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取应用本地储存根目录
     *
     * @return 应用本地储存根目录
     */
    public static File getAppRootDir() {
        File file = new File(getSDCardDir(), "root");
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        return file;
    }


    /**
     * 获取安装文件下载目录
     *
     * @return 安装文件下载目录
     */
    public static File getApkDir() {
        File file = new File(getAppRootDir(), "Apk");
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取安装文件
     *
     * @return 安装文件
     */
    public static File getApkFile(String fileName) {
        return new File(getApkDir(), fileName);
    }


    /**
     * 获取应用奔溃日志储存目录
     *
     * @return 应用奔溃日志储存目录
     */
    public static File getCrashLogsDir() {
        File file = new File(getAppRootDir(), "CrashLogs");
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取应用奔溃日志文件
     *
     * @return 应用奔溃日志文件
     */
    public static File getCrashFilePath(String crashFileName) {
        return new File(getCrashLogsDir(), crashFileName);
    }


    /**
     * 删除文件夹及其下所有文件
     *
     * @param file 文件夹
     */
    public static void delete(File file) {
        if (file.isFile()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
                return;
            }
            for (File childFile : childFiles) {
                delete(childFile);
            }
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }


    /**
     * 获取文件夹大小
     *
     * @param file 文件夹
     * @return 文件夹大小
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
        }
        return size;
    }

    /**
     * 文件尺寸转换成带单位的值
     *
     * @param xSize 大小
     * @return 大小（带单位）
     */
    public static String calculateSize(float xSize) {
        xSize = (new BigDecimal(xSize)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        if (xSize < 1000) {
            return xSize + " B";
        }
        xSize = xSize / 1000;
        xSize = (new BigDecimal(xSize)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        if (xSize < 1000) {
            return xSize + " KB";
        }
        xSize = xSize / 1000;
        xSize = (new BigDecimal(xSize)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        if (xSize < 1000) {
            return xSize + " MB";
        }
        xSize = xSize / 1000;
        xSize = (new BigDecimal(xSize)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        if (xSize < 1000) {
            return xSize + " GB";
        }
        return "";
    }

    /**
     * 获取文件夹大小（带单位）
     *
     * @param file 文件夹
     * @return 文件夹大小（带单位）
     */
    public static String getFolderSizeString(File file) {
        return calculateSize(getFolderSize(file));
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static float getFileSize(File file) throws Exception {
        float size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        } else {
            file.createNewFile();
        }
        return size;
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 检查文件是否存在
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    /**
     * 获取指定文件MD5值
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
