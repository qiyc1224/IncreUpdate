# IncreUpdate-Android 文档说明
Android使用bsdiff 的增量更新工具.Android  java

本项目使用bsdiff 进行老apk 文件和 补丁包patch  的合成操作。
使用项目需要在studio 内安装 NDK    CMake  LLDB 

cpp 目录下文件 bspatch.c 为合成操作的 c文件。bzip 目录下为bspatch 的依赖包。

需要在CMakeLists.txt 文件中把bspatch.c 文件  以及bzip 文件添加到依赖库。


没有进行从服务端检查更新的网络请求，使用的时候需要自己完善检查更新的操作。


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
                
                UriParseUtils   为安装的工具类。需要注意适配7.0 以上FileProvider以及未知来源应用安装权限。
