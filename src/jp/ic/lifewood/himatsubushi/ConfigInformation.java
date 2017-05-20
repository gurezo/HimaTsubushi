package jp.ic.lifewood.himatsubushi;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import jp.ic.lifewood.himatsubushi.R;

public class ConfigInformation {

	public static final boolean DEFAULT_VIBE_VALUE = true;			//バイブ初期値⇒有
	public static final int DEFAULT_BGM_VALUE = R.id.bgm_on ;		//ＢＧＭ初期値⇒有

	public static final String PREFERENCE_FILE_NAME = "HimaTsubushi";

	public static final String KEY_WORD_VIBE = "VIBE";
	public static final String KEY_WORD_BGM = "BGM";
	public static final String KEY_WORD_GRID = "GRID";

	public static final int VIBE_TIME_VALUE = 100;					//バイブ時間100ミリ秒⇒0.1秒

	private boolean vibe;
	private int bgm_group;
	private int grid_group;

	public ConfigInformation() {
		this.vibe = DEFAULT_VIBE_VALUE;
		this.bgm_group = DEFAULT_BGM_VALUE;
	}

	public ConfigInformation(boolean vibe,int bgm_group,int grid_group) {
		this.vibe = vibe;
		this.bgm_group = bgm_group;
		this.grid_group = grid_group;
	}

	public boolean isVibe() {
		return vibe;
	}

	public void setVibe(boolean vibe) {
		this.vibe = vibe;
	}

	public int getBgm_group() {
		return bgm_group;
	}

	public void setBgm_group(int bgm_group) {
		this.bgm_group = bgm_group;
	}

	public int getGrid_group() {
		return grid_group;
	}

	public void setGrid_group(int grid_group) {
		this.grid_group = grid_group;
	}

	/**
	 * 設定ファイル存在チェック処理
	 */
	public boolean checkConfig(Context cont,int mode){

		try {
			SharedPreferences shpr = cont.getSharedPreferences(ConfigInformation.PREFERENCE_FILE_NAME, mode);
			this.vibe = shpr.getBoolean(KEY_WORD_VIBE, DEFAULT_VIBE_VALUE);
			this.bgm_group = shpr.getInt(KEY_WORD_BGM, DEFAULT_BGM_VALUE);
		} catch(Exception e){
			Log.e("HIMA_LOG",e.getMessage());
			return false;
		}
		return true;
	}


}
