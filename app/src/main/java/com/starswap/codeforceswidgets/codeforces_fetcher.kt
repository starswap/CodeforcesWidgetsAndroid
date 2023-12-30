package com.starswap.codeforceswidgets

import android.util.Log
import com.beust.klaxon.Klaxon
import java.net.URL

data class SubmissionsResponse(val status: String, val result: List<Submission>? = null, val comment: String? = null)
data class Submission(val id: Int, val contestId: Int, val creationTimeSeconds: Long, val relativeTimeSeconds: Long, val problem: Problem, val author: Author, val programmingLanguage: String, val verdict: String, val testset: String, val passedTestCount: Int, val timeConsumedMillis: Int, val memoryConsumedBytes: Int)
data class Problem(val contestId: Int, val index: String, val name: String, val type: String, val rating: Int? = null, val tags: List<String>)
data class Author(val contestId: Int, val members: List<Member>, val participantType: String, val ghost: Boolean, val startTimeSeconds: Long? = null)
data class Member(val handle: String)

data class UsersResponse(val status: String, val result: List<User>? = null, val comment: String? = null)
data class User(val rating: Int? = null)


fun latest_submissions(handle: String): List<Submission>? {
    val responseText = URL("https://codeforces.com/api/user.status?handle=$handle&from=1&count=100").readText()
    Log.d("CodeforcesAPI", responseText)
    val result = Klaxon().parse<SubmissionsResponse>(responseText)?.result
    Log.d("CodeforcesAPI", "parsed response")
    return result
}
// Add configuration option for background colour

fun get_user(handle: String): User? {
    val responseText = URL("https://codeforces.com/api/user.info?handles=$handle").readText()
    Log.d("CodeforcesAPI", responseText)
    val result = Klaxon().parse<UsersResponse>(responseText)?.result
    Log.d("CodeforcesAPI", "parsed response")
    return result?.first()
}

fun rating_to_colour(rating: Int): Int
    = when {
        rating < 1200 -> 0xFF808080.toInt()
        rating < 1400 -> 0xFF008000.toInt()
        rating < 1600 -> 0xFF03a89e.toInt()
        rating < 1900 -> 0xFF0000ff.toInt()
        rating < 2100 -> 0xFFaa00aa.toInt()
        rating < 2300 -> 0xFFff8c00.toInt()
        rating < 2400 -> 0xFFff8c00.toInt() // IM
        rating < 2600 -> 0xFFff0000.toInt()
        rating < 3000 -> 0xFFff0000.toInt() // IGM
        else          -> 0xFFff0000.toInt() // LGM
    }

