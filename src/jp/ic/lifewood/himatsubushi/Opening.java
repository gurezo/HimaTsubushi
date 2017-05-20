package jp.ic.lifewood.himatsubushi;

import jp.ic.lifewood.himatsubushi.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.media.MediaPlayer;

/**
 * @author ic_lifewood
 * 暇潰しアプリ
 * オープニング画面アクティビティ
 *
 */
public class Opening extends Activity {

	//設定ファイル情報格納クラス
	private ConfigInformation cfg_inf = new ConfigInformation();

	//BGM関連クラス得宣言
	private BGMPlayer bgm_p = null;
    private MediaPlayer mp =null;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening);

        //---- 設定データ有無判定 ----
        cfg_inf.checkConfig(this,MODE_WORLD_WRITEABLE);

        // 設定ボタンのクリックリスナー設定
        Button config_button = (Button)findViewById(R.id.config_button);
        config_button.setOnClickListener(new Config_ButtonClickListener());

        // ゲームスタートボタンのクリックリスナー設定
        Button game_button = (Button)findViewById(R.id.game);
        game_button.setOnClickListener(new Game_ButtonClickListener());

        // ゲームエンドボタンのクリックリスナー設定
        Button app_end = (Button)findViewById(R.id.app_end);
        app_end.setOnClickListener(new App_EndButtonClickListener());

        //BGM再生チェック
        if(cfg_inf.getBgm_group() == ConfigInformation.DEFAULT_BGM_VALUE){
            //BGMPlayerクラス インスタンス生成
            bgm_p = new BGMPlayer();

            //MediaPlayerオブジェクト生成
            mp = bgm_p.Make(mp,this,R.raw.woodl);

            //BGM再生
            bgm_p.Start(mp);

            //BGMリピート
            bgm_p.Repeat(mp);
        }

    }

    @Override
    protected void onRestart() {
    	// TODO 自動生成されたメソッド・スタブ
    	super.onRestart();

        //---- 設定データ有無判定 ----
        cfg_inf.checkConfig(this,MODE_WORLD_WRITEABLE);

        //画面が再表示された時に動作
        //BGM再生チェック
        if(cfg_inf.getBgm_group() == ConfigInformation.DEFAULT_BGM_VALUE){
            bgm_p = new BGMPlayer();

            //MediaPlayerオブジェクト生成
            mp = bgm_p.Make(mp,this,R.raw.woodl);

            //BGM再生
            bgm_p.Start(mp);
            //BGMリピート
            bgm_p.Repeat(mp);
        }
    }

    @Override
    protected void onDestroy() {
    	// TODO 自動生成されたメソッド・スタブ
    	super.onDestroy();
		try {
            //BGM停止チェック
            if(cfg_inf.getBgm_group() == ConfigInformation.DEFAULT_BGM_VALUE){
	            //BGM停止
	        	bgm_p.Stop(mp);
            }
		} catch(Exception e) {
			Log.e("LOG-Himatsubushi",e.getMessage().toString());
		} finally {
			bgm_p = null;
		    mp =null;
		}
    }

    // 設定ボタンのクリックリスナー定義
    class Config_ButtonClickListener implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        public void onClick(View v) {
        	Toast.makeText(Opening.this,"設定するぞ！！！", Toast.LENGTH_SHORT).show();
        	Intent config_intent = new Intent(Opening.this,Config.class);

            //BGM停止チェック
            if(cfg_inf.getBgm_group() == ConfigInformation.DEFAULT_BGM_VALUE){
	            //BGM停止
	        	bgm_p.Stop(mp);
            }

            // 次のアクティビティの起動
            startActivity(config_intent);
        }
    }

    // ゲーム開始ボタンのクリックリスナー定義
    class Game_ButtonClickListener implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        public void onClick(View v) {
        	Toast.makeText(Opening.this,"ゲーム開始！！！", Toast.LENGTH_SHORT).show();
        	Intent grid_intent = null;

    		//３マス
        	grid_intent = new Intent(Opening.this,GridThree.class);

            //BGM停止チェック
            if(cfg_inf.getBgm_group() == ConfigInformation.DEFAULT_BGM_VALUE){
	            //BGM停止
	        	bgm_p.Stop(mp);
            }

        	// 次のアクティビティの起動
            startActivity(grid_intent);
        }
    }

    // ゲーム終了ボタンのクリックリスナー定義
    class App_EndButtonClickListener implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        public void onClick(View v) {
        	Toast.makeText(Opening.this,"ゲーム終了！！！", Toast.LENGTH_SHORT).show();
        	finish();
        }
    }
}