package qukakac.net.qqq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

import androidx.core.content.FileProvider;

public class UriParseUtils {


    /**
     * 创建文件输出路径的Uri
     * @param context
     * @param file
     * @return   转换后的scheme 为fileProvider的Uri
     */
    private static Uri getUriForFile(Context context, File file){

        return FileProvider.getUriForFile(context,getFileProvider(context),file);
    }

    /**
     * 获取fileProvider 路径，适配6.0+
     * @param context   上下文
     * @return    fileprovider 路径
     */
    private static String getFileProvider(Context context) {
        return context.getApplicationInfo().packageName+".fileprovider";
    }

    /**
     * 安装apk
     */

    public static void install(Activity activity,File apkFile){
        if (!apkFile.exists()){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            Uri fileUri =getUriForFile(activity,apkFile);
            intent.setDataAndType(fileUri,"application/vnd.android.package-archive");
        }else {
            intent.setDataAndType(Uri.fromFile(apkFile),"application/vnd.android.package-archive");
        }

        activity.startActivity(intent);
    }

}
