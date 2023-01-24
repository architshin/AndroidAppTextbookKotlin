package com.websarva.wings.android.webviewsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter

class MainActivity : AppCompatActivity() {
	private var _list: MutableList<MutableMap<String, String>> = mutableListOf()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		_list = createList()

		val lvSiteList = findViewById<ListView>(R.id.lvSiteList)
		val from = arrayOf("name", "url")
		val to = intArrayOf(android.R.id.text1, android.R.id.text2)
		val adapter = SimpleAdapter(this@MainActivity, _list, android.R.layout.simple_list_item_2, from, to)
		lvSiteList.adapter = adapter
		lvSiteList.onItemClickListener = ListItemClickListener()
	}

	private fun createList(): MutableList<MutableMap<String, String>> {
		val list: MutableList<MutableMap<String, String>> = mutableListOf()
		var map: MutableMap<String, String> = mutableMapOf("name" to "CodeZine", "url" to "https://codezine.jp/")
		list.add(map)
		map = mutableMapOf("name" to "EnterpriseZine", "url" to "https://www.enterprisezine.jp/")
		list.add(map)
		map = mutableMapOf("name" to "MarkeZine", "url" to "https://markezine.jp/")
		list.add(map)
		map = mutableMapOf("name" to "ECzine", "url" to "https://eczine.jp/")
		list.add(map)
		return list
	}

	private inner class ListItemClickListener : AdapterView.OnItemClickListener {
		override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
			val item = _list.get(position)
			val url = item.get("url")
			val wvSite = findViewById<WebView>(R.id.wvSite)
			val webSettings = wvSite.settings
			webSettings.javaScriptEnabled = true
			wvSite.stopLoading()
			wvSite.webChromeClient = WebChromeClient()
			wvSite.loadUrl(url!!)
		}
	}
}
