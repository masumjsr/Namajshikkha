package com.fsit.sohojnamaj.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.fsit.sohojnamaj.PrayerPreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class PrayerPreferencesSerializer @Inject constructor() : Serializer<PrayerPreferences>{
    override val defaultValue: PrayerPreferences = PrayerPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): PrayerPreferences =
        try{
            PrayerPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException){
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: PrayerPreferences, output: OutputStream) {
        t.writeTo(output)
    }
}