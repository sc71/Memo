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
import android.widget.TextView;

/**
 * The configuration screen for the {@link MemoWidget MemoWidget} AppWidget.
 */
public class MemoWidgetConfigureActivity extends Activity implements View.OnClickListener{

    private static final String PREFS_NAME = "com.example.memo.MemoWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText;
    Typeface caveatRegular;

    TextView textViewDisplay, textViewBackground, textViewTitle;
    Button buttonAdd;

    public MemoWidgetConfigureActivity() {
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
            return "";
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

        setContentView(R.layout.memo_widget_configure);

        caveatRegular = Typeface.createFromAsset(this.getApplicationContext().getAssets(), "fonts/caveat-bold.ttf");

        findViewById(R.id.add_button).setOnClickListener(this);

        buttonAdd = findViewById(R.id.add_button);
        textViewBackground = findViewById(R.id.textview_memoconfig_background);
        textViewDisplay = findViewById(R.id.textview_memoconfig_displaytxt);
        mAppWidgetText = findViewById(R.id.appwidget_text);
        textViewTitle = findViewById(R.id.textview_memoconfig_createnew);

        buttonAdd.setTypeface(caveatRegular);
        textViewDisplay.setTypeface(caveatRegular);
        textViewBackground.setTypeface(caveatRegular);
        mAppWidgetText.setTypeface(caveatRegular);
        textViewTitle.setTypeface(caveatRegular);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        mAppWidgetText.setText(loadTitlePref(MemoWidgetConfigureActivity.this, mAppWidgetId));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_button:
                final Context context = MemoWidgetConfigureActivity.this;

                // When the button is clicked, store the string locally
                String widgetText = mAppWidgetText.getText().toString();
                saveTitlePref(context, mAppWidgetId, widgetText);

                // It is the responsibility of the configuration activity to update the app widget
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                MemoWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

                // Make sure we pass back the original appWidgetId
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
        }

    }
}

