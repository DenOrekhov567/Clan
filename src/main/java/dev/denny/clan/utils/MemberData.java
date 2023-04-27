package dev.denny.clan.utils;

import dev.denny.database.DatabasePlugin;
import lombok.Getter;

public class MemberData {


    @Getter
    public Integer id;

    @Getter
    public String name;

    @Getter
    public String player;

    @Getter
    public String permission;

    public void delete() {
        String request = "DELETE FROM clan_members WHERE LOWER(player) = LOWER('%1$s')";
        request = String.format(request, player);

        DatabasePlugin.getDatabase().query(request);
    }
}