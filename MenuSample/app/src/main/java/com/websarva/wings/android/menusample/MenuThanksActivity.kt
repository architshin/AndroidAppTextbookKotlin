package com.websarva.wings.android.menusample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView

/**
 * 『Androidアプリ開発の教科書』
 * 第8章
 * メニューサンプル
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

		//アクションバーの［戻る］メニューを有効に設定。
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		//選択されたメニューが［戻る］の場合、アクティビティを終了。
		if(item.itemId == android.R.id.home) {
			finish()
		}
		//親クラスの同名メソッドを呼び出し、その戻り値を返却。
		return super.onOptionsItemSelected(item)
	}
}
