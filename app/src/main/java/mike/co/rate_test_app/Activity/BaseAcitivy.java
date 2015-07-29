package mike.co.rate_test_app.Activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;
import mike.co.rate_test_app.BuildConfig;

/**
 * Created by miguelalegria on 7/28/15.
 */
public class BaseAcitivy extends AppCompatActivity {

    public void Inject() {
        ButterKnife.inject(this);
    }

    public void log(String TAG, String log) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, log);
        }
    }
}
