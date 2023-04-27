package dev.denny.clan.utils;

import cn.nukkit.Player;
import lombok.Getter;

public class InviteRequest {

    @Getter
    private final long requestTime;

    @Getter
    private final Player sender;

    @Getter
    private final Player recipient;

    public InviteRequest(long _requestTime, Player _sender, Player _recipient) {
        requestTime = _requestTime;
        sender = _sender;
        recipient = _recipient;
    }
}