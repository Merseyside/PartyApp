package com.merseyside.partyapp.di

import com.merseyside.partyapp.utils.ContentResolver
import com.squareup.sqldelight.db.SqlDriver

actual var sqlDriver: SqlDriver? = null

actual var baseContentResolver: ContentResolver? = null