package net.osdn.gokigen.watchface.prpr1

import android.util.Log
import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository

class MyWatchFace : WatchFaceService()
{
    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace
    {
        if (Log.isLoggable(TAG, Log.DEBUG))
        {
            Log.d(TAG, "createWatchFace()")
        }

        val renderer = MyWatchFaceRenderer(
            context = applicationContext,
            surfaceHolder = surfaceHolder,
            watchState = watchState,
            currentUserStyleRepository = currentUserStyleRepository,
            canvasType = CanvasType.HARDWARE
        )
        return (WatchFace(watchFaceType = WatchFaceType.DIGITAL, renderer = renderer))
    }

    companion object {
        const val TAG = "MyWatchFace"
    }
}
