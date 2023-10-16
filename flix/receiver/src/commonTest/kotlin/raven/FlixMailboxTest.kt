package raven

import io.ktor.client.HttpClient
import kommander.expect
import kommander.toContain
import koncurrent.later.await
import kotlin.test.Ignore
import kotlin.test.Test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest

class FlixMailboxTest {

    val url = "http://127.0.0.1:8080"

    val scope = CoroutineScope(SupervisorJob())

    val box = FlixMailbox(FlixMailBoxOptions(url, Channel(Channel.BUFFERED), scope))

    val mailer = FlixClientMailer(FlixClientMailerOptions(url, scope, HttpClient { }))

//    @Ignore
    @Test
    fun should_be_able_to_receive_emails_in_the_flix_mailbox() = runTest {
        launch {
            repeat(5) {
                val received = box.channel.receive()
                println("received: $received")
            }
        }


        repeat(5) {
            mailer.send(EmailDraft("Test Email", "Test Email"), from = "andy@lamax.com", to = "john@doe.com").await()
        }
    }

//    @Ignore
    @Test
    fun should_be_able_to_anticipate_a_single_mail() = runTest {
        val anticipatedMail = box.anticipate()
        mailer.send(EmailDraft("Test Email", "Test anticipation"), from = "andy@lamax.com", to = "john@doe.com").await()
        val mail = anticipatedMail.await()
        expect(mail).toContain("anticipation")
    }

//    @Ignore
    @Test
    fun should_be_able_to_anticipate_mail_multiple_mails() = runTest {
        repeat(10) {
            val anticipatedMail = box.anticipate()
            mailer.send(EmailDraft("Test Email", "Test anticipation-$it"), from = "andy@lamax.com", to = "john@doe.com").await()
            val mail = anticipatedMail.await()
            expect(mail).toContain("anticipation-$it")
        }
    }
}