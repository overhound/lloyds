package com.ot.playground.techtest.model.apidata


import com.google.gson.annotations.SerializedName

data class TemplateRepository(
    @SerializedName("allow_merge_commit")
    val allowMergeCommit: Boolean = false,
    @SerializedName("allow_rebase_merge")
    val allowRebaseMerge: Boolean = false,
    @SerializedName("allow_squash_merge")
    val allowSquashMerge: Boolean = false,
    @SerializedName("archive_url")
    val archiveUrl: String = "",
    @SerializedName("archived")
    val archived: Boolean = false,
    @SerializedName("assignees_url")
    val assigneesUrl: String = "",
    @SerializedName("blobs_url")
    val blobsUrl: String = "",
    @SerializedName("branches_url")
    val branchesUrl: String = "",
    @SerializedName("clone_url")
    val cloneUrl: String = "",
    @SerializedName("collaborators_url")
    val collaboratorsUrl: String = "",
    @SerializedName("comments_url")
    val commentsUrl: String = "",
    @SerializedName("commits_url")
    val commitsUrl: String = "",
    @SerializedName("compare_url")
    val compareUrl: String = "",
    @SerializedName("contents_url")
    val contentsUrl: String = "",
    @SerializedName("contributors_url")
    val contributorsUrl: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("default_branch")
    val defaultBranch: String = "",
    @SerializedName("delete_branch_on_merge")
    val deleteBranchOnMerge: Boolean = false,
    @SerializedName("deployments_url")
    val deploymentsUrl: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("disabled")
    val disabled: Boolean = false,
    @SerializedName("downloads_url")
    val downloadsUrl: String = "",
    @SerializedName("events_url")
    val eventsUrl: String = "",
    @SerializedName("fork")
    val fork: Boolean = false,
    @SerializedName("forks")
    val forks: Int = 0,
    @SerializedName("forks_count")
    val forksCount: Int = 0,
    @SerializedName("forks_url")
    val forksUrl: String = "",
    @SerializedName("full_name")
    val fullName: String = "",
    @SerializedName("git_commits_url")
    val gitCommitsUrl: String = "",
    @SerializedName("git_refs_url")
    val gitRefsUrl: String = "",
    @SerializedName("git_tags_url")
    val gitTagsUrl: String = "",
    @SerializedName("git_url")
    val gitUrl: String = "",
    @SerializedName("has_downloads")
    val hasDownloads: Boolean = false,
    @SerializedName("has_issues")
    val hasIssues: Boolean = false,
    @SerializedName("has_pages")
    val hasPages: Boolean = false,
    @SerializedName("has_projects")
    val hasProjects: Boolean = false,
    @SerializedName("has_wiki")
    val hasWiki: Boolean = false,
    @SerializedName("hompeage")
    val homepage: String = "",
    @SerializedName("hooks_url")
    val hooksUrl: String = "",
    @SerializedName("html_url")
    val htmlUrl: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("is_template")
    val isTemplate: Boolean = false,
    @SerializedName("issue_comment_url")
    val issueCommentUrl: String = "",
    @SerializedName("issue_events_url")
    val issueEventsUrl: String = "",
    @SerializedName("issues_url")
    val issuesUrl: String = "",
    @SerializedName("keys_url")
    val keysUrl: String = "",
    @SerializedName("labels_url")
    val labelsUrl: String = "",
    @SerializedName("language")
    val language: Any? = Any(),
    @SerializedName("languages_url")
    val languagesUrl: String = "",
    @SerializedName("license")
    val license: License = License(),
    @SerializedName("merges_url")
    val mergesUrl: String = "",
    @SerializedName("milestones_url")
    val milestonesUrl: String = "",
    @SerializedName("mirror_url")
    val mirrorUrl: String = "",
    val name: String = "",
    @SerializedName("network_count")
    val networkCount: Int = 0,
    @SerializedName("node_id")
    val nodeId: String = "",
    @SerializedName("notifications_url")
    val notificationsUrl: String = "",
    @SerializedName("open_issues")
    val openIssues: Int = 0,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int = 0,
    @SerializedName("owner")
    val owner: OwnerX = OwnerX(),
    @SerializedName("permissions")
    val permissions: PermissionsX = PermissionsX(),
    @SerializedName("private")
    val `private`: Boolean = false,
    @SerializedName("pulls_url")
    val pullsUrl: String = "",
    @SerializedName("pushed_at")
    val pushedAt: String = "",
    @SerializedName("releases_url")
    val releasesUrl: String = "",
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("ssh_url")
    val sshUrl: String = "",
    @SerializedName("stargazers_count")
    val stargazersCount: Int = 0,
    @SerializedName("stargazers_url")
    val stargazersUrl: String = "",
    @SerializedName("statuses_url")
    val statusesUrl: String = "",
    @SerializedName("subscribers_count")
    val subscribersCount: Int = 0,
    @SerializedName("subscribers_url")
    val subscribersUrl: String = "",
    @SerializedName("subscription_url")
    val subscriptionUrl: String = "",
    @SerializedName("svn_url")
    val svnUrl: String = "",
    @SerializedName("tags_url")
    val tagsUrl: String = "",
    @SerializedName("teams_url")
    val teamsUrl: String = "",
    @SerializedName("temp_clone_token")
    val tempCloneToken: String = "",
    val topics: List<String> = listOf(),
    @SerializedName("trees_url")
    val treesUrl: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("visibility")
    val visibility: String = "",
    @SerializedName("watchers")
    val watchers: Int = 0,
    @SerializedName("watchers_count")
    val watchersCount: Int = 0
)