package com.gitee.android.http

import androidx.collection.ArrayMap
import retrofit2.Response
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Common class used by API responses.
 *
 * @param <T> the type of the response object</T>
 *
 * From : https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/api/ApiResponse.kt
 */
open class ApiResponse<T> {
    val code: Int
    val body: T?
    val errorMessage: String?
    val links: Map<String, String>?

    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = error.message ?: "unknown error"
        links = emptyMap()
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            var msg: String? = null
            if (response.errorBody() != null) {
                try {
                    msg = response.errorBody()?.string()
                } catch (ignored: IOException) {
                    //Timber.e(ignored, "error while parsing response")
                }
            }
            if (msg == null || msg.trim { it <= ' ' }.isEmpty()) {
                msg = response.message()
            }
            errorMessage = msg
            body = null
        }
        val linkHeader = response.headers()["link"]
        if (linkHeader == null) {
            links = emptyMap()
        } else {
            links = ArrayMap()
            val matcher: Matcher = LINK_PATTERN.matcher(linkHeader)
            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links.put(matcher.group(2), matcher.group(1))
                }
            }
        }
    }

    val isSuccessful: Boolean
        get() = code in 200..299

    val nextPage: Int?
        get() {
            val next = links?.get(NEXT_LINK) ?: return null
            val matcher: Matcher = PAGE_PATTERN.matcher(next)
            return if (!matcher.find() || matcher.groupCount() != 1) {
                null
            } else try {
                matcher.group(1)?.toInt()
            } catch (ex: NumberFormatException) {
                //Timber.w("cannot parse next page from %s", next)
                null
            }
        }

    override fun toString(): String {
        return "ApiResponse(code=$code, body=$body, errorMessage=$errorMessage, links=$links)"
    }

    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
        private const val NEXT_LINK = "next"
    }

}