package com.websarva.wings.android.toolbarsample

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar

/**
 * 『Androidアプリ開発の教科書』
 * 第16章
 * ツールバーサンプル
 *
 * アクティビティクラス。
 *
 * @author Shinzo SAITO
 */
class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		//Toolbarを取得。
		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		//ツールバーにロゴを設定。
		toolbar.setLogo(R.mipmap.ic_launcher)
		//ツールバーにタイトル文字列を設定。
		toolbar.setTitle(R.string.toolbar_title)
		//ツールバーのタイトル文字色を設定。
		toolbar.setTitleTextColor(Color.WHITE)
		//ツールバーのサブタイトル文字列を設定。
		toolbar.setSubtitle(R.string.toolbar_subtitle)
		//ツールバーのサブタイトル文字色を設定。
		toolbar.setSubtitleTextColor(Color.LTGRAY)
		//アクションバーにツールバーを設定。
		setSupportActionBar(toolbar)
	}
}
