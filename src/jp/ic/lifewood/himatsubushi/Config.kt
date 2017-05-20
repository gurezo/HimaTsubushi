package jp.ic.lifewood.himatsubushi

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.Toast

import jp.ic.lifewood.himatsubushi.R

class Config : Activity() {

    //設定ファイル情報格納クラス
    private val cfg_inf = ConfigInformation()

    private var vibe: CheckBox? = null
    private var bgm_group: RadioGroup? = null

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        // TODO 自動生成されたメソッド・スタブ
        super.onCreate(savedInstanceState)
        setContentView(R.layout.config)

        //画面部品連携
        connectDisplayParts()

        //---- 設定データ有無判定 ----
        cfg_inf.checkConfig(this, MODE_WORLD_WRITEABLE)

        vibe!!.setChecked(cfg_inf.isVibe())
        bgm_group!!.check(cfg_inf.getBgm_group())

        // 保存ボタンのクリックリスナー設定
        val save_button = findViewById(R.id.save_button) as Button
        save_button.setOnClickListener(SaveButtonClickListener())

        // 初期化ボタンのクリックリスナー初期化
        val init_button = findViewById(R.id.init_button) as Button
        init_button.setOnClickListener(InitButtonClickListener())

        // 戻るボタンのクリックリスナー戻る
        val back_button = findViewById(R.id.back_button) as Button
        back_button.setOnClickListener(BackButtonClickListener())
    }

    /**
     * Config.javaとconfig.xmlの連携
     */
    private fun connectDisplayParts() {
        vibe = findViewById(R.id.vibe) as CheckBox
        bgm_group = findViewById(R.id.bgm_group) as RadioGroup
    }

    /**
     * 画面設定情報保存処理
     */
    private fun saveConfig(): Boolean {

        try {

            val shpr = getSharedPreferences(ConfigInformation.PREFERENCE_FILE_NAME, MODE_WORLD_WRITEABLE)
            val edt = shpr.edit()

            edt.putBoolean(ConfigInformation.KEY_WORD_VIBE, vibe!!.isChecked())

            //if文で条件分岐
            if (bgm_group!!.getCheckedRadioButtonId() === R.id.bgm_on) {
                edt.putInt(ConfigInformation.KEY_WORD_BGM, R.id.bgm_on)
            } else {
                edt.putInt(ConfigInformation.KEY_WORD_BGM, R.id.bgm_off)
            }

            edt.commit()
        } catch (e: Exception) {
            return false
        }

        return true
    }

    // 保存ボタンのクリックリスナー定義
    internal inner class SaveButtonClickListener : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {
            Toast.makeText(this@Config, "保存するぞ！！！", Toast.LENGTH_SHORT).show()

            if (!saveConfig()) {
                Toast.makeText(this@Config, "保存に失敗しました。！！！", Toast.LENGTH_SHORT).show()
                return
            }

            //保存の処理を記述して終了
            finish()
        }
    }

    // 初期化ボタンのクリックリスナー定義
    internal inner class InitButtonClickListener : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        fun onClick(v: View) {
            Toast.makeText(this@Config, "初期化するぞ！！！", Toast.LENGTH_SHORT).show()
            //初期値セット
            vibe!!.setChecked(ConfigInformation.DEFAULT_VIBE_VALUE)
            bgm_group!!.check(ConfigInformation.DEFAULT_BGM_VALUE)

        }
    }

    // 戻るボタンのクリックリスナー定義
    internal inner class BackButtonClickListener : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        fun onClick(v: View) {
            Toast.makeText(this@Config, "戻るぞ！！！", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
