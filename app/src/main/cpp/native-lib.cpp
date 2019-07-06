#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_qukakac_net_qqq_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"{
    extern int main(int argc,char * argv[]);
}
extern "C"

JNIEXPORT void JNICALL
Java_qukakac_net_qqq_MainActivity_pathApk(JNIEnv *env, jobject instance, jstring oldApk_,
                                          jstring pathFile_, jstring outPut_) {
    const char *oldApk = env->GetStringUTFChars(oldApk_, 0);
    const char *pathFile = env->GetStringUTFChars(pathFile_, 0);
    const char *outPut = env->GetStringUTFChars(outPut_, 0);

    const char *argv[] ={"", oldApk,outPut,pathFile};
//    char *argv[] ={"", const_cast<char *>(oldApk), const_cast<char *>(outPut),const_cast<char *>(pathFile)};
    main(4, const_cast<char **>(argv));

    env->ReleaseStringUTFChars(oldApk_, oldApk);
    env->ReleaseStringUTFChars(pathFile_, pathFile);
    env->ReleaseStringUTFChars(outPut_, outPut);
}