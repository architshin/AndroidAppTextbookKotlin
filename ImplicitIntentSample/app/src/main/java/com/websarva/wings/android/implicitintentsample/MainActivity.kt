package com.websarva.wings.android.implicitintentsample

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.net.URLEncoder

/**
 * 『Androidアプリ開発の教科書』
 * 第14章
 * 暗黙的インテントサンプル
 *
 * アクティビティクラス。
 *
 * @author Shinzo SAITO
 */
class MainActivity : AppCompatActivity() {
	/**
	 * 緯度フィールド。
	 */
	private var _latitude = 0.0
	/**
	 * 経度フィールド
	 */
	private var _longitude = 0.0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		//LocationManagerオブジェクトを取得。
		val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
		//位置情報が更新された際のリスナオブジェクトを生成。
		val locationListener = GPSLocationListener()
		//ACCESS_FINE_LOCATIONの許可が下りていないなら…
		if(ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			//ACCESS_FINE_LOCATIONの許可を求めるダイアログを表示。その際、リクエストコードを1000に設定。
			val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
			ActivityCompat.requestPermissions(this@MainActivity, permissions, 1000)
			//onCreate()メソッドを終了。
			return
		}
		//位置情報の追跡を開始。
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		//ACCESS_FINE_LOCATIONに対するパーミションダイアログでかつ許可を選択したなら…
		if(requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			//LocationManagerオブジェクトを取得。
			val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
			//位置情報が更新された際のリスナオブジェクトを生成。
			val locationListener = GPSLocationListener()
			//再度ACCESS_FINE_LOCATIONの許可が下りていないかどうかのチェックをし、降りていないなら処理を中止。
			if(ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				return
			}
			//位置情報の追跡を開始。
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
		}
	}

	/**
	 * 地図検索ボタンがタップされたときの処理メソッド。
	 */
	fun onMapSearchButtonClick(view: View) {
		//入力欄に入力されたキーワード文字列を取得。
		val etSearchWord = findViewById<EditText>(R.id.etSearchWord)
		var searchWord = etSearchWord.text.toString()
		//入力されたキーワードをURLエンコード。
		searchWord = URLEncoder.encode(searchWord, "UTF-8")
		//マップアプリと連携するURI文字列を生成。
		val uriStr = "geo:0,0?q=${searchWord}"
		//URI文字列からURIオブジェクトを生成。
		val uri = Uri.parse(uriStr)
		//Intentオブジェクトを生成。
		val intent = Intent(Intent.ACTION_VIEW, uri)
		//アクティビティを起動。
		startActivity(intent)
	}

	/**
	 * 現在地の地図表示ボタンがタップされたときの処理メソッド。
	 */
	fun onMapShowCurrentButtonClick(view: View) {
		//フィールドの緯度と経度の値をもとにマップアプリと連携するURI文字列を生成。
		val uriStr = "geo:${_latitude},${_longitude}"
		//URI文字列からURIオブジェクトを生成。
		val uri = Uri.parse(uriStr)
		//Intentオブジェクトを生成。
		val intent = Intent(Intent.ACTION_VIEW, uri)
		//アクティビティを起動。
		startActivity(intent)
	}

	/**
	 * ロケーションリスナクラス。
	 */
	private inner class GPSLocationListener : LocationListener {
		override fun onLocationChanged(location: Location) {
			//引数のLocationオブジェクトから緯度を取得。
			_latitude = location.latitude
			//引数のLocationオブジェクトから経度を取得。
			_longitude = location.longitude
			//取得した緯度をTextViewに表示。
			val tvLatitude = findViewById<TextView>(R.id.tvLatitude)
			tvLatitude.text = _latitude.toString()
			//取得した経度をTextViewに表示。
			val tvLongitude = findViewById<TextView>(R.id.tvLongitude)
			tvLongitude.text = _longitude.toString()
		}

		override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

		override fun onProviderEnabled(provider: String) {}

		override fun onProviderDisabled(provider: String) {}
	}
}
