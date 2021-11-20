package bot.tx.wsure.top.official.intf

import bot.tx.wsure.top.official.dtos.event.AtMessageCreateEvent

abstract class OfficialBotEvent {

    open suspend fun onAtMessageCreate(data: AtMessageCreateEvent){

    }
    //TODO 官方有许多事件，后续在这里添加事件名称

}