package com.starswap.codeforceswidgets

import android.util.Log
import com.beust.klaxon.Klaxon
import java.net.URL

data class SubmissionsResponse(val status: String, val result: List<Submission>? = null, val comment: String? = null)
data class Submission(val id: Int, val contestId: Int, val creationTimeSeconds: Long, val relativeTimeSeconds: Long, val problem: Problem, val author: Author, val programmingLanguage: String, val verdict: String, val testset: String, val passedTestCount: Int, val timeConsumedMillis: Int, val memoryConsumedBytes: Int)
data class Problem(val contestId: Int, val index: String, val name: String, val type: String, val rating: Int? = null, val tags: List<String>)
data class Author(val contestId: Int, val members: List<Member>, val participantType: String, val ghost: Boolean, val startTimeSeconds: Long? = null)
data class Member(val handle: String)

fun latest_submissions(handle: String): List<Submission>? {
    val responseText = URL("https://codeforces.com/api/user.status?handle=$handle&from=1&count=100").readText()
    Log.d("CodeforcesAPI", responseText)
    val result = Klaxon().parse<SubmissionsResponse>(responseText)?.result
    Log.d("CodeforcesAPI", "parsed response")
    return result
}