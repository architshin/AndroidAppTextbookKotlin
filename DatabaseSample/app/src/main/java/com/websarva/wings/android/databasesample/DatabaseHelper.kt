package com.websarva.wings.android.databasesample

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * 『Androidアプリ開発の教科書Kotlin』
 * 第10章
 * データベースサンプル
 *
 * データベースヘルパークラス。
 *
 * @author Shinzo SAITO
 */
class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
	// クラス内のprivate定数を宣言するためにcompanion objectブロックとする。
	companion object {
		/**
		 * データベースファイル名の定数フィールド。
		 */
		private const val DATABASE_NAME = "cocktailmemo.db"
		/**
		 * バージョン情報の定数フィールド。
		 */
		private const val DATABASE_VERSION = 1
	}

	override fun onCreate(db: SQLiteDatabase) {
		// テーブル作成用SQL文字列の作成。
		val sb = StringBuilder()
		sb.append("CREATE TABLE cocktailmemos (")
		sb.append("_id INTEGER PRIMARY KEY,")
		sb.append("name TEXT,")
		sb.append("note TEXT")
		sb.append(");")
		val sql = sb.toString()

		// SQLの実行。
		db.execSQL(sql)
	}

	override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}
