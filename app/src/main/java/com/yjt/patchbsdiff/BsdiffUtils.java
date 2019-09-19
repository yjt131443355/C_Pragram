package com.yjt.patchbsdiff;

public class BsdiffUtils {
    private BsdiffUtils() {
        //do nothing
    }

    public static native void startPatch(String oldfile, String newfile, String patchfile);

    public static native void startCombine(String oldfile, String newfile, String patchfile);
    static{
        System.loadLibrary("patchBsdiff");
    }
}
