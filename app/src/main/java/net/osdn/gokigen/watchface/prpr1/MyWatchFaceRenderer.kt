package net.osdn.gokigen.watchface.prpr1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.SurfaceHolder
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.Renderer.CanvasRenderer2
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyle
import kotlinx.coroutines.*
import java.time.ZonedDateTime

private const val FRAME_PERIOD_MS_DEFAULT: Long = 16L * 60L * 12L  // 12Sec.

class MyWatchFaceRenderer(
    context: Context,
    surfaceHolder: SurfaceHolder,
    currentUserStyleRepository: CurrentUserStyleRepository,
    watchState: WatchState,
    canvasType: Int,
) : CanvasRenderer2<Renderer.SharedAssets>(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    canvasType,
    FRAME_PERIOD_MS_DEFAULT,
    true /* clearWithBackgroundTintBeforeRenderingHighlightLayer */
), Renderer.SharedAssets
{
    private val scope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val imageHolder = MyWatchFaceHolder(context)
    private val myDrawer = MyWatchFaceDrawer(imageHolder)

    init
    {
        scope.launch {
            currentUserStyleRepository.userStyle.collect { userStyle ->
                updateWatchFaceData(userStyle)
            }
        }
    }

    private fun updateWatchFaceData(userStyle: UserStyle)
    {
        if (Log.isLoggable(TAG, Log.VERBOSE))
        {
            Log.v(TAG, " updateWatchFace(): $userStyle")
        }
    }

    override suspend fun createSharedAssets(): SharedAssets
    {
        if (Log.isLoggable(TAG, Log.VERBOSE))
        {
            Log.v(TAG, " createSharedAssets()")
        }
        return (this)
    }

    override fun onDestroy()
    {
        if (Log.isLoggable(TAG, Log.VERBOSE))
        {
            Log.v(TAG, " onDestroy()")
        }
        imageHolder.dispose()
        scope.cancel("MyWatchFaceRenderer scope clear() request")
        super.onDestroy()
    }

    override fun render(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: SharedAssets
    )
    {
        try
        {
            myDrawer.doDraw(canvas, bounds, hour = zonedDateTime.hour, minute = zonedDateTime.minute, second = zonedDateTime.second)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }
    }

    override fun renderHighlightLayer(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: SharedAssets
    )
    {
        try
        {
            myDrawer.doDraw(canvas, bounds, hour = zonedDateTime.hour, minute = zonedDateTime.minute, second = zonedDateTime.second)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }
    }

    companion object
    {
        private const val TAG = "MyWatchFaceRenderer"
    }
}
