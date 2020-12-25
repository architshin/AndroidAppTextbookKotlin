package com.websarva.wings.android.fragmentsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * 『Androidアプリ開発の教科書Kotlin』
 * 第9章
 * フラグメントサンプル
 *
 * 注文完了画面のアクティビティクラス。
 *
 * @author Shinzo SAITO
 */
class MenuThanksActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_menu_thanks)
	}
}
