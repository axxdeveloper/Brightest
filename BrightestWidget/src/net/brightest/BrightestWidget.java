package net.brightest;

import net.brightest.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class BrightestWidget extends AppWidgetProvider {

	
    @Override
    public void onUpdate(Context context,AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    	super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews updateViews = new RemoteViews( context.getPackageName(), R.layout.main);
        Intent intent = new Intent(context, EmptyBrightnessActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.now, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, updateViews);
    }
    
}

