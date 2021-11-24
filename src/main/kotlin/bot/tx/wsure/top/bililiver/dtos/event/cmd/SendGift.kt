package bot.tx.wsure.top.bililiver.dtos.event.cmd
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class SendGift(
    @SerialName("action")
    val action: String,
    @SerialName("batch_combo_id")
    val batchComboId: String?,
    @SerialName("batch_combo_send")
    val batchComboSend: BatchComboSend?,
    @SerialName("beatId")
    val beatId: String,
    @SerialName("biz_source")
    val bizSource: String,
    @SerialName("blind_gift")
    val blindGift: BlindGift?,
    @SerialName("broadcast_id")
    val broadcastId: Int,
    @SerialName("coin_type")
    val coinType: String,
    @SerialName("combo_resources_id")
    val comboResourcesId: Int,
    @SerialName("combo_send")
    val comboSend: ComboInfo?,
    @SerialName("combo_stay_time")
    val comboStayTime: Int,
    @SerialName("combo_total_coin")
    val comboTotalCoin: Int,
    @SerialName("crit_prob")
    val critProb: Int,
    @SerialName("demarcation")
    val demarcation: Int,
    @SerialName("discount_price")
    val discountPrice: Int,
    @SerialName("dmscore")
    val dmscore: Int,
    @SerialName("draw")
    val draw: Int,
    @SerialName("effect")
    val effect: Int,
    @SerialName("effect_block")
    val effectBlock: Int,
    @SerialName("face")
    val face: String,
    @SerialName("float_sc_resource_id")
    val floatScResourceId: Int,
    @SerialName("giftId")
    val giftId: Int,
    @SerialName("giftName")
    val giftName: String,
    @SerialName("giftType")
    val giftType: Int,
    @SerialName("gold")
    val gold: Int,
    @SerialName("guard_level")
    val guardLevel: Int,
    @SerialName("is_first")
    val isFirst: Boolean,
    @SerialName("is_special_batch")
    val isSpecialBatch: Int,
    @SerialName("magnification")
    val magnification: Double,
    @SerialName("medal_info")
    val medalInfo: MedalInfo,
    @SerialName("name_color")
    val nameColor: String,
    @SerialName("num")
    val num: Int,
    @SerialName("original_gift_name")
    val originalGiftName: String,
    @SerialName("price")
    val price: Int,
    @SerialName("rcost")
    val rcost: Int,
    @SerialName("remain")
    val remain: Int,
    @SerialName("rnd")
    val rnd: String,
//    @SerialName("send_master")
//    val sendMaster: Any?,
    @SerialName("silver")
    val silver: Int,
    @SerialName("super_batch_gift_num")
    val superBatchGiftNum: Int,
    @SerialName("super_gift_num")
    val superGiftNum: Int,
    @SerialName("super")
    val superX: Int,
    @SerialName("svga_block")
    val svgaBlock: Int,
    @SerialName("tag_image")
    val tagImage: String,
    @SerialName("tid")
    val tid: String,
    @SerialName("timestamp")
    val timestamp: Int,
//    @SerialName("top_list")
//    val topList: Any?,
    @SerialName("total_coin")
    val totalCoin: Int,
    @SerialName("uid")
    val uid: Int,
    @SerialName("uname")
    val uname: String
)
@Serializable
data class MedalInfo(
    @SerialName("anchor_roomid")
    val anchorRoomid: Int,
    @SerialName("anchor_uname")
    val anchorUname: String,
    @SerialName("guard_level")
    val guardLevel: Int,
    @SerialName("icon_id")
    val iconId: Int,
    @SerialName("is_lighted")
    val isLighted: Int,
    @SerialName("medal_color")
    val medalColor: Int,
    @SerialName("medal_color_border")
    val medalColorBorder: Int,
    @SerialName("medal_color_end")
    val medalColorEnd: Int,
    @SerialName("medal_color_start")
    val medalColorStart: Int,
    @SerialName("medal_level")
    val medalLevel: Int,
    @SerialName("medal_name")
    val medalName: String,
    @SerialName("special")
    val special: String,
    @SerialName("target_id")
    val targetId: Int
)

@Serializable
data class ComboInfo(
    @SerialName("action")
    val action: String,
    @SerialName("batch_combo_id")
    val batchComboId: String?,
    @SerialName("batch_combo_num")
    val batchComboNum: Int?,
    @SerialName("blind_gift")
    val blindGift: BlindGift?,
    @SerialName("gift_id")
    val giftId: Int,
    @SerialName("gift_name")
    val giftName: String,
    @SerialName("gift_num")
    val giftNum: Int,
//    @SerialName("send_master")
//    val sendMaster: Any,
    @SerialName("uid")
    val uid: Int,
    @SerialName("uname")
    val uname: String
)

@Serializable
data class BlindGift(
    @SerialName("blind_gift_config_id")
    val blindGiftConfigId: Int,
    @SerialName("gift_action")
    val giftAction: String,
    @SerialName("original_gift_id")
    val originalGiftId: Int,
    @SerialName("original_gift_name")
    val originalGiftName: String
)
@Serializable
data class BatchComboSend(
    @SerialName("action")
    val action: String,
    @SerialName("batch_combo_id")
    val batchComboId: String?,
    @SerialName("batch_combo_num")
    val batchComboNum: Int?,
    @SerialName("blind_gift")
    val blindGift: BlindGift?,
    @SerialName("gift_id")
    val giftId: Int,
    @SerialName("gift_name")
    val giftName: String,
    @SerialName("gift_num")
    val giftNum: Int,
//    @SerialName("send_master")
//    val sendMaster: Any,
    @SerialName("uid")
    val uid: Int,
    @SerialName("uname")
    val uname: String
)