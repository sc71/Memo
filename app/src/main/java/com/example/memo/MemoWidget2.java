package com.example.memo;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link MemoWidget2ConfigureActivity MemoWidget2ConfigureActivity}
 */
public class MemoWidget2 extends AppWidgetProvider {

    private static DynamicLayout mTextLayout2;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        
        CharSequence widgetText = MemoWidget2ConfigureActivity.loadTitlePref(context, appWidgetId);
        String widgetText2 = MemoWidget2ConfigureActivity.loadTitlePref(context, appWidgetId);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.memo_widget2);
        Bitmap myBitmap = Bitmap.createBitmap(168, 84, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(myBitmap);
        TextPaint mTextPaint=new TextPaint();
        Typeface mytypeface = Typeface.createFromAsset(context.getAssets(),"fonts/indieflower.ttf");

        mTextPaint.setTypeface(mytypeface);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(context.getResources().getColor(R.color.textColor));
        mTextPaint.setTextSize(19);

        mTextLayout2 = new DynamicLayout(widgetText2, mTextPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        int textX = 0;
        int textY = 0;

        canvas.translate(textX, textY);
        mTextLayout2.draw(canvas);
        views.setImageViewBitmap(R.id.appwidget2_text, myBitmap);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            MemoWidget2ConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

