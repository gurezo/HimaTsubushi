package jp.ic.lifewood.himatsubushi;

import jp.ic.lifewood.himatsubushi.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Config extends Activity {

	//設定ファイル情報格納クラス
	private ConfigInformation cfg_inf = new ConfigInformation();

	private CheckBox vibe = null;
	private RadioGroup bgm_group = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
        setContentView(R.layout.config);

        //画面部品連携
        connectDisplayParts();

        //---- 設定データ有無判定 ----
        cfg_inf.checkConfig(this,MODE_WORLD_WRITEABLE);

		vibe.setChecked(cfg_inf.isVibe());
		bgm_group.check(cfg_inf.getBgm_group());

        // 保存ボタンのクリックリスナー設定
        Button save_button = (Button)findViewById(R.id.save_button);
        save_button.setOnClickListener(new SaveButtonClickListener());

        // 初期化ボタンのクリックリスナー初期化
        Button init_button = (Button)findViewById(R.id.init_button);
        init_button.setOnClickListener(new InitButtonClickListener());

        // 戻るボタンのクリックリスナー戻る
        Button back_button = (Button)findViewById(R.id.back_button);
        back_button.setOnClickListener(new BackButtonClickListener());
	}

	/**
	 * Config.javaとconfig.xmlの連携
	 */
	private void connectDisplayParts() {
		vibe = (CheckBox)findViewById(R.id.vibe);
		bgm_group = (RadioGroup)findViewById(R.id.bgm_group);
	}

	/**
	 * 画面設定情報保存処理
	 */
	private boolean saveConfig() {

		try {

			SharedPreferences shpr = getSharedPreferences(ConfigInformation.PREFERENCE_FILE_NAME, MODE_WORLD_WRITEABLE);
			SharedPreferences.Editor edt = shpr.edit();

			edt.putBoolean(ConfigInformation.KEY_WORD_VIBE, vibe.isChecked());

			//if文で条件分岐
			if(bgm_group.getCheckedRadioButtonId() == R.id.bgm_on){
	    		edt.putInt(ConfigInformation.KEY_WORD_BGM, R.id.bgm_on);
			}else{
	    		edt.putInt(ConfigInformation.KEY_WORD_BGM, R.id.bgm_off);
			}

			edt.commit();
		}catch(Exception e) {
			return false;
		}
		return true;
	}

    // 保存ボタンのクリックリスナー定義
    class SaveButtonClickListener implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
		@Override
        public void onClick(View v) {
        	Toast.makeText(Config.this,"保存するぞ！！！", Toast.LENGTH_SHORT).show();

        	if(!saveConfig()){
            	Toast.makeText(Config.this,"保存に失敗しました。！！！", Toast.LENGTH_SHORT).show();
        		return;
        	}

        	//保存の処理を記述して終了
        	finish();
        }
    }

    // 初期化ボタンのクリックリスナー定義
    class InitButtonClickListener implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        public void onClick(View v) {
        	Toast.makeText(Config.this,"初期化するぞ！！！", Toast.LENGTH_SHORT).show();
            //初期値セット
    		vibe.setChecked(ConfigInformation.DEFAULT_VIBE_VALUE);
    		bgm_group.check(ConfigInformation.DEFAULT_BGM_VALUE);

        }
    }

    // 戻るボタンのクリックリスナー定義
    class BackButtonClickListener implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        public void onClick(View v) {
        	Toast.makeText(Config.this,"戻るぞ！！！", Toast.LENGTH_SHORT).show();
        	finish();
        }
    }
}
