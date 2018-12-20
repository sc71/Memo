package com.example.memo;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The configuration screen for the {@link MemoWidget2 MemoWidget2} AppWidget.
 */
public class MemoWidget2ConfigureActivity extends Activity{

    private static final String PREFS_NAME = "com.example.memo.MemoWidget2";
    private static final String PREF_PREFIX_KEY = "appwidget_2";
    int mAppWidgetId2 = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText2;
    Typeface indieFlower;

    ImageView imageViewBackgroundPreview2;

    TextView textViewDisplay2, textViewBackground2, textViewTitle2;
    Button buttonAdd2;
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = MemoWidget2ConfigureActivity.this;

            // When the button is clicked, store the string locally
            String widgetText2 = mAppWidgetText2.getText().toString();
            saveTitlePref(context, mAppWidgetId2, widgetText2);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            MemoWidget2.updateAppWidget(context, appWidgetManager, mAppWidgetId2);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId2);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public MemoWidget2ConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.memo_widget2_configure);
        mAppWidgetText2 = (EditText) findViewById(R.id.appwidget2_text);

        indieFlower = Typeface.createFromAsset(this.getApplicationContext().getAssets(), "fonts/indieflower.ttf");


        buttonAdd2 = findViewById(R.id.add2_button);
        textViewBackground2 = findViewById(R.id.textview_memoconfig2_background);
        textViewDisplay2 = findViewById(R.id.textview_memoconfig2_displaytxt);
        textViewTitle2 = findViewById(R.id.textview_memoconfig2_createnew);
        imageViewBackgroundPreview2 = findViewById(R.id.imageview_memoconfig2_previewbkg);

        buttonAdd2.setOnClickListener(mOnClickListener);
        buttonAdd2.setTypeface(indieFlower);
        textViewDisplay2.setTypeface(indieFlower);
        textViewBackground2.setTypeface(indieFlower);
        mAppWidgetText2.setTypeface(indieFlower);
        textViewTitle2.setTypeface(indieFlower);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId2 = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId2 == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        mAppWidgetText2.setText(loadTitlePref(MemoWidget2ConfigureActivity.this, mAppWidgetId2));
    }
}

