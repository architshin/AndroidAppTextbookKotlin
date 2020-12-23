package com.websarva.wings.android.lifecyclesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

/**
 * 『Androidアプリ開発の教科書Kotlin』
 * 第7章
 * ライフサイクルサンプル
 *
 * 第2画面のアクティビティクラス。
 *
 * @author Shinzo SAITO
 */
class SubActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		Log.i("LifeCycleSample", "Sub onCreate() called.")
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_sub)
	}

	public override fun onStart() {
		Log.i("LifeCycleSample", "Sub onStart() called.")
		super.onStart()
	}

	public override fun onRestart() {
		Log.i("LifeCycleSample", "Sub onRestart() called.")
		super.onRestart()
	}

	public override fun onResume() {
		Log.i("LifeCycleSample", "Sub onResume() called.")
		super.onResume()
	}

	public override fun onPause() {
		Log.i("LifeCycleSample", "Sub onPause() called.")
		super.onPause()
	}

	public override fun onStop() {
		Log.i("LifeCycleSample", "Sub onStop() called.")
		super.onStop()
	}

	public override fun onDestroy() {
		Log.i("LifeCycleSample", "Sub onDestory() called.")
		super.onDestroy()
	}

	/**
	 * 「前の画面を表示」ボタンがタップされた時の処理。
	 */
	fun onButtonClick(view: View) {
		// このアクティビティの終了。
		finish()
	}
}
