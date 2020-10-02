package com.test.shaadoow.util

data class NetworkState constructor(
    val status: Status,
    val msg: String? = null) {
    // Enums to monitor Network state
    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }
}