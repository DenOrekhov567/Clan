package dev.denny.clan.utils;

import cn.nukkit.Player;
import dev.denny.database.DatabasePlugin;
import lombok.Getter;

import java.util.List;

public class ClanData {

    @Getter
    public Integer id;

    @Getter
    public String name;

    @Getter
    public String startDate;

    @Getter
    public Integer level;

    @Getter
    public Integer experience;

    @Getter
    public Integer balance;

    @Getter
    public Integer wins;

    @Getter
    public Integer loses;

    public Boolean isOwner(Player player) {

        String request = "SELECT * FROM clan_members WHERE LOWER(name) = LOWER('%1$s') AND LOWER(player) = LOWER('%2$s') AND permission = 'owner';";
        request = String.format(request, name, player.getName());

        return DatabasePlugin.getDatabase().query(request, MemberData.class) != null;
    }

    public void addExperience(Integer count) {
        String request = "UPDATE clans SET experience = %2$s WHERE LOWER(name) = LOWER('%1$s');";
        request = String.format(request, name, experience + count);

        DatabasePlugin.getDatabase().query(request);
    }

    public MemberData getMember(Player player) {
        String request = "SELECT * FROM clan_members WHERE LOWER(name) = LOWER('%1$s') AND LOWER(player) = LOWER('%2$s');";
        request = String.format(request, name, player.getName());

        List<MemberData> response = DatabasePlugin.getDatabase().query(request, MemberData.class);

        return response != null ? response.get(0) : null;
    }

    public MemberData getMember(String player) {
        String request = "SELECT * FROM clan_members WHERE LOWER(name) = LOWER('%1$s') AND LOWER(player) = LOWER('%2$s');";
        request = String.format(request, name, player);

        List<MemberData> response = DatabasePlugin.getDatabase().query(request, MemberData.class);

        return response != null ? response.get(0) : null;
    }

    public List<MemberData> getMembers() {
        String request = "SELECT * FROM clan_members WHERE LOWER(name) = LOWER('%1$s');";
        request = String.format(request, name);

        return DatabasePlugin.getDatabase().query(request, MemberData.class);
    }

    public void addMember(Player player) {
        String request = "INSERT INTO clan_members(name, player, permission) VALUES ('%1$s', '%2$s', 'member');";
        request = String.format(request, name, player.getName().toLowerCase());

        DatabasePlugin.getDatabase().query(request);
    }

    public void addMoney(int count) {
        String request = "UPDATE clans SET balance = %2$s WHERE LOWER(name) = LOWER('%1$s');";
        request = String.format(request, name, balance + count);

        DatabasePlugin.getDatabase().query(request);
    }

    public void delete() {
        String request = "DELETE FROM clans WHERE LOWER(name) = LOWER('%1$s')";
        request = String.format(request, name);

        List<MemberData> members = getMembers();
        for (MemberData member : members) {
            member.delete();
        }

        DatabasePlugin.getDatabase().query(request);
    }

    public Boolean isFull() {
        List<MemberData> members = getMembers();

        return !members.isEmpty() && members.size() >= 10 ? true : false;
    }
}