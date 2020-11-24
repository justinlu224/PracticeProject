package com.example.practiceproject.appwdigets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import com.example.practiceproject.R
import com.example.practiceproject.todoasync.CoroutinesAsyncActivity


class Index(var index: Int)

class ExampleAppWidgetProvider : AppWidgetProvider() {
    // 每次會重新產生一個新的物件
    var index = Index(1)
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.i("ExampleAppWidgetProvider", "onReceive: $index , ${index.index}")
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.i("ExampleAppWidgetProvider", "onUpdate: $index , ${index.index}")
        appWidgetIds?.forEach { appWidgetId ->
            val pendingIntent = Intent(context, CoroutinesAsyncActivity::class.java)
                .let { intent ->
                    PendingIntent.getActivity(context, 0, intent, 0)
                }

            val remoteViews = RemoteViews(context?.packageName, R.layout.example_appwidget)
                .apply {
                    setOnClickPendingIntent(R.id.ll_appwidget, pendingIntent)
                    setTextViewText(R.id.text, String.format("init%s", index.index))
                }
            index.index = index.index + 1
            appWidgetManager?.updateAppWidget(appWidgetId, remoteViews)
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.i("ExampleAppWidgetProvider", "onEnabled: $index , ${index.index}")
//        val intent = Intent()
//        val pendingIntent = PendingIntent.getService(context, 0, intent, 0)
//        val alarm = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarm.setRepeating(
//            AlarmManager.ELAPSED_REALTIME,
//            SystemClock.elapsedRealtime(), 1000, pendingIntent
//        )
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
    }

    override fun onRestored(context: Context?, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
    }
}