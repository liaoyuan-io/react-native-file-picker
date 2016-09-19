package java.io.liaoyuan.reactnative.fileaccessframework;

import android.net.Uri;
import com.facebook.react.bridge.Promise;

import android.app.Activity;
import android.content.Intent;

public class FilePicker extends Activity{
    private static final int PICK_FILE_REQUEST_CODE = 42;
    private static final String E_PICK_FILE = "E_PICK_FILE";
    private Promise promise;

    FilePicker(final Promise promise) {
        this.promise = promise;
    }

    void launch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                Uri fileUri = resultData.getData();
                promise.resolve(fileUri.toString());
            } catch (Exception e) {
                promise.reject(E_PICK_FILE, e.getMessage());
            }
        }
    }
}
