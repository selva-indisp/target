package com.target.deal.core

sealed class NetworkFailure {
    object NoInternet: com.target.deal.core.NetworkFailure()
    object ConnectionTimeOut: com.target.deal.core.NetworkFailure()
    data class UnknownFailure(val error: Throwable): com.target.deal.core.NetworkFailure()
}