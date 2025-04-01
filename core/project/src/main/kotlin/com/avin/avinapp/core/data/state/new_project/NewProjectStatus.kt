package com.avin.avinapp.core.data.state.new_project

sealed class NewProjectStatus {
    object Idle : NewProjectStatus()
    object Creating : NewProjectStatus()
    object AddGit : NewProjectStatus()
    object Completed : NewProjectStatus()
    data object Error : NewProjectStatus()
}

fun NewProjectStatus.isSuccess() = this is NewProjectStatus.Completed
fun NewProjectStatus.isError() = this is NewProjectStatus.Error
fun NewProjectStatus.isIdle() = this is NewProjectStatus.Idle
fun NewProjectStatus.isLoading() = !isIdle() && !isError() && !isSuccess()


inline fun NewProjectStatus.onLoading(callback: (status: NewProjectStatus) -> Unit): NewProjectStatus {
    return if (isLoading()) {
        callback(this)
        this
    } else this
}

inline fun NewProjectStatus.onSuccess(callback: () -> Unit): NewProjectStatus {
    return if (isSuccess()) {
        callback()
        this
    } else this
}

inline fun NewProjectStatus.onError(callback: () -> Unit): NewProjectStatus {
    return if (isError()) {
        callback()
        this
    } else this
}