package com.starswap.codeforceswidgets.codeforces

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import com.beust.klaxon.Klaxon
import java.net.URL
import java.time.LocalDate
import java.util.Calendar
import java.util.TimeZone

data class SubmissionsResponse(val status: String, val result: List<Submission>? = null, val comment: String? = null)
data class Submission(val id: Int, val contestId: Int, val creationTimeSeconds: Long, val relativeTimeSeconds: Long, val problem: Problem, val author: Author, val programmingLanguage: String, val verdict: String? = null, val testset: String, val passedTestCount: Int, val timeConsumedMillis: Int, val memoryConsumedBytes: Int)
data class Problem(val contestId: Int, val index: String, val name: String, val type: String, val rating: Int? = null, val tags: List<String>)
data class Author(val contestId: Int, val members: List<Member>, val participantType: String, val ghost: Boolean, val startTimeSeconds: Long? = null)
data class Member(val handle: String)

data class UsersResponse(val status: String, val result: List<User>? = null, val comment: String? = null)
data class User(val rating: Int? = null, val handle: String)

fun latest_submissions(handle: String, min: Int = 1, limit: Int? = 100): List<Submission>? {
    var query = "https://codeforces.com/api/user.status?handle=$handle&from=$min"
    if (limit != null) {
        query += "&count=$limit"
    }
    val responseText = URL(query).readText()
    return Klaxon().parse<SubmissionsResponse>(responseText)?.result
}

fun get_user(handle: String): User? {
    val responseText = URL("https://codeforces.com/api/user.info?handles=$handle").readText()
    val result = Klaxon().parse<UsersResponse>(responseText)?.result
    return result?.first()
}

fun render_user(user: User): SpannableString  {
    val rating = user.rating ?: 0
    val string = SpannableString(user.handle)
    string.setSpan(ForegroundColorSpan(rating_to_colour(rating)), 0, user.handle.length, 0)
    if (rating >= 3000) {
        string.setSpan(ForegroundColorSpan(Color.BLACK), 0, 1, 0)
    }
    return string
}

private fun rating_to_colour(rating: Int)
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

fun streak(handle: String): Pair<Int, Int> {
    fun toLocalDate(epochSeconds: Long): LocalDate {
        val tz = TimeZone.getDefault()
        val cal = Calendar.Builder().setInstant(epochSeconds * 1000).setTimeZone(tz).build()
        return LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
    }
    val today = LocalDate.now() /* Cache this because the function might be executed at 11:59:59 pm :( */
    val BATCH_SIZE = 100

    var streakStart = today
    var acProblemsToday = 0
    var hasSubmittedToday = false
    var streak = 0
    var submissionsBatch: List<Submission>?

    var min = 1
    var streakDone = false;
    do {
        submissionsBatch = latest_submissions(handle, min, min + BATCH_SIZE - 1)

        submissionsBatch?.let { submissionsBatch ->
            val submissionDates = submissionsBatch.map { toLocalDate(it.creationTimeSeconds) }
            Log.d("Streak", submissionDates.toString())
            acProblemsToday += submissionsBatch.filter { toLocalDate(it.creationTimeSeconds) == today }.map { it.problem }.toSet().size
            hasSubmittedToday = hasSubmittedToday || submissionDates.count { it == today } > 0
            for (submissionDate in submissionDates) {
                if (streakStart.minusDays(1) == submissionDate) {
                    streakStart = submissionDate
                    streak++
                } else if (streakStart.minusDays(1) > submissionDate) {
                    streakDone = true;
                    break;
                }
            }
        }
        min += BATCH_SIZE
    } while (!streakDone && !submissionsBatch.isNullOrEmpty())

    if (hasSubmittedToday) {
        streak++
    }
    return Pair(streak, acProblemsToday)
}
