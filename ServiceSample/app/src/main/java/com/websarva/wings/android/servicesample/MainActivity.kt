package com.websarva.wings.android.servicesample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat

/**
 * 『Androidアプリ開発の教科書Kotlin』
 * 第13章
 * サービスサンプル
 *
 * アクティビティクラス。
 *
 * @author Shinzo SAITO
 */
class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// POST_NOTIFICATIONSの許可が下りていないなら…
//		if (ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//			// 許可をPOST_NOTIFICATIONSに設定。
//			val permissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
//			// 許可を求めるダイアログを表示。その際、リクエストコードを1000に設定。
//			ActivityCompat.requestPermissions(this@MainActivity, permissions, 1000)
//			// onCreate()メソッドを終了。
//			return
//		}

		// Intentから通知のタップからの引き継ぎデータを取得。
		val fromNotification = intent.getBooleanExtra("fromNotification", false)
		// 引き継ぎデータが存在、つまり通知のタップからならば…
		if(fromNotification) {
			// 再生ボタンをタップ不可に、停止ボタンをタップ可に変更。
			val btPlay = findViewById<Button>(R.id.btPlay)
			val btStop = findViewById<Button>(R.id.btStop)
			btPlay.isEnabled = false
			btStop.isEnabled = true
		}
	}

	/**
	 * 再生ボタンタップ時の処理メソッド。
	 *
	 * @param view 画面部品
	 */
	fun onPlayButtonClick(view: View) {
		// インテントオブジェクトを生成。
		val intent = Intent(this@MainActivity, SoundManageService::class.java)
		// サービスを起動。
		startService(intent)
		// 再生ボタンをタップ不可に、停止ボタンをタップ可に変更。
		val btPlay = findViewById<Button>(R.id.btPlay)
		val btStop = findViewById<Button>(R.id.btStop)
		btPlay.isEnabled = false
		btStop.isEnabled = true
	}

	/**
	 * 停止ボタンタップ時の処理メソッド。
	 *
	 * @param view 画面部品
	 */
	fun onStopButtonClick(view: View) {
		// インテントオブジェクトを生成。
		val intent = Intent(this@MainActivity, SoundManageService::class.java)
		// サービスを停止。
		stopService(intent)
		// 再生ボタンをタップ可に、停止ボタンをタップ不可に変更。
		val btPlay = findViewById<Button>(R.id.btPlay)
		val btStop = findViewById<Button>(R.id.btStop)
		btPlay.isEnabled = true
		btStop.isEnabled = false
	}
}
