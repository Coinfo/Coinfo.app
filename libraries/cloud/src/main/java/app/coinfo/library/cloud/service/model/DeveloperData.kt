package app.coinfo.library.cloud.service.model

import com.google.gson.annotations.SerializedName

internal data class DeveloperData(
    @SerializedName("forks")
    val forks: Long?,
    @SerializedName("stars")
    val start: Long?,
    @SerializedName("subscribers")
    val subscribers: Long?,
    @SerializedName("total_issues")
    val totalIssues: Long?,
    @SerializedName("closed_issues")
    val closedIssues: Long?,
    @SerializedName("pull_requests_merged")
    val pullRequestsMerged: Long?,
    @SerializedName("pull_request_contributors")
    val pullRequestContributors: Long?,
    @SerializedName("commit_count_4_weeks")
    val commitsCount4Weeks: Long?,
)
