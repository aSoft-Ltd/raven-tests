package raven

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.jvm.JvmField
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

interface MockMailerConfig {
    val printToConsole: Boolean
    val separator: String
    val simulationTime: Long
    val box: MailBox

    /**
     * It looks better when this value is an odd number
     */
    val charsPerLine: Int
    val marginWidth: String
    val border: String
    val paddingWidth: String
    val scope: CoroutineScope

    companion object {
        @JvmField
        val DEFAULT_PRINT_TO_CONSOLE = true

        @JvmField
        val DEFAULT_SIMULATION_TIME = 0L

        @JvmField
        val DEFAULT_SCOPE = CoroutineScope(SupervisorJob())

        @JvmField
        val DEFAULT_CHARS_PER_LINE = 55

        @JvmField
        val DEFAULT_SEPERATOR = "="

        @JvmField
        val DEFAULT_BORDER = "|"

        @JvmField
        val DEFAULT_MARGIN_WIDTH = "\t".repeat(6)

        @JvmField
        val DEFAULT_PADDING_WIDTH = " "

        @JvmField
        val DEFAULT_MAIL_BOX = LocalMemoryMailbox()

        @JvmOverloads
        @JvmStatic
        @JvmName("create")
        operator fun invoke(
            printToConsole: Boolean = DEFAULT_PRINT_TO_CONSOLE,
            simulationTime: Long = DEFAULT_SIMULATION_TIME,
            separator: String = DEFAULT_SEPERATOR,
            charsPerLine: Int = DEFAULT_CHARS_PER_LINE,
            marginWidth: String = DEFAULT_MARGIN_WIDTH,
            border: String = DEFAULT_BORDER,
            paddingWidth: String = DEFAULT_PADDING_WIDTH,
            scope: CoroutineScope = DEFAULT_SCOPE,
            box: MailBox = DEFAULT_MAIL_BOX
        ) = object : MockMailerConfig {
            override val printToConsole = printToConsole
            override val simulationTime = simulationTime
            override val separator = separator
            override val charsPerLine = charsPerLine
            override val marginWidth = marginWidth
            override val border = border
            override val paddingWidth = paddingWidth
            override val scope = scope
            override val box = box
        }
    }
}