package com.gs.test.data.api

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.SocketTimeoutException

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response? = null
        var responseString: String? = null
        try {
            response = chain.proceed(chain.request())
            responseString = String(response.body!!.bytes())
        } catch (ex: Exception) {
            return when (ex) {
                is SocketTimeoutException -> buildErrorResponse(
                    chain,
                    InterceptErrorCode.SocketTimeout
                )
                else -> buildErrorResponse(
                    chain,
                    InterceptErrorCode.OtherError
                )
            }
        }
        return response.newBuilder()
            .body(responseString.toResponseBody(response.body!!.contentType()))
            .build()
    }
    private fun buildErrorResponse(chain: Interceptor.Chain, error: InterceptErrorCode): Response {
        return Response.Builder()
            .code(error.code)
            .protocol(Protocol.HTTP_2)
            .message(error.msg)
            .body("{\"x\": 1}".toResponseBody("application/json".toMediaType()))
            .request(chain.request())
            .build()
    }
    sealed class InterceptErrorCode(val code: Int, val msg: String) {
        companion object {
            const val SOCKET_TIMEOUT_CODE = 504
            const val SOCKET_TIMEOUT_MSG = "Timeout, Try again"
            const val OTHER_ERROR_CODE = 504
            const val OTHER_ERROR_MSG = "Error occurred"
        }

        object SocketTimeout : InterceptErrorCode(SOCKET_TIMEOUT_CODE, SOCKET_TIMEOUT_MSG)
        object OtherError : InterceptErrorCode(OTHER_ERROR_CODE, OTHER_ERROR_MSG)
    }
}