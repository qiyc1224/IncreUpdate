package qukakac.net.qqq;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(permission[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permission, 110);
            }
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"开始增量更新",Toast.LENGTH_LONG).show();
                new AsyncTask<Void, Void, File>() {
                    //下载补丁包

                    @Override
                    protected File doInBackground(Void... voids) {
                        //此处省略从服务器端检查更新  是否需要补丁包的网络请求。

                        //下载完成补丁包开始进行合成操作。
                        String oldApk = getApplicationInfo().sourceDir;
                        String patch = new File(Environment.getExternalStorageDirectory(), "qyc_patch").getAbsolutePath();//补丁包的名字 需要和服务器端名字保持一致。
                        String output = createNewApk();
                        pathApk(oldApk, patch, output);
                        return new File(output);
                    }

                    @Override
                    protected void onPostExecute(File file) {
                        Toast.makeText(MainActivity.this,"开始安装",Toast.LENGTH_LONG).show();
                        UriParseUtils.install(MainActivity.this, file);
                    }
                }.execute();
            }
        });
    }

    private String createNewApk() {
        File newApk = new File(Environment.getExternalStorageDirectory(), "qyc.apk");
        try {
            if (!newApk.exists()) {
                newApk.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newApk.getAbsolutePath();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }


    public native void pathApk(String oldApk, String pathFile, String outPut);

}
