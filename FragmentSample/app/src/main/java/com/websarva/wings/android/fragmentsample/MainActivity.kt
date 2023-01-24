package com.websarva.wings.android.fragmentsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * 『Androidアプリ開発の教科書Kotlin』
 * 第9章
 * フラグメントサンプル
 *
 * メインアクティビティクラス。
 *
 * @author Shinzo SAITO
 */
class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}
}
