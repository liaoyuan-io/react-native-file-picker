package io.liaoyuan.reactnative.fileaccessframework;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

class FileAccessFramework extends ReactContextBaseJavaModule {

    FileAccessFramework(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "FileAccessFramework";
    }

    @ReactMethod
    public void pickDocument(final Promise promise) {
        FilePicker picker = new FilePicker(promise);
        picker.launch();
    }

}
