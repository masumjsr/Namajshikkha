package com.fsit.sohojnamaj.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: Dispatchers)

enum class Dispatchers {
    IO
}