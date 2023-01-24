package com.websarva.wings.android.implicitintentsample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import java.net.URLEncoder

/**
 * 『Androidアプリ開発の教科書Kotlin』
 * 第14章
 * 暗黙的インテントサンプル
 *
 * アクティビティクラス。
 *
 * @author Shinzo SAITO
 */
class MainActivity : AppCompatActivity() {
	/**
	 * 緯度プロパティ。
	 */
	private var _latitude = 0.0
	/**
	 * 経度プロパティ。
	 */
	private var _longitude = 0.0
	/**
	 * FusedLocationProviderClientオブジェクトプロパティ。
	 */
	private lateinit var _fusedLocationClient: FusedLocationProviderClient
	/**
	 * LocationRequestオブジェクトプロパティ。
	 */
	private lateinit var _locationRequest: LocationRequest
	/**
	 * 位置情報が変更された時の処理を行うコールバックオブジェクトプロパティ。
	 */
	private lateinit var _onUpdateLocation: OnUpdateLocation

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// FusedLocationProviderClientオブジェクトを取得。
		_fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
		// LocationRequestのビルダーオブジェクトを生成。
		val builder = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
		// LocationRequestオブジェクトを生成。
		_locationRequest = builder.build()
		// 位置情報が変更された時の処理を行うコールバックオブジェクトを生成。
		_onUpdateLocation = OnUpdateLocation()
	}

	override fun onResume() {
		super.onResume()

		// ACCESS_FINE_LOCATIONとACCESS_COARSE_LOCATIONの許可が下りていないなら…
		if (ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// 許可をACCESS_FINE_LOCATIONとACCESS_COARSE_LOCATIONに設定。
			val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
			// 許可を求めるダイアログを表示。その際、リクエストコードを1000に設定。
			ActivityCompat.requestPermissions(this@MainActivity, permissions, 1000)
			// onResume()メソッドを終了。
			return
		}
		// 位置情報の追跡を開始。
		_fusedLocationClient.requestLocationUpdates(_locationRequest, _onUpdateLocation, mainLooper)
	}

	override fun onPause() {
		super.onPause()

		// 位置情報の追跡を停止。
		_fusedLocationClient.removeLocationUpdates(_onUpdateLocation)
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		// 位置情報のパーミションダイアログでかつ許可を選択したなら…
		if(requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
			// 再度許可が下りていないかどうかのチェックをし、降りていないなら処理を中止。
			if(ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				return
			}
			// 位置情報の追跡を開始。
			_fusedLocationClient.requestLocationUpdates(_locationRequest, _onUpdateLocation, mainLooper)
		}
	}

	/**
	 * 地図検索ボタンがタップされたときの処理メソッド。
	 */
	fun onMapSearchButtonClick(view: View) {
		// 入力欄に入力されたキーワード文字列を取得。
		val etSearchWord = findViewById<EditText>(R.id.etSearchWord)
		var searchWord = etSearchWord.text.toString()
		// 入力されたキーワードをURLエンコード。
		searchWord = URLEncoder.encode(searchWord, "UTF-8")
		// マップアプリと連携するURI文字列を生成。
		val uriStr = "geo:0,0?q=${searchWord}"
		// URI文字列からURIオブジェクトを生成。
		val uri = Uri.parse(uriStr)
		// Intentオブジェクトを生成。
		val intent = Intent(Intent.ACTION_VIEW, uri)
		// アクティビティを起動。
		startActivity(intent)
	}

	/**
	 * 現在地の地図表示ボタンがタップされたときの処理メソッド。
	 */
	fun onMapShowCurrentButtonClick(view: View) {
		// プロパティの緯度と経度の値をもとにマップアプリと連携するURI文字列を生成。
		val uriStr = "geo:${_latitude},${_longitude}"
		// URI文字列からURIオブジェクトを生成。
		val uri = Uri.parse(uriStr)
		// Intentオブジェクトを生成。
		val intent = Intent(Intent.ACTION_VIEW, uri)
		// アクティビティを起動。
		startActivity(intent)
	}

	/**
	 * 位置情報が変更された時の処理を行うコールバッククラス。
	 */
	private inner class OnUpdateLocation : LocationCallback() {
		override fun onLocationResult(locationResult: LocationResult) {
			// 直近の位置情報を取得。
			val location = locationResult.lastLocation
			location?.let {
				// locationオブジェクトから緯度を取得。
				_latitude = it.latitude
				// locationオブジェクトから経度を取得。
				_longitude = it.longitude
				// 取得した緯度をTextViewに表示。
				val tvLatitude = findViewById<TextView>(R.id.tvLatitude)
				tvLatitude.text = _latitude.toString()
				// 取得した経度をTextViewに表示。
				val tvLongitude = findViewById<TextView>(R.id.tvLongitude)
				tvLongitude.text = _longitude.toString()
			}
		}
	}
}
