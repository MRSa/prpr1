package net.osdn.gokigen.watchface.prpr1

import android.graphics.*
import java.util.*

class MyWatchFaceDrawer(private val mHolder: MyWatchFaceHolder, private val isShowSeconds: Boolean = false)
{

    /**
     * 時計の描画
     * @param canvas      描画するキャンバス
     * @param bounds      描画領域の大きさ
     * @param hour        描画する時
     * @param minute     描画する分
     * @param second     描画する秒
     */
    fun doDraw(canvas: Canvas, bounds: Rect, hour: Int, minute: Int, second: Int)
    {
        val width = bounds.width()
        val height = bounds.height()

        // Log.v(TAG, "doDraw() ${hour}:${minute}:${second}")  // 描画のタイミングをみるためのログ

        // 背景画像の描画
        canvas.drawBitmap(mHolder.getBackgroundScaledBitmap(width, height), 0f, 0f, null)

        val timeString: String = if (isShowSeconds)
        {
            // 現在時刻を hh:mm.ss 形式にする
            formatTwoDigitNumber(hour) + MyWatchFaceHolder.TIME_SEPARATOR + formatTwoDigitNumber(minute) + MyWatchFaceHolder.SECOND_SEPARATOR + formatTwoDigitNumber(second)
        } else {
            // 現在時刻を hh:mm 形式にする
            formatTwoDigitNumber(hour) + MyWatchFaceHolder.TIME_SEPARATOR + formatTwoDigitNumber(minute)
        }

        // 時刻の表示位置を決める
        val textPaint: Paint = mHolder.getTimePaint()
        val textWidth = textPaint.measureText(timeString)
        val fontMetrics = textPaint.fontMetrics
        val fontSize = Math.abs(fontMetrics.descent + fontMetrics.ascent) / 2.0f
        val baseY: Float = height - fontSize - mHolder.getYOffset()
        val baseX = (width - textWidth) / 2.0f

        // 背景の描画(半透明にする)
        canvas.drawRoundRect(
            baseX - 5.0f,
            baseY + fontMetrics.ascent,
            baseX + textWidth + 5.0f,
            baseY + fontMetrics.descent,
            10.0f,
            10.0f,
            mHolder.getBackPaint()
        )

        // 時刻の描画
        canvas.drawText(timeString, baseX, baseY, textPaint)
    }

    /**
     * 数字を二桁の数値に揃える
     *
     * @param data 数値
     * @return  string data
     */
    private fun formatTwoDigitNumber(data: Int): String
    {
        return (String.format(Locale.ENGLISH, "%02d", data))
    }

    companion object
    {
        private const val TAG = "MyWatchFaceDrawer"
    }
}
