package com.example.memo;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link MemoWidgetConfigureActivity MemoWidgetConfigureActivity}
 */
public class MemoWidget extends AppWidgetProvider {

    private static StaticLayout mTextLayout;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = MemoWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        String widgetText2 = MemoWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.memo_widget);

        Bitmap myBitmap = Bitmap.createBitmap(168, 84, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(myBitmap);
        TextPaint mTextPaint=new TextPaint();
        Typeface mytypeface = Typeface.createFromAsset(context.getAssets(),"fonts/caveat_regular.ttf");

        mTextPaint.setTypeface(mytypeface);
        mTextPaint.setColor(context.getResources().getColor(R.color.textColor));
        mTextPaint.setTextSize(22);
        if(widgetText2.length() <= 49) {
            mTextLayout = new StaticLayout(widgetText2, mTextPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        }

        else {
            mTextLayout = new StaticLayout(widgetText2.substring(0,50) + "...", mTextPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        }
        int textX = 0;
        int textY = 0;

        canvas.translate(textX, textY);
        mTextLayout.draw(canvas);

        views.setImageViewBitmap(R.id.appwidget_text, myBitmap);

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
            MemoWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
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

    public static Bitmap buildUpdate(String text, Context context)
    {
        Bitmap myBitmap = Bitmap.createBitmap(168, 84, Bitmap.Config.ARGB_8888);
        Canvas myCanvas = new Canvas(myBitmap);
        Paint paint = new Paint();
        Typeface mytypeface = Typeface.createFromAsset(context.getAssets(),"fonts/caveat_regular.ttf");
        paint.setAntiAlias(true);
        paint.setTypeface(mytypeface);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(context.getResources().getColor(R.color.textColor));
        paint.setTextSize(24);
        paint.setTextAlign(Paint.Align.LEFT);
        myCanvas.drawText(text, 0, 20, paint);
        return myBitmap;
    }

    public static Bitmap buildUpdate2(String text, String text2, Context context)
    {
        Bitmap myBitmap = Bitmap.createBitmap(168, 84, Bitmap.Config.ARGB_8888);
        Canvas myCanvas = new Canvas(myBitmap);
        Paint paint = new Paint();
        Typeface mytypeface = Typeface.createFromAsset(context.getAssets(),"fonts/caveat_regular.ttf");
        paint.setAntiAlias(true);
        paint.setTypeface(mytypeface);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(context.getResources().getColor(R.color.textColor));
        paint.setTextSize(24);
        paint.setTextAlign(Paint.Align.LEFT);
        myCanvas.drawText(text, myBitmap.getHeight(), 20, paint);
        return myBitmap;
    }

    public static Bitmap buildUpdate3(String text, Context context)
    {
        Bitmap myBitmap = Bitmap.createBitmap(168, 84, Bitmap.Config.ARGB_8888);
        Canvas myCanvas = new Canvas(myBitmap);
        Paint paint = new Paint();
        Typeface mytypeface = Typeface.createFromAsset(context.getAssets(),"fonts/caveat_regular.ttf");
        paint.setAntiAlias(true);
        paint.setTypeface(mytypeface);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(context.getResources().getColor(R.color.textColor));
        paint.setTextSize(24);
        paint.setTextAlign(Paint.Align.LEFT);
        myCanvas.drawText(text, 2*myBitmap.getHeight(), 20, paint);
        return myBitmap;
    }
}

