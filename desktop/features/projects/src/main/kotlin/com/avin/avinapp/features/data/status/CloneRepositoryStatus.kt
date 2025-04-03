package com.avin.avinapp.features.data.status

sealed class CloneRepositoryStatus {
    data object Idle : CloneRepositoryStatus()
    data class Loading(val title: String = "", val totalWorks: Int = 0, val currentWork: Int = 0) :
        CloneRepositoryStatus()

    data object Completed : CloneRepositoryStatus()
}


inline fun CloneRepositoryStatus.onLoading(callback: (status: CloneRepositoryStatus.Loading) -> Unit): CloneRepositoryStatus {
    return if (this is CloneRepositoryStatus.Loading) {
        callback(this)
        this
    } else this
}

inline fun CloneRepositoryStatus.onSuccess(callback: () -> Unit): CloneRepositoryStatus {
    return if (this is CloneRepositoryStatus.Completed) {
        callback()
        this
    } else this
}