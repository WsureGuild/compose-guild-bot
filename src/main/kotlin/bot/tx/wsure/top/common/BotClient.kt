package bot.tx.wsure.top.common

interface BotClient {

    fun reconnect()

    fun sendMessage(text: String)
}