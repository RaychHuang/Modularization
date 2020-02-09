package com.ray.utopia.base.rxjava;

/**
 * This logger intends to log where an Observer is created.
 * Because the Throwable returned by onError doesn't indicate where this Observer it is.
 */
class ObserverMarker {
    private static String PACKAGE_NAME;
    private final String mStackTrace;

    ObserverMarker() {
        if (PACKAGE_NAME == null) {
            try {
                PACKAGE_NAME = ObserverMarker.class.getPackage().getName();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        String packageName = PACKAGE_NAME;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 2; i < elements.length; i++) {
            String className = elements[i].getClassName();
            if (!className.startsWith(packageName)) {
                mStackTrace = elements[i].toString();
                return;
            }
        }
        mStackTrace = "[Can not get StackTrace]";
    }

    String getStackTrace() {
        return mStackTrace;
    }
}
