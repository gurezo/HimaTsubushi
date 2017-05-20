package jp.ic.lifewood.himatsubushi

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

/**
 * @author ic_lifewood
 */
/**
 * BGMPlayerコンストラクタ
 */
class BGMPlayer : MediaPlayer.OnCompletionListener {

    /**
     * MediaPlayerコンストラクタ
     */
    fun Make(mp: MediaPlayer, cont: Context, music_id: Int): MediaPlayer {
        var mp = mp
        try {
            mp = MediaPlayer.create(cont, music_id)
        } catch (e: Exception) {
            Log.e("LOG-Himatsubushi", e.getMessage())
        }

        return mp
    }

    fun Start(mp: MediaPlayer) {
        try {
            //mp.prepare();
            mp.seekTo(0)
            mp.start()
        } catch (e: Exception) {
            Log.e("LOG-Himatsubushi", e.getMessage())
        }

    }

    fun Repeat(mp: MediaPlayer) {
        mp.setLooping(true)
    }

    fun Stop(mp: MediaPlayer?) {
        var mp = mp
        mp!!.stop()
        mp!!.setOnCompletionListener(null)
        mp!!.release()
        mp = null
    }

    @Override
    fun onCompletion(mp: MediaPlayer) {
        // TODO 自動生成されたメソッド・スタブ

    }


}
