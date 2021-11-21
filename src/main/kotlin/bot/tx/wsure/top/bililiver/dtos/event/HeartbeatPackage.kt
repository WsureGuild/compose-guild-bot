package bot.tx.wsure.top.bililiver.dtos.event

import bot.tx.wsure.top.bililiver.enums.Operation
import bot.tx.wsure.top.bililiver.enums.ProtocolVersion

object HeartbeatPackage: ChatPackage(
31,
    16,
    ProtocolVersion.INT,
    Operation.HEARTBEAT,
    1,
    "[object Object]".toByteArray()
)