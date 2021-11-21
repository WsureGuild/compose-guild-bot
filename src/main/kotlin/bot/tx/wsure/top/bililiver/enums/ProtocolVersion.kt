package bot.tx.wsure.top.bililiver.enums

enum class ProtocolVersion(val code: Int) {
    JSON(0),
    INT(1),
    ZLIB_INFLATE(2),
    BROTLI(3),
    UNKNOWN(-1),
    ;

    companion object {
        fun getByCode(code: Int): ProtocolVersion {
            return values().associateBy { it.code }[code] ?: UNKNOWN
        }
    }
}