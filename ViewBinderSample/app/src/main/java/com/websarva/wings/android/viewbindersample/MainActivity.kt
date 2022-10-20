package com.websarva.wings.android.viewbindersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// SimpleAdapterで使用する名前Listオブジェクトをprivateメソッドを利用して用意。
		val nameList = createNameList()
		// SimpleAdapterの第4引数fromに使用する変数を用意。
		val from = arrayOf("name", "sex")
		// SimpleAdapterの第5引数toに使用する変数を用意。
		val to = intArrayOf(R.id.tvName, R.id.imSex)
		// SimpleAdapterを生成。
		val adapter = SimpleAdapter(this@MainActivity, nameList, R.layout.row, from, to)
		// カスタムビューバインダを登録。
		adapter.viewBinder = CustomViewBinder()
		// 画面部品ListViewを取得。
		val lvPhones = findViewById<ListView>(R.id.lvNameList)
		// アダプタの登録。
		lvPhones.adapter = adapter
	}

	/**
	 * リストビューに表示させる名前リストデータを生成するメソッド。
	 *
	 * @return 生成された名前リストデータ。
	 */
	private fun createNameList(): MutableList<MutableMap<String, Any>> {
		// 名前リスト用のListオブジェクトを用意。
		val nameList = mutableListOf<MutableMap<String, Any>>()

		// 一人目のデータを格納するMapオブジェクトの用意とnameListへのデータ登録。
		var person = mutableMapOf<String, Any>("name" to "田中一郎", "sex" to 1)
		nameList.add(person)

		// 二人目のデータを格納するMapオブジェクトの用意とnameListへのデータ登録。
		person = mutableMapOf("name" to "江藤香織", "sex" to 0)
		nameList.add(person)

		// 以下データ登録の繰り返し。
		person = mutableMapOf("name" to "中山裕子", "sex" to 0)
		nameList.add(person)
		person = mutableMapOf("name" to "中谷源蔵", "sex" to 1)
		nameList.add(person)
		person = mutableMapOf("name" to "山下直美", "sex" to 0)
		nameList.add(person)
		person = mutableMapOf("name" to "鈴木翔太", "sex" to 1)
		nameList.add(person)
		person = mutableMapOf("name" to "石橋信二", "sex" to 1)
		nameList.add(person)
		person = mutableMapOf("name" to "杉本孝典", "sex" to 1)
		nameList.add(person)
		person = mutableMapOf("name" to "牧野知子", "sex" to 0)
		nameList.add(person)
		person = mutableMapOf("name" to "三上春香", "sex" to 0)
		nameList.add(person)
		person = mutableMapOf("name" to "大野弘明", "sex" to 1)
		nameList.add(person)
		person = mutableMapOf("name" to "西口健太", "sex" to 1)
		nameList.add(person)
		person = mutableMapOf("name" to "西野明美", "sex" to 0)
		nameList.add(person)

		return nameList
	}

	/**
	 * リストビューのカスタムビューバインダクラス。
	 */
	private inner class CustomViewBinder : SimpleAdapter.ViewBinder {
		override fun setViewValue(view: View, data: Any, textRepresentation: String): Boolean {
			/*
			 * 引数のviewはリスト1行内でデータを割り当てる画面部品。
			 * dataはそれに割り当てるデータ。
			 * textRepresentationはdataを文字列に変換したデータ。
			 *
			 * viewとdataの組合わせはfromとtoの組合せそのもの。
			 */

			// idのR値に応じて分岐。
			when(view.id) {
				// 名前を表示するTextViewなら…
				R.id.tvName -> {
					// TextViewにキャストして、表示データ(名前)をセットする。
					val tvName = view as TextView
					tvName.text = textRepresentation
					return true
				}
				// 性別アイコンを表示するImageViewなら…
				R.id.imSex -> {
					// ImageViewにキャスト。
					val imPhoneType = view as ImageView
					// 表示データを整数型にキャスト。
					val sex = data as Int
					// 表示データに応じて処理を分岐。
					when(sex) {
						// 女性なら…
						0 ->
							// 女性アイコンをセット。
							imPhoneType.setImageResource(R.drawable.ic_female)
						// 男性なら…
						1 ->
							// 男性アイコンをセット。
							imPhoneType.setImageResource(R.drawable.ic_male)
					}
					return true
				}
			}
			return false
		}
	}
}
