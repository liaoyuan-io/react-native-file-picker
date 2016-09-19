package io.liaoyuan.reactnative.filepicker;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

class FilePicker extends ReactContextBaseJavaModule {

    FilePicker(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "FilePicker";
    }

    @ReactMethod
    public void pickDocument(final Promise promise) {
        FileNavigator navigator = new FileNavigator(promise);
        navigator.launch();
    }

}
