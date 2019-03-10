package com.websarva.wings.android.coordinatorlayoutsample

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar

/**
 * 『Androidアプリ開発の教科書』
 * 第16章
 * スロール連動サンプル
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
		//アクションバーにツールバーを設定。
		setSupportActionBar(toolbar)
		//CollapsingToolbarLayoutを取得。
		val toolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.toolbarLayout)
		//タイトルを設定。
		toolbarLayout.title = getString(R.string.toolbar_title)
		//通常サイズ時の文字色を設定。
		toolbarLayout.setExpandedTitleColor(Color.WHITE)
		//縮小サイズ時の文字色を設定。
		toolbarLayout.setCollapsedTitleTextColor(Color.LTGRAY)
	}
}
