package net.osdn.gokigen.watchface.prpr1

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ExtStorageFileUtil(offsetDir: String)
{
    private var baseDirectory = "/"

    init
    {
        prepareBaseDirectory(offsetDir)
    }

    /**
     * 記録のベースディレクトリを作成し、記録の準備
     *
     */
    private fun prepareBaseDirectory(offsetDir: String)
    {
        var gokigenDirectory = Environment.getExternalStorageDirectory().path + "/Gokigen"
        try
        {
            var baseDir = File(gokigenDirectory)
            if (!baseDir.exists())
            {
                if (!baseDir.mkdirs())
                {
                    baseDirectory = Environment.getExternalStorageDirectory().path
                    return
                }
            }
            gokigenDirectory += offsetDir
            baseDir = File(gokigenDirectory)
            if (!baseDir.exists())
            {
                if (!baseDir.mkdirs())
                {
                    baseDirectory = Environment.getExternalStorageDirectory().path + "/Gokigen"
                    return
                }
            }
            baseDirectory = gokigenDirectory
            return
        }
        catch (ex: Exception)
        {
            if (Log.isLoggable(TAG, Log.VERBOSE))
            {
                Log.v(TAG, "prepareBaseDirectory() : " + ex.message)
            }
        }
        baseDirectory = Environment.getExternalStorageDirectory().path
    }

    /**
     * ベースディレクトリを応答する
     *
     * @return  ファイルのあるベースディレクトリ
     */
    fun getGokigenDirectory(): String
    {
        return baseDirectory
    }

    /**
     * ディレクトリを作成する
     *
     * @param dirName ディレクトリ名
     */
    fun makeDirectory(dirName: String)
    {
        val makeDir = "$baseDirectory/$dirName"
        try
        {
            val dir = File(makeDir)
            if (!dir.exists())
            {
                val ret = dir.mkdirs()
                if (!ret)
                {
                    if (Log.isLoggable(TAG, Log.VERBOSE))
                    {
                        Log.v(TAG, "makeDirectory() : false ")
                    }
                }
            }
        }
        catch (ex: Exception)
        {
            if (Log.isLoggable(TAG, Log.VERBOSE))
            {
                Log.v(TAG, "makeDirectory() : " + ex.message)
            }
        }
    }

    /**
     * BITMAPをPNGイメージにして保存する
     *
     * @param targetImage      PNGで保存するBitmap
     * @return  保存したビットマップのファイル名(nullの場合には保存失敗)
     */
    fun putPngImageFromBitmap(appendDirectory: String?, targetImage: Bitmap): String? {
        val fileName: String
        try {
            val dateTime = Calendar.getInstance().time.time
            fileName = "$dateTime.png"
            var imagePath = baseDirectory
            if (appendDirectory != null) {
                imagePath += appendDirectory
            }
            imagePath = "$imagePath/$fileName"
            val out = BufferedOutputStream(FileOutputStream(imagePath))
            targetImage.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.close()
        } catch (ee: Exception) {
            return (null)
        }
        return (fileName)
    }

    companion object
    {
        private const val TAG = "ExtStorageFileUtil"
    }
}
