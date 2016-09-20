package io.liaoyuan.reactnative.filepicker;

import android.net.Uri;
import com.facebook.react.bridge.*;

import android.app.Activity;
import android.content.Intent;

class FilePicker extends ReactContextBaseJavaModule implements ActivityEventListener {

    private static final int FILE_PICKER_REQUEST_CODE = 82935;
    private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
    private static final String E_PICKER_CANCELLED = "E_PICKER_CANCELLED";
    private static final String E_FAILED_TO_SHOW_PICKER = "E_FAILED_TO_SHOW_PICKER";
    private static final String E_NO_FILE_DATA_FOUND = "E_NO_FILE_DATA_FOUND";

    private Promise mFilePickerPromise;

    FilePicker(ReactApplicationContext reactContext) {
        super(reactContext);

        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "FilePicker";
    }

    @ReactMethod
    public void pickDocument(final Promise promise) {
        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
            return;
        }

        mFilePickerPromise = promise;

        try {
            final Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            currentActivity.startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);

        } catch (Exception e) {
            mFilePickerPromise.reject(E_FAILED_TO_SHOW_PICKER, e);
            mFilePickerPromise = null;
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (requestCode == FILE_PICKER_REQUEST_CODE) {
            if (mFilePickerPromise != null) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    mFilePickerPromise.reject(E_PICKER_CANCELLED, "File picker was cancelled");
                    return;
                }
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = intent.getData();

                    if (uri == null) {
                        mFilePickerPromise.reject(E_NO_FILE_DATA_FOUND, "No file data found");
                        return;
                    }

                    mFilePickerPromise.resolve(uri.toString());
                }
                mFilePickerPromise = null;
            }
        }
    }

}
