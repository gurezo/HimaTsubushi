package jp.ic.lifewood.himatsubushi;

import jp.ic.lifewood.himatsubushi.R;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.os.Vibrator;

public class GridThree extends Activity {

	//設定ファイル情報格納クラス
	private ConfigInformation cfg_inf = new ConfigInformation();

	//---- BGM関連クラス得宣言 ----
	//ゲームBGM
	private BGMPlayer game_bgmp = null;
    private MediaPlayer game_mp =null;

	//タップ音
	private BGMPlayer tap_bgmp = null;
    private MediaPlayer tap_mp =null;

    //バイブレーション関連クラス
    private Vibrator vib = null;

    //暇潰し開始時間
	private long gameStartTime = 0;
    //暇潰し時間
	private String gamePlayTime = "";

	//ボタン状態把握用配列
	//0 - 未表示  1 - 暇画像表示  2 - 潰画像表示
	int[] btn_condition ={Hima.BUTTON_CONDITION_INIT,Hima.BUTTON_CONDITION_INIT,Hima.BUTTON_CONDITION_INIT,
						  Hima.BUTTON_CONDITION_INIT,Hima.BUTTON_CONDITION_INIT,Hima.BUTTON_CONDITION_INIT,
						  Hima.BUTTON_CONDITION_INIT,Hima.BUTTON_CONDITION_INIT,Hima.BUTTON_CONDITION_INIT};

	private int HimaButtonClickCount = 0;

	//ログ表示フラグ
	private boolean logger = true;	//true - 表示 false - 非表示

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);

        setContentView(R.layout.gridthree);

        //---- 設定データ有無判定 ----
        cfg_inf.checkConfig(this,MODE_WORLD_WRITEABLE);

        //---- 各種ボタンリスナー関連付け ----
        relationButtonListener();

        //---- 初期状態 ----
        initPannelButton();

        //---- ゲーム制御関連 ----
        // ゲームスタートボタンのクリックリスナー設定
		ButtonControl(R.id.game_start_three,true);

        // ゲームエンドボタンのクリックリスナー設定
		ButtonControl(R.id.game_end_three,false);

        // ゲームエンドボタンのクリックリスナー設定
		ButtonControl(R.id.back_three,true);

        //---- BGM再生チェック ----
        if(cfg_inf.getBgm_group() == ConfigInformation.DEFAULT_BGM_VALUE){
            //BGMPlayerクラス インスタンス生成
            game_bgmp = new BGMPlayer();
            tap_bgmp = new BGMPlayer();
            //MediaPlayerオブジェクト生成
            game_mp = tap_bgmp.Make(tap_mp,this,R.raw.tamco08s);
            tap_mp = tap_bgmp.Make(tap_mp,this,R.raw.ita1e);

            //BGM再生
            game_bgmp.Start(game_mp);

            //BGMリピート
            game_bgmp.Repeat(game_mp);
        }

        //---- バイブレーション動作チェック ----
        if(cfg_inf.isVibe() == ConfigInformation.DEFAULT_VIBE_VALUE){
        	vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        }
	}

	@Override
	protected void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
		try {
			//game_bgmp.Stop(game_mp);
			//tap_bgmp.Stop(tap_mp);
		}catch(Exception e) {
			Log.e("LOG-Himatsubushi",e.getMessage().toString());
		} finally {
			game_bgmp = null;
		    game_mp =null;
			tap_bgmp = null;
		    tap_mp = null;
		}
	}

	@Override
	 public boolean dispatchKeyEvent(KeyEvent event) {
		/*
		 * Backキー無効処理
		 */
		if(event.getAction() == KeyEvent.ACTION_DOWN){
			if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			  return true;
		  }
	  }
	  // 自動生成されたメソッド・スタブ
	  return super.dispatchKeyEvent(event);
	}

	/*
	 * ボタン情報配列初期化
	 *
	 */
	private void initPannelButtonArray() {
		for(int i=0;i<btn_condition.length;i++) {
			btn_condition[i] = Hima.BUTTON_CONDITION_INIT;
		}
	}

	/*
	 *ボタン初期化処理
	 */
	private void initPannelButton() {
		//初期表示の時は、ボタンをロック，画像無
		ButtonControl(R.id.grid11,R.drawable.mu_pannel,false);
		ButtonControl(R.id.grid12,R.drawable.mu_pannel,false);
		ButtonControl(R.id.grid13,R.drawable.mu_pannel,false);
		ButtonControl(R.id.grid21,R.drawable.mu_pannel,false);
		ButtonControl(R.id.grid22,R.drawable.mu_pannel,false);
		ButtonControl(R.id.grid23,R.drawable.mu_pannel,false);
		ButtonControl(R.id.grid31,R.drawable.mu_pannel,false);
		ButtonControl(R.id.grid32,R.drawable.mu_pannel,false);
		ButtonControl(R.id.grid33,R.drawable.mu_pannel,false);
	}

	/*
	 *ボタン制御メソッド
	 */
	private void ButtonControl(int id,int pannel_id,boolean btn_flg) {
		//ボタン情報取得
		Button btn = (Button)findViewById(id);
		//画像設定
		btn.setBackgroundResource(pannel_id);
		//ボタンロック設定
		btn.setEnabled(btn_flg);
	}

	/*
	 *ボタン制御メソッド
	 */
	private void ButtonControl(int id,boolean btn_flg) {
		//ボタン情報取得
		Button btn = (Button)findViewById(id);

		//ボタンロック設定
		btn.setEnabled(btn_flg);
	}

	/*
	 * 各種ボタンリスナー関連付けメソッド
	 */
	private void relationButtonListener() {

		//---- 暇ボタン関連 ----
        // 各暇ボタンのクリックリスナー設定
        Button grd11 = (Button)findViewById(R.id.grid11);
        grd11.setOnClickListener(new Grd11());

        Button grd12 = (Button)findViewById(R.id.grid12);
        grd12.setOnClickListener(new Grd12());

        Button grd13 = (Button)findViewById(R.id.grid13);
        grd13.setOnClickListener(new Grd13());

        Button grd21 = (Button)findViewById(R.id.grid21);
        grd21.setOnClickListener(new Grd21());

        Button grd22 = (Button)findViewById(R.id.grid22);
        grd22.setOnClickListener(new Grd22());

        Button grd23 = (Button)findViewById(R.id.grid23);
        grd23.setOnClickListener(new Grd23());

        Button grd31 = (Button)findViewById(R.id.grid31);
        grd31.setOnClickListener(new Grd31());

        Button grd32 = (Button)findViewById(R.id.grid32);
        grd32.setOnClickListener(new Grd32());

        Button grd33 = (Button)findViewById(R.id.grid33);
        grd33.setOnClickListener(new Grd33());

        //---- ゲーム制御ボタン関連 ----
        // ゲームスタートボタンのクリックリスナー設定
        Button game_start = (Button)findViewById(R.id.game_start_three);
        game_start.setOnClickListener(new Game_StartClickListener());

        // ゲームエンドボタンのクリックリスナー設定
        Button game_end = (Button)findViewById(R.id.game_end_three);
        game_end.setOnClickListener(new Game_EndClickListener());

        // ゲームエンドボタンのクリックリスナー設定
        Button back = (Button)findViewById(R.id.back_three);
        back.setOnClickListener(new BackClickListener());
	}

	/*
	 * 暇ボタン共通処理
	 */
	private void commonOnClieck(int id) {
		try {
			int btnID =id -R.id.grid11;

	    	//---- BGM再生チェック ----
	        if(cfg_inf.getBgm_group() == ConfigInformation.DEFAULT_BGM_VALUE){

    			//ログ表示判定条件式 loggerがtrueの時、表示
    			if(logger)Log.v("LOG-Himatsubushi","GridThree：commonOnClieck()：BGM再生");

	            //BGM再生
	            tap_bgmp.Start(tap_mp);

    			//ログ表示判定条件式 loggerがtrueの時、表示
    			if(logger)Log.v("LOG-Himatsubushi","GridThree：commonOnClieck()：BGM停止");
	        }

	        //---- バイブレーション動作チェック ----
	        if(cfg_inf.isVibe() == ConfigInformation.DEFAULT_VIBE_VALUE){

    			//ログ表示判定条件式 loggerがtrueの時、表示
    			if(logger)Log.v("LOG-Himatsubushi","GridThree：commonOnClieck()：バイブレーション動作");

	        	vib.vibrate(ConfigInformation.VIBE_TIME_VALUE);	// 指定時間ONする
	        }

			//ログ表示判定条件式 loggerがtrueの時、表示
			if(logger)Log.v("LOG-Himatsubushi","GridThree：commonOnClieck()：潰しパネル表示");

	        ButtonControl(id,R.drawable.tsubushi_pannel,false);
	        btn_condition[btnID]++;
	        HimaButtonClickCount++;

    		displayHima();
		} catch (Exception e) {
			Log.e("LOG-Himatsubushi",e.getMessage().toString());
		}
	}

    //---- 暇ボタンのクリックリスナー定義 ----
    class Grd11 implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {
			commonOnClieck(R.id.grid11);
        }
    }

    class Grd12 implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {
			commonOnClieck(R.id.grid12);
        }
    }

    class Grd13 implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {
			commonOnClieck(R.id.grid13);
        }
    }

    class Grd21 implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {
			commonOnClieck(R.id.grid21);
        }
    }

    class Grd22 implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {
			commonOnClieck(R.id.grid22);
        }
    }

    class Grd23 implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {
			commonOnClieck(R.id.grid23);
        }
    }

    class Grd31 implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {
			commonOnClieck(R.id.grid31);
        }
    }

    class Grd32 implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {
			commonOnClieck(R.id.grid32);
        }
    }

    class Grd33 implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {
			commonOnClieck(R.id.grid33);
        }
    }

    //---- ゲーム制御関連 ----
    // ゲーム開始ボタンのクリックリスナー定義
    class Game_StartClickListener implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {
        	Toast.makeText(GridThree.this,"ゲーム開始！！！", Toast.LENGTH_SHORT).show();

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
        	initPannelButtonArray();
        	gameStartTime = SystemClock.elapsedRealtime();
        	HimaButtonClickCount = 0;

            //---- ゲーム制御関連 ----
            // ゲームスタートボタンのクリックリスナー設定
    		ButtonControl(R.id.game_start_three,false);

            // ゲームエンドボタンのクリックリスナー設定
    		ButtonControl(R.id.game_end_three,true);

            // ゲームエンドボタンのクリックリスナー設定
    		ButtonControl(R.id.back_three,false);

    		displayHima();
        }

    }

    // ゲーム終了ボタンのクリックリスナー定義
    class Game_EndClickListener implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {

        	long minutes = 0;
        	long seconds = 0;
        	gamePlayTime = "";

    		minutes = ((SystemClock.elapsedRealtime() - gameStartTime) / 1000) / 60;
    		seconds = ((SystemClock.elapsedRealtime() - gameStartTime) / 1000) % 60;

        	gamePlayTime = "\t\tゲーム時間： ";
        	if( minutes < 10 ){
        		gamePlayTime = gamePlayTime + "0" + minutes;
        	}else{
        		gamePlayTime += minutes;
        	}
        	gamePlayTime += ":";
        	if( seconds < 10 ){
        		gamePlayTime = gamePlayTime + "0" + seconds;
        	}else{
        		gamePlayTime += seconds;
        	}

        	String crlf = System.getProperty("line.separator");
			StringBuffer msg = new StringBuffer();

			msg.append("ゲーム終了！！！");
			msg.append(crlf);
			msg.append("暇潰し数： ");
			msg.append(HimaButtonClickCount);
			msg.append(" 個");
			msg.append(crlf);
			msg.append(gamePlayTime);
        	Toast.makeText(GridThree.this,msg, Toast.LENGTH_SHORT).show();

            //---- ゲーム制御関連 ----
            // ゲームスタートボタンのクリックリスナー設定
    		ButtonControl(R.id.game_start_three,true);

            // ゲームエンドボタンのクリックリスナー設定
    		ButtonControl(R.id.game_end_three,false);

            // ゲームエンドボタンのクリックリスナー設定
    		ButtonControl(R.id.back_three,true);

    		//ボタン状態配列初期化
        	initPannelButtonArray();

            //---- 初期状態 ----
            initPannelButton();
		}
    }

    // 戻るボタンのクリックリスナー定義
    class BackClickListener implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {
			try {
	            //BGM停止チェック
	            if(cfg_inf.getBgm_group() == ConfigInformation.DEFAULT_BGM_VALUE){
		            //BGM停止
	    			game_bgmp.Stop(game_mp);
	    			tap_bgmp.Stop(tap_mp);
	            }

	        	finish();
			} catch (Exception e) {
				Log.e("LOG-Himatsubushi",e.getMessage().toString());
			}
        }
    }

    /*
     * 暇パネル表示
     *
     */
    private void displayHima() {

    	//ボタンID取得
		int btnID = 0;
		int btnIndex = 0;
		int cnt = 0;

		start:
    	do {
    		btnIndex = (int)(Math.random() * 9);
    		btnID = R.id.grid11 + btnIndex;

    		//暇画像が表示されていない時
    		if(btn_condition[btnIndex] == Hima.BUTTON_CONDITION_INIT){

    			//ログ表示判定条件式 loggerがtrueの時、表示
    			if(logger)Log.v("LOG-Himatsubushi","GridThree：displayHima()：暇パネル　表示");

    	   		ButtonControl(btnID,R.drawable.hima_pannel,true);
    	   		btn_condition[btnIndex]++;
    	   		break;
    		}

    		//永久ループ防止
    		cnt = 0;
			for(int i = 0;i < btn_condition.length;i++) {
				//暇と表示されていないボタンが無いか検索
	    		if(btn_condition[i] == Hima.BUTTON_CONDITION_INIT){

	    			//ログ表示判定条件式 loggerがtrueの時、表示
	    			if(logger)Log.v("LOG-Himatsubushi","GridThree：displayHima()：暇パネル　発見");

	    			break;
	    		}else{
	        		cnt++;
	    		}
			}
    		if(cnt == btn_condition.length) {

    			//ログ表示判定条件式 loggerがtrueの時、表示
    			if(logger)Log.v("LOG-Himatsubushi","GridThree：displayHima()：全て潰しパネル");

                //---- 初期状態 ----
                initPannelButton();

        		//ボタン状態配列初期化
            	initPannelButtonArray();

    			//ログ表示判定条件式 loggerがtrueの時、表示
    			if(logger)Log.v("LOG-Himatsubushi","GridThree：displayHima()：未表示パネル検索");

    			//終了した場合は、画面初期化
    	   		continue start;
    		}
    	} while (true);

		//ログ表示判定条件式 loggerがtrueの時、表示
		if(logger)Log.v("LOG-Himatsubushi","GridThree：displayHima()：ループ脱出");

    }

}
