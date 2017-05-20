package jp.ic.lifewood.himatsubushi;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * @author ic_lifewood
 *
 */
public class BGMPlayer implements MediaPlayer.OnCompletionListener{

	/**
	 * BGMPlayerコンストラクタ
	 */
	public BGMPlayer(){

	}

	/**
	 * MediaPlayerコンストラクタ
	 */
	public MediaPlayer Make(MediaPlayer mp,Context cont,int music_id){
		try {
			mp=MediaPlayer.create(cont,music_id);
		} catch(Exception e) {
			Log.e("LOG-Himatsubushi",e.getMessage());
		}
		return mp;
	}

	public void Start(MediaPlayer mp){
		try {
			//mp.prepare();
			mp.seekTo(0);
			mp.start();
		} catch(Exception e) {
			Log.e("LOG-Himatsubushi",e.getMessage());
		}
	}

	public void Repeat(MediaPlayer mp){
		mp.setLooping(true);
	}

	public void Stop(MediaPlayer mp){
		mp.stop();
		mp.setOnCompletionListener(null);
		mp.release();
		mp=null;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO 自動生成されたメソッド・スタブ

	}


}
