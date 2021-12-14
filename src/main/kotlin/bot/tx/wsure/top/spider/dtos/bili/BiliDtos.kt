package bot.tx.wsure.top.spider.dtos.bili
import bot.tx.wsure.top.spider.dtos.weibo.User
import bot.tx.wsure.top.spider.dtos.weibo.WBCard
import bot.tx.wsure.top.utils.JsonUtils.jsonToObject
import bot.tx.wsure.top.utils.JsonUtils.objectToJson
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


@Serializable
data class BiliDynamic(
    @SerialName("cards")
    val cards: List<WBCard>? = null,
    @SerialName("_gt_")
    val gt: Int? = null,
    @SerialName("has_more")
    val hasMore: Int? = null,
    @SerialName("next_offset")
    val nextOffset: Long? = null
)

@Serializable
data class BLCard(
    @SerialName("card")
    @Serializable(with = CardContext.CardContextSerializer::class )
    val card: CardContext? = null,
    @SerialName("desc")
    val desc: Desc? = null,
    @SerialName("display")
    val display: Display? = null,
    @SerialName("extend_json")
    val extendJson: String? = null,
    @SerialName("extra")
    val extra: Extra? = null
)
@Serializable
data class CardContext(
    @SerialName("item")
    val item: Item? = null,
    @SerialName("origin")
    val origin: String? = null,
    @SerialName("origin_extend_json")
    val originExtendJson: String? = null,
    @SerialName("origin_user")
    val originUser: OriginUser? = null,
    @SerialName("user")
    val user: User? = null
){
    class CardContextSerializer : KSerializer<CardContext>{
        override fun deserialize(decoder: Decoder): CardContext {
            return decoder.decodeString().jsonToObject()
        }

        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("CardContext", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: CardContext) {
            return encoder.encodeString(value.objectToJson())
        }

    }
}

@Serializable
data class Item(
    @SerialName("at_uids")
    val atUids: List<Int>? = null,
    @SerialName("content")
    val content: String? = null,
    @SerialName("ctrl")
    val ctrl: String? = null,
    @SerialName("orig_dy_id")
    val origDyId: Long? = null,
    @SerialName("orig_type")
    val origType: Int? = null,
    @SerialName("pre_dy_id")
    val preDyId: Long? = null,
    @SerialName("reply")
    val reply: Int? = null,
    @SerialName("rp_id")
    val rpId: Long? = null,
    @SerialName("uid")
    val uid: Int? = null
)

@Serializable
data class OriginUser(
    @SerialName("card")
    val card: Card? = null,
    @SerialName("info")
    val info: Info? = null,
    @SerialName("level_info")
    val levelInfo: LevelInfo? = null,
    @SerialName("pendant")
    val pendant: Pendant? = null,
    @SerialName("rank")
    val rank: String? = null,
    @SerialName("sign")
    val sign: String? = null,
    @SerialName("vip")
    val vip: Vip? = null
)

@Serializable
data class User(
    @SerialName("face")
    val face: String? = null,
    @SerialName("uid")
    val uid: Int? = null,
    @SerialName("uname")
    val uname: String? = null
)

@Serializable
data class Card(
    @SerialName("official_verify")
    val officialVerify: OfficialVerify? = null
)

@Serializable
data class Pendant(
    @SerialName("expire")
    val expire: Int? = null,
    @SerialName("image")
    val image: String? = null,
    @SerialName("image_enhance")
    val imageEnhance: String? = null,
    @SerialName("image_enhance_frame")
    val imageEnhanceFrame: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("pid")
    val pid: Int? = null
)

@Serializable
data class Vip(
    @SerialName("avatar_subscript")
    val avatarSubscript: Int? = null,
    @SerialName("avatar_subscript_url")
    val avatarSubscriptUrl: String? = null,
    @SerialName("label")
    val label: Label? = null,
    @SerialName("nickname_color")
    val nicknameColor: String? = null,
    @SerialName("role")
    val role: Int? = null,
    @SerialName("themeType")
    val themeType: Int? = null,
    @SerialName("vipDueDate")
    val vipDueDate: Long? = null,
    @SerialName("vipStatus")
    val vipStatus: Int? = null,
    @SerialName("vipType")
    val vipType: Int? = null
)

@Serializable
data class Desc(
    @SerialName("acl")
    val acl: Int? = null,
    @SerialName("bvid")
    val bvid: String? = null,
    @SerialName("comment")
    val comment: Int? = null,
    @SerialName("dynamic_id")
    val dynamicId: Long? = null,
    @SerialName("dynamic_id_str")
    val dynamicIdStr: String? = null,
    @SerialName("inner_id")
    val innerId: Int? = null,
    @SerialName("is_liked")
    val isLiked: Int? = null,
    @SerialName("like")
    val like: Int? = null,
    @SerialName("orig_dy_id")
    val origDyId: Long? = null,
    @SerialName("orig_dy_id_str")
    val origDyIdStr: String? = null,
    @SerialName("orig_type")
    val origType: Int? = null,
    @SerialName("origin")
    val origin: Origin? = null,
    @SerialName("pre_dy_id")
    val preDyId: Long? = null,
    @SerialName("pre_dy_id_str")
    val preDyIdStr: String? = null,
    @SerialName("previous")
    val previous: Previous? = null,
    @SerialName("r_type")
    val rType: Int? = null,
    @SerialName("repost")
    val repost: Int? = null,
    @SerialName("rid")
    val rid: Long? = null,
    @SerialName("rid_str")
    val ridStr: String? = null,
    @SerialName("status")
    val status: Int? = null,
    @SerialName("stype")
    val stype: Int? = null,
    @SerialName("timestamp")
    val timestamp: Int? = null,
    @SerialName("type")
    val type: Int? = null,
    @SerialName("uid")
    val uid: Int? = null,
    @SerialName("uid_type")
    val uidType: Int? = null,
    @SerialName("user_profile")
    val userProfile: UserProfile? = null,
    @SerialName("view")
    val view: Int? = null
)

@Serializable
data class Display(
    @SerialName("add_on_card_info")
    val addOnCardInfo: List<AddOnCardInfo>? = null,
    @SerialName("attach_card")
    val attachCard: AttachCardX? = null,
    @SerialName("comment_info")
    val commentInfo: CommentInfo? = null,
    @SerialName("cover_play_icon_url")
    val coverPlayIconUrl: String? = null,
    @SerialName("emoji_info")
    val emojiInfo: EmojiInfo? = null,
    @SerialName("origin")
    val origin: Origin? = null,
    @SerialName("relation")
    val relation: Relation? = null,
    @SerialName("show_tip")
    val showTip: ShowTip? = null,
    @SerialName("topic_info")
    val topicInfo: TopicInfo? = null,
    @SerialName("usr_action_txt")
    val usrActionTxt: String? = null
)

@Serializable
data class Extra(
    @SerialName("is_pgc_feature")
    val isPgcFeature: Int? = null,
    @SerialName("is_space_top")
    val isSpaceTop: Int? = null
)

@Serializable
data class Origin(
    @SerialName("acl")
    val acl: Int? = null,
    @SerialName("bvid")
    val bvid: String? = null,
    @SerialName("dynamic_id")
    val dynamicId: Long? = null,
    @SerialName("dynamic_id_str")
    val dynamicIdStr: String? = null,
    @SerialName("inner_id")
    val innerId: Int? = null,
    @SerialName("like")
    val like: Int? = null,
    @SerialName("orig_dy_id")
    val origDyId: Int? = null,
    @SerialName("orig_dy_id_str")
    val origDyIdStr: String? = null,
    @SerialName("pre_dy_id")
    val preDyId: Int? = null,
    @SerialName("pre_dy_id_str")
    val preDyIdStr: String? = null,
    @SerialName("r_type")
    val rType: Int? = null,
    @SerialName("repost")
    val repost: Int? = null,
    @SerialName("rid")
    val rid: Int? = null,
    @SerialName("rid_str")
    val ridStr: String? = null,
    @SerialName("status")
    val status: Int? = null,
    @SerialName("stype")
    val stype: Int? = null,
    @SerialName("timestamp")
    val timestamp: Int? = null,
    @SerialName("type")
    val type: Int? = null,
    @SerialName("uid")
    val uid: Int? = null,
    @SerialName("uid_type")
    val uidType: Int? = null,
    @SerialName("view")
    val view: Int? = null
)

@Serializable
data class Previous(
    @SerialName("acl")
    val acl: Int? = null,
    @SerialName("dynamic_id")
    val dynamicId: Long? = null,
    @SerialName("dynamic_id_str")
    val dynamicIdStr: String? = null,
    @SerialName("inner_id")
    val innerId: Int? = null,
    @SerialName("like")
    val like: Int? = null,
    @SerialName("orig_dy_id")
    val origDyId: Long? = null,
    @SerialName("orig_dy_id_str")
    val origDyIdStr: String? = null,
    @SerialName("pre_dy_id")
    val preDyId: Long? = null,
    @SerialName("pre_dy_id_str")
    val preDyIdStr: String? = null,
    @SerialName("r_type")
    val rType: Int? = null,
    @SerialName("repost")
    val repost: Int? = null,
    @SerialName("rid")
    val rid: Long? = null,
    @SerialName("rid_str")
    val ridStr: String? = null,
    @SerialName("status")
    val status: Int? = null,
    @SerialName("stype")
    val stype: Int? = null,
    @SerialName("timestamp")
    val timestamp: Int? = null,
    @SerialName("type")
    val type: Int? = null,
    @SerialName("uid")
    val uid: Int? = null,
    @SerialName("uid_type")
    val uidType: Int? = null,
    @SerialName("view")
    val view: Int? = null
)

@Serializable
data class UserProfile(
    @SerialName("card")
    val card: CardX? = null,
    @SerialName("decorate_card")
    val decorateCard: DecorateCard? = null,
    @SerialName("info")
    val info: Info? = null,
    @SerialName("level_info")
    val levelInfo: LevelInfo? = null,
    @SerialName("pendant")
    val pendant: Pendant? = null,
    @SerialName("rank")
    val rank: String? = null,
    @SerialName("sign")
    val sign: String? = null,
    @SerialName("vip")
    val vip: Vip? = null
)

@Serializable
data class CardX(
    @SerialName("official_verify")
    val officialVerify: OfficialVerify? = null
)

@Serializable
data class DecorateCard(
    @SerialName("big_card_url")
    val bigCardUrl: String? = null,
    @SerialName("card_type")
    val cardType: Int? = null,
    @SerialName("card_type_name")
    val cardTypeName: String? = null,
    @SerialName("card_url")
    val cardUrl: String? = null,
    @SerialName("expire_time")
    val expireTime: Int? = null,
    @SerialName("fan")
    val fan: Fan? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("image_enhance")
    val imageEnhance: String? = null,
    @SerialName("item_id")
    val itemId: Int? = null,
    @SerialName("item_type")
    val itemType: Int? = null,
    @SerialName("jump_url")
    val jumpUrl: String? = null,
    @SerialName("mid")
    val mid: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("uid")
    val uid: Int? = null
)

@Serializable
data class Info(
    @SerialName("face")
    val face: String? = null,
    @SerialName("face_nft")
    val faceNft: Int? = null,
    @SerialName("uid")
    val uid: Int? = null,
    @SerialName("uname")
    val uname: String? = null
)

@Serializable
data class LevelInfo(
    @SerialName("current_level")
    val currentLevel: Int? = null
)

@Serializable
data class OfficialVerify(
    @SerialName("desc")
    val desc: String? = null,
    @SerialName("type")
    val type: Int? = null
)

@Serializable
data class Fan(
    @SerialName("color")
    val color: String? = null,
    @SerialName("is_fan")
    val isFan: Int? = null,
    @SerialName("num_desc")
    val numDesc: String? = null,
    @SerialName("number")
    val number: Int? = null
)

@Serializable
data class Label(
    @SerialName("bg_color")
    val bgColor: String? = null,
    @SerialName("bg_style")
    val bgStyle: Int? = null,
    @SerialName("border_color")
    val borderColor: String? = null,
    @SerialName("label_theme")
    val labelTheme: String? = null,
    @SerialName("path")
    val path: String? = null,
    @SerialName("text")
    val text: String? = null,
    @SerialName("text_color")
    val textColor: String? = null
)

@Serializable
data class AddOnCardInfo(
    @SerialName("add_on_card_show_type")
    val addOnCardShowType: Int? = null,
    @SerialName("attach_card")
    val attachCard: AttachCard? = null
)

@Serializable
data class AttachCardX(
    @SerialName("button")
    val button: Button? = null,
    @SerialName("cover_type")
    val coverType: Int? = null,
    @SerialName("cover_url")
    val coverUrl: String? = null,
    @SerialName("desc_first")
    val descFirst: String? = null,
    @SerialName("desc_second")
    val descSecond: String? = null,
    @SerialName("head_text")
    val headText: String? = null,
    @SerialName("jump_url")
    val jumpUrl: String? = null,
    @SerialName("oid_str")
    val oidStr: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("type")
    val type: String? = null
)

@Serializable
data class CommentInfo(
    @SerialName("comment_ids")
    val commentIds: String? = null,
    @SerialName("comments")
    val comments: List<Comment>? = null,
    @SerialName("emojis")
    val emojis: List<Emoji>? = null
)

@Serializable
data class EmojiInfo(
    @SerialName("emoji_details")
    val emojiDetails: List<EmojiDetail>? = null
)

@Serializable
data class AttachCard(
    @SerialName("button")
    val button: Button? = null,
    @SerialName("cover_type")
    val coverType: Int? = null,
    @SerialName("cover_url")
    val coverUrl: String? = null,
    @SerialName("desc_first")
    val descFirst: String? = null,
    @SerialName("desc_second")
    val descSecond: String? = null,
    @SerialName("head_text")
    val headText: String? = null,
    @SerialName("jump_url")
    val jumpUrl: String? = null,
    @SerialName("oid_str")
    val oidStr: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("type")
    val type: String? = null
)

@Serializable
data class Button(
    @SerialName("check")
    val check: Check? = null,
    @SerialName("status")
    val status: Int? = null,
    @SerialName("type")
    val type: Int? = null,
    @SerialName("uncheck")
    val uncheck: Uncheck? = null
)

@Serializable
data class Check(
    @SerialName("icon")
    val icon: String? = null,
    @SerialName("text")
    val text: String? = null
)

@Serializable
data class Uncheck(
    @SerialName("icon")
    val icon: String? = null,
    @SerialName("text")
    val text: String? = null
)
@Serializable
data class Comment(
    @SerialName("content")
    val content: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("uid")
    val uid: Int? = null
)

@Serializable
data class Emoji(
    @SerialName("emoji_name")
    val emojiName: String? = null,
    @SerialName("meta")
    val meta: Meta? = null,
    @SerialName("url")
    val url: String? = null
)

@Serializable
data class Meta(
    @SerialName("size")
    val size: Int? = null
)

@Serializable
data class EmojiDetail(
    @SerialName("attr")
    val attr: Int? = null,
    @SerialName("emoji_name")
    val emojiName: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("meta")
    val meta: Meta? = null,
    @SerialName("mtime")
    val mtime: Int? = null,
    @SerialName("package_id")
    val packageId: Int? = null,
    @SerialName("state")
    val state: Int? = null,
    @SerialName("text")
    val text: String? = null,
    @SerialName("type")
    val type: Int? = null,
    @SerialName("url")
    val url: String? = null
)

@Serializable
data class Relation(
    @SerialName("is_follow")
    val isFollow: Int? = null,
    @SerialName("is_followed")
    val isFollowed: Int? = null,
    @SerialName("status")
    val status: Int? = null
)

@Serializable
data class ShowTip(
    @SerialName("del_tip")
    val delTip: String? = null
)

@Serializable
data class TopicInfo(
    @SerialName("topic_details")
    val topicDetails: List<TopicDetail>? = null
)

@Serializable
data class TopicDetail(
    @SerialName("is_activity")
    val isActivity: Int? = null,
    @SerialName("topic_id")
    val topicId: Int? = null,
    @SerialName("topic_link")
    val topicLink: String? = null,
    @SerialName("topic_name")
    val topicName: String? = null
)