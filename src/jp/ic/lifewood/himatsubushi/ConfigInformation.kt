package jp.ic.lifewood.himatsubushi

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

import jp.ic.lifewood.himatsubushi.R

class ConfigInformation {

    var isVibe: Boolean = false
    var bgm_group: Int = 0
    var grid_group: Int = 0

    constructor() {
        this.isVibe = DEFAULT_VIBE_VALUE
        this.bgm_group = DEFAULT_BGM_VALUE
    }

    constructor(vibe: Boolean, bgm_group: Int, grid_group: Int) {
        this.isVibe = vibe
        this.bgm_group = bgm_group
        this.grid_group = grid_group
    }

    /**
     * 設定ファイル存在チェック処理
     */
    fun checkConfig(cont: Context, mode: Int): Boolean {

        try {
            val shpr = cont.getSharedPreferences(ConfigInformation.PREFERENCE_FILE_NAME, mode)
            this.isVibe = shpr.getBoolean(KEY_WORD_VIBE, DEFAULT_VIBE_VALUE)
            this.bgm_group = shpr.getInt(KEY_WORD_BGM, DEFAULT_BGM_VALUE)
        } catch (e: Exception) {
            Log.e("HIMA_LOG", e.getMessage())
            return false
        }

        return true
    }

    companion object {

        val DEFAULT_VIBE_VALUE = true            //バイブ初期値⇒有
        val DEFAULT_BGM_VALUE = R.id.bgm_on        //ＢＧＭ初期値⇒有

        val PREFERENCE_FILE_NAME = "HimaTsubushi"

        val KEY_WORD_VIBE = "VIBE"
        val KEY_WORD_BGM = "BGM"
        val KEY_WORD_GRID = "GRID"

        val VIBE_TIME_VALUE = 100                    //バイブ時間100ミリ秒⇒0.1秒
    }


}
