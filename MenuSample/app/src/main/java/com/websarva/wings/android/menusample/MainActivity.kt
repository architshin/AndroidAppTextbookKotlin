package com.websarva.wings.android.menusample

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity

/**
 * 『Androidアプリ開発の教科書Kotlin』
 * 第8章
 * メニューサンプル
 *
 * メニューリスト画面のアクティビティクラス。
 *
 * @author Shinzo SAITO
 */
class MainActivity : AppCompatActivity() {
	/**
	 * リストビューに表示するリストデータ。
	 */
	private var _menuList: MutableList<MutableMap<String, Any>> = mutableListOf()
	/**
	 * SimpleAdapterの第4引数fromに使用するプロパティ。
	 */
	private val _from = arrayOf("name", "price")
	/**
	 * SimpleAdapterの第5引数toに使用するプロパティ。
	 */
	private val _to = intArrayOf(R.id.tvMenuName, R.id.tvMenuPrice)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// SimpleAdapterで使用する定食メニューListオブジェクトをprivateメソッドを利用して用意し、フィールドに格納。
		_menuList = createTeishokuList()
		// 画面部品ListViewを取得。
		val lvMenu = findViewById<ListView>(R.id.lvMenu)
		// SimpleAdapterを生成。
		val adapter = SimpleAdapter(this@MainActivity, _menuList, R.layout.row, _from, _to)
		// アダプタの登録。
		lvMenu.adapter = adapter
		// リストタップのリスナクラス登録。
		lvMenu.onItemClickListener = ListItemClickListener()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// オプションメニュー用xmlファイルをインフレイト。
		menuInflater.inflate(R.menu.menu_options_menu_list, menu)
		// trueをリターン。
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// 戻り値用の変数を初期値trueで用意。
		var returnVal = true
		// 選択されたメニューのIDのR値による処理の分岐。
		when(item.itemId) {
			// 定食メニューが選択された場合の処理。
			R.id.menuListOptionTeishoku ->
				// 定食メニューリストデータの生成。
				_menuList = createTeishokuList()
			// カレーメニューが選択された場合の処理。
			R.id.menuListOptionCurry ->
				// カレーメニューリストデータの生成。
				_menuList = createCurryList()
			//それ以外…
			else ->
				//親クラスの同名メソッドを呼び出し、その戻り値をreturnValとする。
				returnVal = super.onOptionsItemSelected(item)
		}
		// 画面部品ListViewを取得。
		val lvMenu = findViewById<ListView>(R.id.lvMenu)
		// SimpleAdapterを選択されたメニューデータで生成。
		val adapter = SimpleAdapter(applicationContext, _menuList, R.layout.row, _from, _to)
		// アダプタの登録。
		lvMenu.adapter = adapter
		return returnVal
	}

	/**
	 * リストビューに表示させる定食リストデータを生成するメソッド。
	 *
	 * @return 生成された定食リストデータ。
	 */
	private fun createTeishokuList(): MutableList<MutableMap<String, Any>> {
		// 定食メニューリスト用のListオブジェクトを用意。
		val menuList: MutableList<MutableMap<String, Any>> = mutableListOf()
		// 「から揚げ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
		var menu = mutableMapOf<String, Any>("name" to "から揚げ定食", "price" to 800, "desc" to "若鳥のから揚げにサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		// 「ハンバーグ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
		menu = mutableMapOf("name" to "ハンバーグ定食", "price" to 850, "desc" to "手ごねハンバーグにサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		// 以下データ登録の繰り返し。
		menu = mutableMapOf("name" to "生姜焼き定食", "price" to 850, "desc" to "すりおろし生姜を使った生姜焼きにサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "ステーキ定食", "price" to 1000, "desc" to "国産牛のステーキにサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "野菜炒め定食", "price" to 750, "desc" to "季節の野菜炒めにサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "とんかつ定食", "price" to 900, "desc" to "ロースとんかつにサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "ミンチかつ定食", "price" to 850, "desc" to "手ごねミンチカツにサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "チキンカツ定食", "price" to 900, "desc" to "ボリュームたっぷりチキンカツにサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "コロッケ定食", "price" to 850, "desc" to "北海道ポテトコロッケにサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "回鍋肉定食", "price" to 750, "desc" to "ピリ辛回鍋肉にサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "麻婆豆腐定食", "price" to 800, "desc" to "本格四川風麻婆豆腐にサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "青椒肉絲定食", "price" to 900, "desc" to "ピーマンの香り豊かな青椒肉絲にサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "焼き魚定食", "price" to 850, "desc" to "鰆の塩焼きにサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "焼肉定食", "price" to 950, "desc" to "特性たれの焼肉にサラダ、ご飯とお味噌汁が付きます。")
		menuList.add(menu)
		return menuList
	}

	/**
	 * リストビューに表示させるカレーリストデータを生成するメソッド。
	 *
	 * @return 生成されたカレーリストデータ。
	 */
	private fun createCurryList(): MutableList<MutableMap<String, Any>> {
		//カレーメニューリスト用のListオブジェクトを用意。
		val menuList: MutableList<MutableMap<String, Any>> = mutableListOf()
		//「ビーフカレー」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
		var menu = mutableMapOf<String, Any>("name" to "ビーフカレー", "price" to 520, "desc" to "特選スパイスをきかせた国産ビーフ100%のカレーです。")
		menuList.add(menu)
		//「ポークカレー」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
		menu = mutableMapOf("name" to "ポークカレー", "price" to 420, "desc" to "特選スパイスをきかせた国産ポーク100%のカレーです。")
		menuList.add(menu)
		//以下データ登録の繰り返し。
		menu = mutableMapOf("name" to "ハンバーグカレー", "price" to 620, "desc" to "特選スパイスをきかせたカレーに手ごねハンバーグをトッピングです。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "チーズカレー", "price" to 560, "desc" to "特選スパイスをきかせたカレーにとろけるチーズをトッピングです。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "カツカレー", "price" to 760, "desc" to "特選スパイスをきかせたカレーに国産ロースカツをトッピングです。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "ビーフカツカレー", "price" to 880, "desc" to "特選スパイスをきかせたカレーに国産ビーフカツをトッピングです。")
		menuList.add(menu)
		menu = mutableMapOf("name" to "からあげカレー", "price" to 540, "desc" to "特選スパイスをきかせたカレーに若鳥のから揚げをトッピングです。")
		menuList.add(menu)
		return menuList
	}

	/**
	 * リストがタップされたときの処理が記述されたメンバクラス。
	 */
	private inner class ListItemClickListener : AdapterView.OnItemClickListener {
		override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
			// タップされた行のデータを取得。SimpleAdapterでは1行分のデータはMutableMap型!
			val item = parent.getItemAtPosition(position) as MutableMap<String, Any>
			// 定食名と金額を取得。
			val menuName = item["name"] as String
			val menuPrice = item["price"] as Int
			// インテントオブジェクトを生成。
			val intent2MenuThanks = Intent(this@MainActivity, MenuThanksActivity::class.java)
			// 第2画面に送るデータを格納。
			intent2MenuThanks.putExtra("menuName", menuName)
			intent2MenuThanks.putExtra("menuPrice", "${menuPrice}円")
			// 第2画面の起動。
			startActivity(intent2MenuThanks)
		}
	}
}
