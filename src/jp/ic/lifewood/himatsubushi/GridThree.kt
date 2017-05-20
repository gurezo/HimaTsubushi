package jp.ic.lifewood.himatsubushi

import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.os.Vibrator
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast

import jp.ic.lifewood.himatsubushi.R

class GridThree : Activity() {

    //ボタン状態把握用配列
    //0 - 未表示  1 - 暇画像表示  2 - 潰画像表示
    internal var btn_condition = intArrayOf(Hima.BUTTON_CONDITION_INIT, Hima.BUTTON_CONDITION_INIT, Hima.BUTTON_CONDITION_INIT, Hima.BUTTON_CONDITION_INIT, Hima.BUTTON_CONDITION_INIT, Hima.BUTTON_CONDITION_INIT, Hima.BUTTON_CONDITION_INIT, Hima.BUTTON_CONDITION_INIT, Hima.BUTTON_CONDITION_INIT)
    //設定ファイル情報格納クラス
    private val cfg_inf = ConfigInformation()
    //---- BGM関連クラス得宣言 ----
    //ゲームBGM
    private var game_bgmp: BGMPlayer? = null
    private var game_mp: MediaPlayer? = null
    //タップ音
    private var tap_bgmp: BGMPlayer? = null
    private var tap_mp: MediaPlayer? = null
    //バイブレーション関連クラス
    private var vib: Vibrator? = null
    //暇潰し開始時間
    private var gameStartTime: Long = 0
    //暇潰し時間
    private var gamePlayTime = ""
    private var HimaButtonClickCount = 0

    //ログ表示フラグ
    private val logger = true    //true - 表示 false - 非表示

    @Override
    fun onCreate(savedInstanceState: Bundle) {
        // TODO 自動生成されたメソッド・スタブ
        super.onCreate(savedInstanceState)

        setContentView(R.layout.gridthree)

        //---- 設定データ有無判定 ----
        cfg_inf.checkConfig(this, MODE_WORLD_WRITEABLE)

        //---- 各種ボタンリスナー関連付け ----
        relationButtonListener()

        //---- 初期状態 ----
        initPannelButton()

        //---- ゲーム制御関連 ----
        // ゲームスタートボタンのクリックリスナー設定
        ButtonControl(R.id.game_start_three, true)

        // ゲームエンドボタンのクリックリスナー設定
        ButtonControl(R.id.game_end_three, false)

        // ゲームエンドボタンのクリックリスナー設定
        ButtonControl(R.id.back_three, true)

        //---- BGM再生チェック ----
        if (cfg_inf.getBgm_group() === ConfigInformation.DEFAULT_BGM_VALUE) {
            //BGMPlayerクラス インスタンス生成
            game_bgmp = BGMPlayer()
            tap_bgmp = BGMPlayer()
            //MediaPlayerオブジェクト生成
            game_mp = tap_bgmp!!.Make(tap_mp, this, R.raw.tamco08s)
            tap_mp = tap_bgmp!!.Make(tap_mp, this, R.raw.ita1e)

            //BGM再生
            game_bgmp!!.Start(game_mp)

            //BGMリピート
            game_bgmp!!.Repeat(game_mp)
        }

        //---- バイブレーション動作チェック ----
        if (cfg_inf.isVibe() === ConfigInformation.DEFAULT_VIBE_VALUE) {
            vib = getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
    }

    @Override
    protected fun onDestroy() {
        // TODO 自動生成されたメソッド・スタブ
        super.onDestroy()
        try {
            //game_bgmp.Stop(game_mp);
            //tap_bgmp.Stop(tap_mp);
        } catch (e: Exception) {
            Log.e("LOG-Himatsubushi", e.getMessage().toString())
        } finally {
            game_bgmp = null
            game_mp = null
            tap_bgmp = null
            tap_mp = null
        }
    }

    @Override
    fun dispatchKeyEvent(event: KeyEvent): Boolean {
        /*
		 * Backキー無効処理
		 */
        if (event.getAction() === KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() === KeyEvent.KEYCODE_BACK) {
                return true
            }
        }
        // 自動生成されたメソッド・スタブ
        return super.dispatchKeyEvent(event)
    }

    /*
     * ボタン情報配列初期化
     *
     */
    private fun initPannelButtonArray() {
        for (i in btn_condition.indices) {
            btn_condition[i] = Hima.BUTTON_CONDITION_INIT
        }
    }

    /*
     *ボタン初期化処理
     */
    private fun initPannelButton() {
        //初期表示の時は、ボタンをロック，画像無
        ButtonControl(R.id.grid11, R.drawable.mu_pannel, false)
        ButtonControl(R.id.grid12, R.drawable.mu_pannel, false)
        ButtonControl(R.id.grid13, R.drawable.mu_pannel, false)
        ButtonControl(R.id.grid21, R.drawable.mu_pannel, false)
        ButtonControl(R.id.grid22, R.drawable.mu_pannel, false)
        ButtonControl(R.id.grid23, R.drawable.mu_pannel, false)
        ButtonControl(R.id.grid31, R.drawable.mu_pannel, false)
        ButtonControl(R.id.grid32, R.drawable.mu_pannel, false)
        ButtonControl(R.id.grid33, R.drawable.mu_pannel, false)
    }

    /*
     *ボタン制御メソッド
     */
    private fun ButtonControl(id: Int, pannel_id: Int, btn_flg: Boolean) {
        //ボタン情報取得
        val btn = findViewById(id) as Button
        //画像設定
        btn.setBackgroundResource(pannel_id)
        //ボタンロック設定
        btn.setEnabled(btn_flg)
    }

    /*
     *ボタン制御メソッド
     */
    private fun ButtonControl(id: Int, btn_flg: Boolean) {
        //ボタン情報取得
        val btn = findViewById(id) as Button

        //ボタンロック設定
        btn.setEnabled(btn_flg)
    }

    /*
     * 各種ボタンリスナー関連付けメソッド
     */
    private fun relationButtonListener() {

        //---- 暇ボタン関連 ----
        // 各暇ボタンのクリックリスナー設定
        val grd11 = findViewById(R.id.grid11) as Button
        grd11.setOnClickListener(Grd11())

        val grd12 = findViewById(R.id.grid12) as Button
        grd12.setOnClickListener(Grd12())

        val grd13 = findViewById(R.id.grid13) as Button
        grd13.setOnClickListener(Grd13())

        val grd21 = findViewById(R.id.grid21) as Button
        grd21.setOnClickListener(Grd21())

        val grd22 = findViewById(R.id.grid22) as Button
        grd22.setOnClickListener(Grd22())

        val grd23 = findViewById(R.id.grid23) as Button
        grd23.setOnClickListener(Grd23())

        val grd31 = findViewById(R.id.grid31) as Button
        grd31.setOnClickListener(Grd31())

        val grd32 = findViewById(R.id.grid32) as Button
        grd32.setOnClickListener(Grd32())

        val grd33 = findViewById(R.id.grid33) as Button
        grd33.setOnClickListener(Grd33())

        //---- ゲーム制御ボタン関連 ----
        // ゲームスタートボタンのクリックリスナー設定
        val game_start = findViewById(R.id.game_start_three) as Button
        game_start.setOnClickListener(Game_StartClickListener())

        // ゲームエンドボタンのクリックリスナー設定
        val game_end = findViewById(R.id.game_end_three) as Button
        game_end.setOnClickListener(Game_EndClickListener())

        // ゲームエンドボタンのクリックリスナー設定
        val back = findViewById(R.id.back_three) as Button
        back.setOnClickListener(BackClickListener())
    }

    /*
     * 暇ボタン共通処理
     */
    private fun commonOnClieck(id: Int) {
        try {
            val btnID = id - R.id.grid11

            //---- BGM再生チェック ----
            if (cfg_inf.getBgm_group() === ConfigInformation.DEFAULT_BGM_VALUE) {

                //ログ表示判定条件式 loggerがtrueの時、表示
                if (logger) Log.v("LOG-Himatsubushi", "GridThree：commonOnClieck()：BGM再生")

                //BGM再生
                tap_bgmp!!.Start(tap_mp)

                //ログ表示判定条件式 loggerがtrueの時、表示
                if (logger) Log.v("LOG-Himatsubushi", "GridThree：commonOnClieck()：BGM停止")
            }

            //---- バイブレーション動作チェック ----
            if (cfg_inf.isVibe() === ConfigInformation.DEFAULT_VIBE_VALUE) {

                //ログ表示判定条件式 loggerがtrueの時、表示
                if (logger) Log.v("LOG-Himatsubushi", "GridThree：commonOnClieck()：バイブレーション動作")

                vib!!.vibrate(ConfigInformation.VIBE_TIME_VALUE)    // 指定時間ONする
            }

            //ログ表示判定条件式 loggerがtrueの時、表示
            if (logger) Log.v("LOG-Himatsubushi", "GridThree：commonOnClieck()：潰しパネル表示")

            ButtonControl(id, R.drawable.tsubushi_pannel, false)
            btn_condition[btnID]++
            HimaButtonClickCount++

            displayHima()
        } catch (e: Exception) {
            Log.e("LOG-Himatsubushi", e.getMessage().toString())
        }

    }

    /*
     * 暇パネル表示
     *
     */
    private fun displayHima() {

        //ボタンID取得
        var btnID = 0
        var btnIndex = 0
        var cnt = 0

        start@ do {
            btnIndex = (Math.random() * 9) as Int
            btnID = R.id.grid11 + btnIndex

            //暇画像が表示されていない時
            if (btn_condition[btnIndex] == Hima.BUTTON_CONDITION_INIT) {

                //ログ表示判定条件式 loggerがtrueの時、表示
                if (logger) Log.v("LOG-Himatsubushi", "GridThree：displayHima()：暇パネル　表示")

                ButtonControl(btnID, R.drawable.hima_pannel, true)
                btn_condition[btnIndex]++
                break
            }

            //永久ループ防止
            cnt = 0
            for (i in btn_condition.indices) {
                //暇と表示されていないボタンが無いか検索
                if (btn_condition[i] == Hima.BUTTON_CONDITION_INIT) {

                    //ログ表示判定条件式 loggerがtrueの時、表示
                    if (logger) Log.v("LOG-Himatsubushi", "GridThree：displayHima()：暇パネル　発見")

                    break
                } else {
                    cnt++
                }
            }
            if (cnt == btn_condition.size) {

                //ログ表示判定条件式 loggerがtrueの時、表示
                if (logger) Log.v("LOG-Himatsubushi", "GridThree：displayHima()：全て潰しパネル")

                //---- 初期状態 ----
                initPannelButton()

                //ボタン状態配列初期化
                initPannelButtonArray()

                //ログ表示判定条件式 loggerがtrueの時、表示
                if (logger) Log.v("LOG-Himatsubushi", "GridThree：displayHima()：未表示パネル検索")

                //終了した場合は、画面初期化
                continue@start
            }
        } while (true)

        //ログ表示判定条件式 loggerがtrueの時、表示
        if (logger) Log.v("LOG-Himatsubushi", "GridThree：displayHima()：ループ脱出")

    }

    //---- 暇ボタンのクリックリスナー定義 ----
    internal inner class Grd11 : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {
            commonOnClieck(R.id.grid11)
        }
    }

    internal inner class Grd12 : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {
            commonOnClieck(R.id.grid12)
        }
    }

    internal inner class Grd13 : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {
            commonOnClieck(R.id.grid13)
        }
    }

    internal inner class Grd21 : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {
            commonOnClieck(R.id.grid21)
        }
    }

    internal inner class Grd22 : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {
            commonOnClieck(R.id.grid22)
        }
    }

    internal inner class Grd23 : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {
            commonOnClieck(R.id.grid23)
        }
    }

    internal inner class Grd31 : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {
            commonOnClieck(R.id.grid31)
        }
    }

    internal inner class Grd32 : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {
            commonOnClieck(R.id.grid32)
        }
    }

    internal inner class Grd33 : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {
            commonOnClieck(R.id.grid33)
        }
    }

    //---- ゲーム制御関連 ----
    // ゲーム開始ボタンのクリックリスナー定義
    internal inner class Game_StartClickListener : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {
            Toast.makeText(this@GridThree, "ゲーム開始！！！", Toast.LENGTH_SHORT).show()

            /*
        	 * クリックされたら、戻るボタン，
        	 * 開始ボタンをロック，ランダムにパネルボタンを解除、
        	 * タップしたら、画像切り替え，ロック
        	 *
        	 * 全てのパネルボタンをタップしたら、
        	 * 初期化⇒最初からスタート
        	 *
        	 * タップ数とゲーム時間を計測
        	 *
        	 */
            initPannelButtonArray()
            gameStartTime = SystemClock.elapsedRealtime()
            HimaButtonClickCount = 0

            //---- ゲーム制御関連 ----
            // ゲームスタートボタンのクリックリスナー設定
            ButtonControl(R.id.game_start_three, false)

            // ゲームエンドボタンのクリックリスナー設定
            ButtonControl(R.id.game_end_three, true)

            // ゲームエンドボタンのクリックリスナー設定
            ButtonControl(R.id.back_three, false)

            displayHima()
        }

    }

    // ゲーム終了ボタンのクリックリスナー定義
    internal inner class Game_EndClickListener : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {

            var minutes: Long = 0
            var seconds: Long = 0
            gamePlayTime = ""

            minutes = (SystemClock.elapsedRealtime() - gameStartTime) / 1000 / 60
            seconds = (SystemClock.elapsedRealtime() - gameStartTime) / 1000 % 60

            gamePlayTime = "\t\tゲーム時間： "
            if (minutes < 10) {
                gamePlayTime = gamePlayTime + "0" + minutes
            } else {
                gamePlayTime += minutes
            }
            gamePlayTime += ":"
            if (seconds < 10) {
                gamePlayTime = gamePlayTime + "0" + seconds
            } else {
                gamePlayTime += seconds
            }

            val crlf = System.getProperty("line.separator")
            val msg = StringBuffer()

            msg.append("ゲーム終了！！！")
            msg.append(crlf)
            msg.append("暇潰し数： ")
            msg.append(HimaButtonClickCount)
            msg.append(" 個")
            msg.append(crlf)
            msg.append(gamePlayTime)
            Toast.makeText(this@GridThree, msg, Toast.LENGTH_SHORT).show()

            //---- ゲーム制御関連 ----
            // ゲームスタートボタンのクリックリスナー設定
            ButtonControl(R.id.game_start_three, true)

            // ゲームエンドボタンのクリックリスナー設定
            ButtonControl(R.id.game_end_three, false)

            // ゲームエンドボタンのクリックリスナー設定
            ButtonControl(R.id.back_three, true)

            //ボタン状態配列初期化
            initPannelButtonArray()

            //---- 初期状態 ----
            initPannelButton()
        }
    }

    // 戻るボタンのクリックリスナー定義
    internal inner class BackClickListener : OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        fun onClick(v: View) {
            try {
                //BGM停止チェック
                if (cfg_inf.getBgm_group() === ConfigInformation.DEFAULT_BGM_VALUE) {
                    //BGM停止
                    game_bgmp!!.Stop(game_mp)
                    tap_bgmp!!.Stop(tap_mp)
                }

                finish()
            } catch (e: Exception) {
                Log.e("LOG-Himatsubushi", e.getMessage().toString())
            }

        }
    }

}
