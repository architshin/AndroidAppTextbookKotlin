package com.websarva.wings.android.asynchandlercallsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.core.os.HandlerCompat
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.Callable
import java.util.concurrent.Executors

/**
 * 『Androidアプリ開発の教科書Kotlin』
 * 第11章
 * Web API連携サンプル Handler+Callable版
 *
 * アクティビティクラス。
 *
 * @author Shinzo SAITO
 */
class MainActivity : AppCompatActivity() {
	// クラス内のprivate定数を宣言するためにcompanion objectブロックとする。
	companion object {
		/**
		 * ログに記載するタグ用の文字列。
		 */
		private const val DEBUG_TAG = "AsyncSample"
		/**
		 * お天気情報のURL。
		 */
		private const val WEATHERINFO_URL = "https://api.openweathermap.org/data/2.5/weather?lang=ja"
		/**
		 * お天気APIにアクセスすするためのAPI Key。
		 * ※※※※※この値は各自のものに書き換える!!※※※※※
		 */
		private const val APP_ID = "xxxxxxxxxxx"
	}

	/**
	 * リストビューに表示させるリストデータ。
	 */
	private var _list: MutableList<MutableMap<String, String>> = mutableListOf()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		_list = createList()

		val lvCityList = findViewById<ListView>(R.id.lvCityList)
		val from  = arrayOf("name")
		val to = intArrayOf(android.R.id.text1)
		val adapter = SimpleAdapter(this@MainActivity, _list, android.R.layout.simple_list_item_1, from, to)
		lvCityList.adapter = adapter
		lvCityList.onItemClickListener = ListItemClickListener()
	}

	/**
	 * リストビューに表示させる天気ポイントリストデータを生成するメソッド。
	 *
	 * @return 生成された天気ポイントリストデータ。
	 */
	private fun createList(): MutableList<MutableMap<String, String>> {
		var list: MutableList<MutableMap<String, String>> = mutableListOf()

		var city = mutableMapOf("name" to "大阪", "q" to "Osaka")
		list.add(city)
		city = mutableMapOf("name" to "神戸", "q" to "Kobe")
		list.add(city)
		city = mutableMapOf("name" to "京都", "q" to "Kyoto")
		list.add(city)
		city = mutableMapOf("name" to "大津", "q" to "Otsu")
		list.add(city)
		city = mutableMapOf("name" to "奈良", "q" to "Nara")
		list.add(city)
		city = mutableMapOf("name" to "和歌山", "q" to "Wakayama")
		list.add(city)
		city = mutableMapOf("name" to "姫路", "q" to "Himeji")
		list.add(city)

		return list
	}

	/**
	 * 処理経過を表示するためにTextViewに引数のメッセージ文字列を追記するメソッド。
	 *
	 * @param msg メッセージ文字列。
	 */
	@UiThread
	private fun addMsg(msg: String) {
		// tvWeatherDescのTextViewを取得。
		val tvWeatherDesc = findViewById<TextView>(R.id.tvWeatherDesc)
		// 現在表示されているメッセージを取得。
		var msgNow = tvWeatherDesc.text
		// 現在表示されているメッセージが空でなければ、改行を追加。
		if(!msgNow.equals("")) {
			msgNow = "${msgNow}\n"
		}
		// 引数のメッセージを追加。
		msgNow = "${msgNow}${msg}"
		// 追加されたメッセージをTextViewに表示。
		tvWeatherDesc.text = msgNow
	}

	/**
	 * お天気情報の取得処理を行うメソッド。
	 *
	 * @param url お天気情報を取得するURL。
	 */
	@UiThread
	private fun receiveWeatherInfo(urlFull: String) {
		// 天気情報表示TextView内の表示文字列をクリア。
		val tvWeatherTelop = findViewById<TextView>(R.id.tvWeatherTelop)
		tvWeatherTelop.text = ""
		val tvWeatherDesc = findViewById<TextView>(R.id.tvWeatherDesc)
		tvWeatherDesc.text = ""

		// Handlerオブジェクトの取得。
		val handler = HandlerCompat.createAsync(mainLooper)
		val backgroundReceiver = WeatherInfoBackgroundReceiver(handler, urlFull)
		val executeService = Executors.newSingleThreadExecutor()
		val future = executeService.submit(backgroundReceiver)
		val result = future.get()
		showWeatherInfo(result)
	}

	/**
	 * 取得したお天気情報JSON文字列を解析の上、画面に表示させるメソッド。
	 *
	 * @param result 取得したお天気情報JSON文字列。
	 */
	@UiThread
	private fun showWeatherInfo(result: String) {
		// ルートJSONオブジェクトを生成。
		val rootJSON = JSONObject(result)
		// 都市名文字列を取得。
		val cityName = rootJSON.getString("name")
		// 緯度経度情報JSONオブジェクトを取得。
		val coordJSON = rootJSON.getJSONObject("coord")
		// 緯度情報文字列を取得。
		val latitude = coordJSON.getString("lat")
		// 経度情報文字列を取得。
		val longitude = coordJSON.getString("lon")
		// 天気情報JSON配列オブジェクトを取得。
		val weatherJSONArray = rootJSON.getJSONArray("weather")
		// 現在の天気情報JSONオブジェクトを取得。
		val weatherJSON = weatherJSONArray.getJSONObject(0)
		// 現在の天気情報文字列を取得。
		val weather = weatherJSON.getString("description")
		// 画面に表示する「〇〇の天気」文字列を生成。
		val telop = "${cityName}の天気"
		// 天気の詳細情報を表示する文字列を生成。
		val desc = "現在は${weather}です。\n緯度は${latitude}度で経度は${longitude}度です。"
		// 天気情報を表示するTextViewを取得。
		val tvWeatherTelop = findViewById<TextView>(R.id.tvWeatherTelop)
		val tvWeatherDesc = findViewById<TextView>(R.id.tvWeatherDesc)
		// 天気情報を表示。
		tvWeatherTelop.text = telop
		tvWeatherDesc.text = desc
	}

	/**
	 * 非同期でお天気情報APIにアクセスするためのクラス。
	 *
	 * @param handler ハンドラオブジェクト。
	 * @param url お天気情報を取得するURL。
	 */
	private inner class WeatherInfoBackgroundReceiver(handler: Handler, url: String): Callable<String> {
		/**
		 * ハンドラオブジェクト。
		 */
		private val _handler = handler
		/**
		 * お天気情報を取得するURL。
		 */
		private val _url = url

		@WorkerThread
		override fun call(): String {
			// 途中経過表示。
			var progressUpdate = ProgressUpdateExecutor("バックグランド処理開始。")
			_handler.post(progressUpdate)

			// 天気情報サービスから取得したJSON文字列。天気情報が格納されている。
			var result = ""
			// URLオブジェクトを生成。
			val url = URL(_url)
			// URLオブジェクトからHttpURLConnectionオブジェクトを取得。
			val con = url.openConnection() as HttpURLConnection
			// 接続に使ってもよい時間を設定。
			con.connectTimeout = 1000
			// データ取得に使ってもよい時間。
			con.readTimeout = 1000
			// HTTP接続メソッドをGETに設定。
			con.requestMethod = "GET"
			try {
				// 途中経過表示。
				progressUpdate = ProgressUpdateExecutor("Webアクセス開始。")
				_handler.post(progressUpdate)

				// 接続。
				con.connect()
				// HttpURLConnectionオブジェクトからレスポンスデータを取得。
				val stream = con.inputStream
				// レスポンスデータであるInputStreamオブジェクトを文字列に変換。
				result = is2String(stream)
				// InputStreamオブジェクトを解放。
				stream.close()

				// 途中経過表示。
				progressUpdate = ProgressUpdateExecutor("Webアクセス終了。")
				_handler.post(progressUpdate)
			}
			catch(ex: SocketTimeoutException) {
				Log.w(DEBUG_TAG, "通信タイムアウト", ex)
			}
			// HttpURLConnectionオブジェクトを解放。
			con.disconnect()

			// 途中経過表示。
			progressUpdate = ProgressUpdateExecutor("バックグランド処理終了。")
			_handler.post(progressUpdate)

			return result
		}

		/**
		 * InputStreamオブジェクトを文字列に変換するメソッド。 変換文字コードはUTF-8。
		 *
		 * @param stream 変換対象のInputStreamオブジェクト。
		 * @return 変換された文字列。
		 */
		private fun is2String(stream: InputStream): String {
			val sb = StringBuilder()
			val reader = BufferedReader(InputStreamReader(stream, StandardCharsets.UTF_8))
			var line = reader.readLine()
			while(line != null) {
				sb.append(line)
				line = reader.readLine()
			}
			reader.close()
			return sb.toString()
		}
	}

	/**
	 * バックグラウンドスレッドの途中経過メッセージをUIスレッドで表示する処理用クラス。
	 *
	 * @param msg  追加メッセージを表す文字列。
	 */
	private inner class ProgressUpdateExecutor(msg: String): Runnable {
		/**
		 * 追加メッセージを表す文字列。
		 */
		private val _msg = msg

		@UiThread
		override fun run() {
			addMsg(_msg)
		}
	}

	/**
	 * リストがタップされた時の処理が記述されたリスナクラス。
	 */
	private inner class ListItemClickListener: AdapterView.OnItemClickListener {
		override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
			val item = _list.get(position)
			val q = item.get("q")
			q?.let {
				val urlFull = "$WEATHERINFO_URL&q=$q&appid=$APP_ID"
				receiveWeatherInfo(urlFull)
			}
		}
	}
}
