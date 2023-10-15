@file:JsExport

package raven

import kotlin.js.JsExport

sealed class MailingResult {
    object Success : MailingResult()
    data class Failure(val cause: Throwable) : MailingResult()
}