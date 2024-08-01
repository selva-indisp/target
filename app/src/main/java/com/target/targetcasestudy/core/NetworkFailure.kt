package com.target.targetcasestudy.core

sealed class NetworkFailure {
    object NoInternet: NetworkFailure()
    object ConnectionTimeOut: NetworkFailure()
    data class UnknownFailure(val error: Throwable): NetworkFailure()
}