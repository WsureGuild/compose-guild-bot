package bot.tx.wsure.top.bililiver.dtos.event.cmd
import bot.tx.wsure.top.bililiver.dtos.event.MedalInfo
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class ComboSend(
    @SerialName("action")
    val action: String,
    @SerialName("batch_combo_id")
    val batchComboId: String,
    @SerialName("batch_combo_num")
    val batchComboNum: Int,
    @SerialName("combo_id")
    val comboId: String,
    @SerialName("combo_num")
    val comboNum: Int,
    @SerialName("combo_total_coin")
    val comboTotalCoin: Int,
    @SerialName("dmscore")
    val dmscore: Int,
    @SerialName("gift_id")
    val giftId: Int,
    @SerialName("gift_name")
    val giftName: String,
    @SerialName("gift_num")
    val giftNum: Int,
    @SerialName("is_show")
    val isShow: Int,
    @SerialName("medal_info")
    val medalInfo: MedalInfo,
    @SerialName("name_color")
    val nameColor: String,
    @SerialName("r_uname")
    val rUname: String,
    @SerialName("ruid")
    val ruid: Int,
//    @SerialName("send_master")
//    val sendMaster: Any,
    @SerialName("total_num")
    val totalNum: Int,
    @SerialName("uid")
    val uid: Int,
    @SerialName("uname")
    val uname: String
)