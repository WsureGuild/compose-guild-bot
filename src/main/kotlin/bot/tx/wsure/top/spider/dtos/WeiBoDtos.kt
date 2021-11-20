package bot.tx.wsure.top.spider.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    val cards: List<Card> = emptyList(),
)

@Serializable
data class Card(
    @SerialName("card_type")
    val cardType: Int? = null,
    @SerialName("lastWeiboCard")
    val lastWeiboCard: Boolean? = null,
    @SerialName("mblog")
    val mblog: Mblog? = null,
)

@Serializable
data class Mblog(

    @SerialName("id")
    val id: String? = null,
    @SerialName("isTop")
    val isTop: Int? = null,

    @SerialName("pic_ids")
    val picIds: List<String>? = null,
    @SerialName("pic_num")
    val picNum: Int? = null,

    @SerialName("retweeted_status")
    val retweetedStatus: Mblog? = null,

    @SerialName("source")
    val source: String? = null,
    @SerialName("text")
    val text: String? = null,
    @SerialName("textLength")
    val textLength: Int? = null,
    @SerialName("thumbnail_pic")
    val thumbnailPic: String? = null,

    @SerialName("title")
    val title: Title? = null,
)

@Serializable
data class Title(
    @SerialName("base_color")
    val baseColor: Int? = null,
    @SerialName("text")
    val text: String? = null
)