package com.websarva.wings.android.intentsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

/**
 * 『Androidアプリ開発の教科書』
 * 第7章
 * 画面遷移サンプル
 *
 * 注文完了画面のアクティビティクラス。
 *
 * @author Shinzo SAITO
 */
class MenuThanksActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_menu_thanks)

		//リスト画面から渡されたデータを取得。
		val menuName = intent.getStringExtra("menuName")
		val menuPrice = intent.getStringExtra("menuPrice")

		val price = intent.getIntExtra("price", 0)

		//定食名と金額を表示させるTextViewを取得。
		val tvMenuName = findViewById<TextView>(R.id.tvMenuName)
		val tvMenuPrice = findViewById<TextView>(R.id.tvMenuPrice)

		//TextViewに定食名と金額を表示。
		tvMenuName.text = menuName
		tvMenuPrice.text = menuPrice
	}

	/**
	 * 戻るボタンをタップした時の処理。
	 * @param view 画面部品。
	 */
	fun onBackButtonClick(view: View) {
		finish()
	}
}
