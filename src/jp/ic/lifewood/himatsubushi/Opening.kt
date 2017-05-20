package jp.ic.lifewood.himatsubushi

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast

import jp.ic.lifewood.himatsubushi.R

/**
 * @author ic_lifewood
 * *         暇潰しアプリ
 * *         オープニング画面アクティビティ
 */
class Opening : Activity() {

    //設定ファイル情報格納クラス
    private val cfg_inf = ConfigInformation()

    //BGM関連クラス得宣言
    private var bgm_p: BGMPlayer? = null
    private var mp: MediaPlayer? = null

    /**
     * Called when the activity is first created.
     */
    @Override
    fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.opening)

        //---- 設定データ有無判定 ----
        cfg_inf.checkConfig(this, MODE_WORLD_WRITEABLE)

        // 設定ボタンのクリックリスナー設定
        val config_button = findViewById(R.id.config_button) as Button
        config_button.setOnClickListener(Config_ButtonClickListener())

        // ゲームスタートボタンのクリックリスナー設定
        val game_button = findViewById(R.id.game) as Button
        game_button.setOnClickListener(Game_ButtonClickListener())

        // ゲームエンドボタンのクリックリスナー設定
        val app_end = findViewById(R.id.app_end) as Button
        app_end.setOnClickListener(App_EndButtonClickListener())

        //BGM再生チェック
        if (cfg_inf.getBgm_group() === ConfigInformation.DEFAULT_BGM_VALUE) {
            //BGMPlayerクラス インスタンス生成
            bgm_p = BGMPlayer()

            //MediaPlayerオブジェクト生成
            mp = bgm_p!!.Make(mp, this, R.raw.woodl)

            //BGM再生
            bgm_p!!.Start(mp)

            //BGMリピート
            bgm_p!!.Repeat(mp)
        }

    }

    @Override
    protected fun onRestart() {
        // TODO 自動生成されたメソッド・スタブ
        super.onRestart()

        //---- 設定データ有無判定 ----
        cfg_inf.checkConfig(this, MODE_WORLD_WRITEABLE)

        //画面が再表示された時に動作
        //BGM再生チェック
        if (cfg_inf.getBgm_group() === ConfigInformation.DEFAULT_BGM_VALUE) {
            bgm_p = BGMPlayer()

            //MediaPlayerオブジェクト生成
            mp = bgm_p!!.Make(mp, this, R.raw.woodl)

            //BGM再生
            bgm_p!!.Start(mp)
            //BGMリピート
            bgm_p!!.Repeat(mp)
        }
    }

    @Override
    protected fun onDestroy() {
        // TODO 自動生成されたメソッド・スタブ
        super.onDestroy()
        try {
            //BGM停止チェック
            if (cfg_inf.getBgm_group() === ConfigInformation.DEFAULT_BGM_VALUE) {
                //BGM停止
                bgm_p!!.Stop(mp)
            }
        } catch (e: Exception) {
            Log.e("LOG-Himatsubushi", e.getMessage().toString())
        } finally {
            bgm_p = null
            mp = null
        }
    }

    // 設定ボタンのクリックリスナー定義
    internal inner class Config_ButtonClickListener : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        fun onClick(v: View) {
            Toast.makeText(this@Opening, "設定するぞ！！！", Toast.LENGTH_SHORT).show()
            val config_intent = Intent(this@Opening, Config::class.java)

            //BGM停止チェック
            if (cfg_inf.getBgm_group() === ConfigInformation.DEFAULT_BGM_VALUE) {
                //BGM停止
                bgm_p!!.Stop(mp)
            }

            // 次のアクティビティの起動
            startActivity(config_intent)
        }
    }

    // ゲーム開始ボタンのクリックリスナー定義
    internal inner class Game_ButtonClickListener : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        fun onClick(v: View) {
            Toast.makeText(this@Opening, "ゲーム開始！！！", Toast.LENGTH_SHORT).show()
            var grid_intent: Intent? = null

            //３マス
            grid_intent = Intent(this@Opening, GridThree::class.java)

            //BGM停止チェック
            if (cfg_inf.getBgm_group() === ConfigInformation.DEFAULT_BGM_VALUE) {
                //BGM停止
                bgm_p!!.Stop(mp)
            }

            // 次のアクティビティの起動
            startActivity(grid_intent)
        }
    }

    // ゲーム終了ボタンのクリックリスナー定義
    internal inner class App_EndButtonClickListener : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        fun onClick(v: View) {
            Toast.makeText(this@Opening, "ゲーム終了！！！", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}