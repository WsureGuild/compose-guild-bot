# vup-fan-guild-bot（go-cqhttp）
#### 一个Vup粉丝QQ频道机器人
## 部署教程
1. 准备工作：
   1. jdk 1.8 (需要自己装嗷，配下环境变量)
   2. go-cqhttp ver:[`v1.0.0-beta8-fix2`](https://github.com/Mrs4s/go-cqhttp/releases/tag/v1.0.0-beta8-fix2)
   3. 下载最新版的[`release`](https://github.com/WsureDev/compose-guild-bot/releases/) 的jar包
2. 部署
   1. 先按照[`go-cqhttp教程`](https://docs.go-cqhttp.org/guide/quick_start.html) 运行起来登录qq（该qq已经加入目标频道）
   2. 使用`java -jar xxxxxx.jar`命令跑起来本项目的jar包，会自动生成`config`文件夹和配置文件
   3. 修改配置文件后，重新运行jar包
3. 配置文件
   1. 配置文件含义
   ```json
      {
      "channels": [ //发送消息的频道，
         {
            "name": "nanami_notify", //以七海频为例，name为唯一的标识
            "description": "开播通知", 
            "guildId": 6000051636714649, //频道Id，从gocq的api或者控制台获取
            "channelId": 2072944 //子频道Id，从gocq的api或者控制台获取
         },
         {
            "name": "nanami_sc",
            "description": "海频SC",
            "guildId": 6000051636714649,
            "channelId": 1370732
         },
         {
            "name": "nanami_ybb",
            "description": "海频YBB",
            "guildId": 6000051636714649,
            "channelId": 1560174
         },
         {
            "name": "nanami_dynamic",
            "description": "海频动态",
            "guildId": 6000051636714649,
            "channelId": 1407453
         },
         {
            "name": "azu_live_topic",
            "description": "梓频直播讨论",
            "guildId": 36667731636792997,
            "channelId": 1365148
         },
         {
            "name": "azu_archives",
            "description": "梓频档案",
            "guildId": 36667731636792997,
            "channelId": 1732797
         },
         {
            "name": "azu_ybb",
            "description": "梓频YBB",
            "guildId": 36667731636792997,
            "channelId": 1591085
         },
         {
            "name": "azu_notify",
            "description": "梓频重要通知",
            "guildId": 36667731636792997,
            "channelId": 1365147
         },
         {
            "name": "azu_dynamic",
            "description": "梓频动态",
            "guildId": 36667731636792997,
            "channelId": 1400120
         },
         {
            "name": "xiaoke_sc",
            "description": "小可频SC",
            "guildId": 19995411637241900,
            "channelId": 1542326
         },
         {
            "name": "xiaoke_dynamic",
            "description": "小可频动态",
            "guildId": 19995411637241900,
            "channelId": 1468457
         },
         {
            "name": "xiaoke_notify",
            "description": "小可频通知",
            "guildId": 19995411637241900,
            "channelId": 1467859
         }
      ],
      "superChatConfig": [ //superChat 通知
         {
            "key": "21452505", //主播直播间房间号
            "channelName": "nanami_sc" //根据上面配置的channel 的name
         },
         {
            "key": "510", //同步到多个子频道，请配置多条
            "channelName": "azu_live_topic"
         },
         {
            "key": "510", //同步到多个子频道，请配置多条
            "channelName": "azu_archives"
         },
         {
            "key": "605",
            "channelName": "xiaoke_sc"
         }
      ],
      "ybbTranConfig": [ //一个ybb小游戏 ，这里配置子频道，表示仅能在限定子频道使用。如果不配置，则所有子频道都可以使用
         "nanami_ybb",
         "azu_ybb"
      ],
      "weiboConfig": [ //微博同步 配置，key为微博用户uid
         {
            "key": "7198559139",
            "channelName": "nanami_dynamic"
         },
         {
            "key": "2203177060",
            "channelName": "azu_dynamic"
         },
         {
            "key": "6377117491",
            "channelName": "xiaoke_dynamic"
         },
         {
            "key": "2085108062",
            "channelName": "nanami_ybb"
         }
      ],
      "bililiverConfig": [ //开播提醒 配置，key为B站主播uid（不是房间号！）
         {
            "key": "434334701",
            "channelName": "nanami_notify"
         },
         {
            "key": "14387072",
            "channelName": "xiaoke_notify"
         },
         {
            "key": "777656",
            "channelName": "nanami_ybb"
         }
      ],
      "jobConfig": { // 这里配置一些定时任务的 参数
         "WeiboScheduleJob": { // 微博同步job
            // 微博同步需要配置cookie
            "cookie": ""
         }
      }
   }
   ```
   2. 获取微博cookie [`获取cookie教程`](https://github.com/dataabc/weiboSpider/blob/master/docs/cookie.md)
   3. 获取微博uid [`获取uid教程`](https://github.com/dataabc/weiboSpider/blob/master/docs/userid.md)
4. 后续版本如果添加了新功能，可能会对config结构产生变化，请在备份原config的情况下按照文档构建新的config
# 二次开发注意事项
1. 本项目依赖[`wsure-bililiver`](https://github.com/WsureDev/wsure-bililiver) 一个自己手搓的直播ws链接工具，请在二开时同时clone此项目
2. 打包使用`shadowJar`
3. 后续会迁移至官方私域bot，届时将会对项目结构进行较大调整