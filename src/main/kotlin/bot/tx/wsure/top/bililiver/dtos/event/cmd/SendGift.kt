package bot.tx.wsure.top.bililiver.dtos.event.cmd
import bot.tx.wsure.top.bililiver.dtos.event.MedalInfo
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class SendGift(
    @SerialName("action")
    val action: String,
    @SerialName("batch_combo_id")
    val batchComboId: String,
    @SerialName("batch_combo_send")
    val batchComboSend: Int?,
    @SerialName("beatId")
    val beatId: String,
    @SerialName("biz_source")
    val bizSource: String,
//    @SerialName("blind_gift")
//    val blindGift: Any,
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
//    val topList: Any,
    @SerialName("total_coin")
    val totalCoin: Int,
    @SerialName("uid")
    val uid: Int,
    @SerialName("uname")
    val uname: String
)
@Serializable
data class ComboInfo(
    @SerialName("action")
    val action: String,
    @SerialName("combo_id")
    val comboId: String,
    @SerialName("combo_num")
    val comboNum: Int,
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