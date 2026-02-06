package com.merseyside.partyapp.di

import android.annotation.SuppressLint
import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import com.merseyside.partyapp.utils.ContentResolver

@SuppressLint("StaticFieldLeak")
actual var mContext: Context? = null

actual var sqlDriver: SqlDriver? = null

actual var baseContentResolver: ContentResolver? = null