package raven

import io.ktor.client.HttpClient
import kommander.expect
import kommander.toContain
import koncurrent.later.await
import kotlin.test.Test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.test.runTest

class FlixMailboxTest {

    val url = "http://127.0.0.1:8080"

    val scope = CoroutineScope(SupervisorJob())

    val box = FlixMailbox(FlixMailBoxOptions(url, Channel(Channel.BUFFERED), scope))

    val mailer = FlixClientMailer(FlixClientMailerOptions(url, scope, HttpClient { }))

    @Test
    fun should_be_able_to_anticipate_multiple_mails() = runTest {
        repeat(3) {
            val anticipatedMail = box.anticipate()
            mailer.send(EmailDraft("Test Email", "Test anticipation-$it"), from = "andy@lamax.com", to = "john@doe.com").await()
            val mail = anticipatedMail.await()
            expect(mail).toContain("anticipation-$it")
        }
    }
}