package com.websarva.wings.android.fragmentsample

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment

/**
 * 『Androidアプリ開発の教科書Kotlin』
 * 第9章
 * フラグメントサンプル
 *
 * 注文リストのフラグメントクラス。
 *
 * @author Shinzo SAITO
 */
class MenuListFragment : Fragment(R.layout.fragment_menu_list) {
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// 画面部品ListViewを取得
		val lvMenu = view.findViewById<ListView>(R.id.lvMenu)

		// SimpleAdapterで使用するMutableListオブジェクトを用意。
		val menuList: MutableList<MutableMap<String, String>> = mutableListOf()
		// 「から揚げ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
		var menu = mutableMapOf("name" to "から揚げ定食", "price" to "800円")
		menuList.add(menu)
		// 「ハンバーグ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
		menu = mutableMapOf("name" to "ハンバーグ定食", "price" to "850円")
		menuList.add(menu)
		// 以下データ登録の繰り返し。
		menu = mutableMapOf("name" to "生姜焼き定食", "price" to "850円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "ステーキ定食", "price" to "1000円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "野菜炒め定食", "price" to "750円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "とんかつ定食", "price" to "900円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "ミンチかつ定食", "price" to "850円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "チキンカツ定食", "price" to "900円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "コロッケ定食", "price" to "850円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "回鍋肉定食", "price" to "750円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "麻婆豆腐定食", "price" to "800円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "青椒肉絲定食", "price" to "900円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "八宝菜定食", "price" to "800円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "酢豚定食", "price" to "850円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "豚の角煮定食", "price" to "850円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "焼き鳥定食", "price" to "900円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "焼き魚定食", "price" to "850円")
		menuList.add(menu)
		menu = mutableMapOf("name" to "焼肉定食", "price" to "950円")
		menuList.add(menu)

		// SimpleAdapter第4引数from用データの用意。
		val from = arrayOf("name", "price")
		// SimpleAdapter第5引数to用データの用意。
		val to = intArrayOf(android.R.id.text1, android.R.id.text2)
		// SimpleAdapterを生成。
		val adapter = SimpleAdapter(activity, menuList, android.R.layout.simple_list_item_2, from, to)
		// アダプタの登録。
		lvMenu.adapter = adapter

		// リスナの登録。
		lvMenu.onItemClickListener = ListItemClickListener()
	}

	/**
	 * リストがタップされたときの処理が記述されたメンバクラス。
	 */
	private inner class ListItemClickListener : AdapterView.OnItemClickListener {
		override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
			// タップされた行のデータを取得。SimpleAdapterでは1行分のデータはMutableMap型!
			val item = parent.getItemAtPosition(position) as MutableMap<String, String>
			// 定食名と金額を取得。
			val menuName = item["name"]
			val menuPrice = item["price"]

			// 引き継ぎデータをまとめて格納できるBundleオブジェクト生成。
			val bundle = Bundle()
			// Bundleオブジェクトに引き継ぎデータを格納。
			bundle.putString("menuName", menuName)
			bundle.putString("menuPrice", menuPrice)

			// 自分が所属するアクティビティがnullじゃないなら…
			activity?.let {
				// フラグメントトランザクションの開始。
				val transaction = parentFragmentManager.beginTransaction()
				// フラグメントトランザクションが正しく動作するように設定。
				transaction.setReorderingAllowed(true)
				// 自分が所属するアクティビティからfragmentMainContainerを取得。
				val fragmentMainContainer = it.findViewById<View>(R.id.fragmentMainContainer)
				// fragmentMainContainerが存在するなら…
				if(fragmentMainContainer != null) {
					// 現在の表示内容をバックスタックに追加。
					transaction.addToBackStack("Only List")
					// fragmentMainContainerのフラグメントを注文完了フラグメントに置き換え。
					transaction.replace(R.id.fragmentMainContainer, MenuThanksFragment::class.java, bundle)
				}
				// fragmentMainContainerが存在しないなら…
				else {
					// fragmentThanksContainerのフラグメントを注文完了フラグメントに置き換え。
					transaction.replace(R.id.fragmentThanksContainer, MenuThanksFragment::class.java, bundle)
				}
				// フラグメントトランザクションのコミット。
				transaction.commit()
			}
		}
	}
}
