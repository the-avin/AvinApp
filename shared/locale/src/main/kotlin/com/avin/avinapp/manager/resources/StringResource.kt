package com.avin.avinapp.manager.resources

import com.avin.avinapp.locale.StringRes
import com.avin.avinapp.platform.Platform

object StringResource {
    val appName = StringRes("app_name")
    val welcome = StringRes("welcome")
    val newProject = StringRes("new_project")
    val creatingNewProject = StringRes("creating_new_project")
    val newProjectMessage = StringRes("new_project_message")
    val cloneProjectMessage = StringRes("clone_project_message")
    val settings = StringRes("settings")
    val system = StringRes("system")
    val dark = StringRes("dark")
    val light = StringRes("light")
    val general = StringRes("general")
    val open = StringRes("open")
    val cloneRepository = StringRes("clone_repository")
    val name = StringRes("name")
    val path = StringRes("path")
    val theme = StringRes("theme")
    val url = StringRes("url")
    val openInFolder: StringRes
        get() = when {
            Platform.isMac() -> StringRes("open_in_finder")
            Platform.isLinux() -> StringRes("open_in_file_manager")
            else -> StringRes("open_in_explorer")
        }
    val deleteProjectFromList = StringRes("delete_project_from_list")
    val finish = StringRes("finish")
    val cancel = StringRes("cancel")
    val existsFileMessage = StringRes("exists_file_message")
    val existsProjectMessage = StringRes("exists_project_message")
    val emptyFieldsMessage = StringRes("empty_fields_message")
    val addToGit = StringRes("add_to_git")
    val addingToGit = StringRes("adding_to_git")
    val creatingFiles = StringRes("creating_files")
}