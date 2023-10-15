package raven

import koncurrent.Later

class LocalMemoryMailbox : MailBox {
    private val messages = mutableListOf<EmailMessage>()
    override fun save(message: EmailMessage): Later<EmailMessage> {
        messages.add(message)
        return Later(message)
    }

    override fun load(): Later<List<EmailMessage>> = Later(messages)
}