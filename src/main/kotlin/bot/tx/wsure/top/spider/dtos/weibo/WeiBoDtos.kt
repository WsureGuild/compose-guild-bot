package bot.tx.wsure.top.spider.dtos.weibo

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import top.wsure.guild.common.utils.TimeUtils
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Serializable
data class WeiBo(
    @SerialName("data")
    val `data`: Data,
    @SerialName("ok")
    val ok: Int
)

@Serializable
data class Data(
    @SerialName("cards")
    val cards: List<WBCard> = emptyList(),
)

@Serializable
data class WBCard(
    @SerialName("card_type")
    val cardType: Int? = null,
    @SerialName("lastWeiboCard")
    val lastWeiboCard: Boolean? = null,
    @SerialName("mblog")
    val mblog: Mblog? = null,
)

@Serializable
data class Mblog(

    @SerialName("user")
    val user: User,
    @SerialName("id")
    val id: String,
    @SerialName("created_at")
    @Serializable(with = WBTimeSerializer::class)
    val createdAt: ZonedDateTime,
    @SerialName("isTop")
    val isTop: Int? = null,

    @SerialName("pic_ids")
    val picIds: List<String> = emptyList(),
    @SerialName("pic_num")
    val picNum: Int,

    @SerialName("retweeted_status")
    val retweetedStatus: Mblog? = null,

    @SerialName("source")
    val source: String? = null,
    @SerialName("text")
    val text: String,
    @SerialName("textLength")
    val textLength: Int?,
    @SerialName("thumbnail_pic")
    val thumbnailPic: String? = null,

    @SerialName("title")
    val title: Title? = null,

    @SerialName("page_info")
    val pageInfo:PageInfo? = null
)
@Serializable
data class PageInfo(
    @SerialName("page_pic")
    val pagePic: PagePic,
    @SerialName("type")
    val type: String,
    @SerialName("url_ori")
    val urlOri: String?,
)
@Serializable
data class PagePic(
    @SerialName("url")
    val url: String,
)

@Serializable
data class Title(
    @SerialName("base_color")
    val baseColor: Int? = null,
    @SerialName("text")
    val text: String? = null
)


@Serializable
data class User(
    @SerialName("avatar_hd")
    val avatarHd: String,
    @SerialName("close_blue_v")
    val closeBlueV: Boolean,
    @SerialName("cover_image_phone")
    val coverImagePhone: String,
    @SerialName("description")
    val description: String,
    @SerialName("follow_count")
    val followCount: Int,
    @SerialName("followers_count")
    val followersCount: String,
    @SerialName("following")
    val following: Boolean,
    @SerialName("gender")
    val gender: String,
    @SerialName("id")
    val id: Long,
    @SerialName("mbrank")
    val mbrank: Int,
    @SerialName("mbtype")
    val mbtype: Int,
    @SerialName("profile_image_url")
    val profileImageUrl: String,
    @SerialName("profile_url")
    val profileUrl: String,
    @SerialName("screen_name")
    val screenName: String,
    @SerialName("statuses_count")
    val statusesCount: Int,
    @SerialName("urank")
    val urank: Int,
    @SerialName("verified")
    val verified: Boolean,
    @SerialName("verified_reason")
    val verifiedReason: String?,
    @SerialName("verified_type")
    val verifiedType: Int?,
    @SerialName("verified_type_ext")
    val verifiedTypeExt: Int?
)


object WBTimeSerializer : KSerializer<ZonedDateTime> {
    val WB_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH)// XXX yyyy
    override fun deserialize(decoder: Decoder): ZonedDateTime {
        return ZonedDateTime.parse(decoder.decodeString(), WB_FORMATTER)
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("WBTimeSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ZonedDateTime) {
        encoder.encodeString(value.format(WB_FORMATTER))
    }
}