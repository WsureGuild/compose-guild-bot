package bot.tx.wsure.top.bililiver.dtos.event.cmd
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class UserToastMsg(
    @SerialName("anchor_show")
    val anchorShow: Boolean,
    @SerialName("color")
    val color: String,
    @SerialName("dmscore")
    val dmscore: Int,
    @SerialName("end_time")
    val endTime: Int,
    @SerialName("guard_level")
    val guardLevel: Int,
    @SerialName("is_show")
    val isShow: Int,
    @SerialName("num")
    val num: Int,
    @SerialName("op_type")
    val opType: Int,
    @SerialName("payflow_id")
    val payflowId: String,
    @SerialName("price")
    val price: Int,
    @SerialName("role_name")
    val roleName: String,
    @SerialName("start_time")
    val startTime: Int,
    @SerialName("svga_block")
    val svgaBlock: Int,
    @SerialName("target_guard_count")
    val targetGuardCount: Int,
    @SerialName("toast_msg")
    val toastMsg: String,
    @SerialName("uid")
    val uid: Int,
    @SerialName("unit")
    val unit: String,
    @SerialName("user_show")
    val userShow: Boolean,
    @SerialName("username")
    val username: String
)