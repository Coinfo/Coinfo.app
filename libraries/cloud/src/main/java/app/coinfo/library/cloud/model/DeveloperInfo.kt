package app.coinfo.library.cloud.model

data class DeveloperInfo(
    val forks: Long?,
    val stars: Long?,
    val subscribers: Long?,
    val issues: Long?,
    val closedIssues: Long?,
    val pullRequestsMerged: Long?
)
