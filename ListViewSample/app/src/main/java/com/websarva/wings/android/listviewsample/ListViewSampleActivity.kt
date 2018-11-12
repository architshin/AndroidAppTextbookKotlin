package com.websarva.wings.android.listviewsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast

/**
 * 『Androidアプリ開発の教科書』
 * 第5章
 * リスト選択サンプル
 *
 * メインアクティビティクラス。
 *
 * @author Shinzo SAITO
 */
class ListViewSampleActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_list_view_sample)

		//ListViewオブジェクトを取得。
		val lvMenu = findViewById<ListView>(R.id.lvMenu)
		//ListViewにリスナを設定。
		lvMenu.onItemClickListener = ListItemClickListener()
	}

	/**
	 * リストがタップされたときの処理が記述されたメンバクラス。
	 */
	private inner class ListItemClickListener : AdapterView.OnItemClickListener {
		override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
			//タップされた定食名を取得。
			val item = parent.getItemAtPosition(position) as String
			//トーストで表示する文字列を生成。
			val show = "あなたが選んだ定食: " + item
			//トーストの表示。
			Toast.makeText(applicationContext, show, Toast.LENGTH_LONG).show()
		}
	}
}
