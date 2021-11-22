package bot.tx.wsure.top.bililiver.enums

import kotlinx.serialization.Serializable

@Serializable
enum class NoticeCmd(val description:String ) {
    DANMU_MSG("弹幕消息"),
    ENTRY_EFFECT("欢迎舰长进入房间"),
    SUPER_CHAT_MESSAGE("SC"),
    SUPER_CHAT_MESSAGE_JPN("SC罕见"),

    HOT_RANK_CHANGED("热度等级变化"),
    HOT_RANK_CHANGED_V2("热度等级变化v2"),
    ACTIVITY_BANNER_UPDATE_V2("小时榜变动"),

    SEND_GIFT("礼物"),
    COMBO_SEND("连击礼物"),
    ONLINE_RANK_TOP3("榜上老头变化"),

    ANCHOR_LOT_START("天选之人开始完整信息"),
    ANCHOR_LOT_END("天选之人获奖id"),
    ANCHOR_LOT_AWARD("天选之人获奖完整信息"),

    ONLINE_RANK_COUNT("在线等级数???"),
    ONLINE_RANK_V2("在线等级数v2???"),
    ROOM_REAL_TIME_MESSAGE_UPDATE("房间人气粉丝数"),
    STOP_LIVE_ROOM_LIST("下播的房间"),
    WIDGET_BANNER("小部件横幅"),
    INTERACT_WORD("交互词"),
    LIVE_INTERACTIVE_GAME("交互游戏"),
    ;
}
