package com.example.healthapp.utils


class SystemTimeProvider: TimeProvider {
    override fun now() = System.currentTimeMillis()
}
