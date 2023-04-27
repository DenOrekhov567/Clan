package dev.denny.clan.manager;

import cn.nukkit.Player;
import dev.denny.clan.utils.ClanData;
import dev.denny.clan.utils.InviteRequest;
import dev.denny.clan.utils.MemberData;
import dev.denny.database.DatabasePlugin;
import dev.denny.database.utils.Database;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClanManager {

    @Getter
    public DatabaseManager databaseManager;

    private Map<Integer, InviteRequest> poolRequests = new ConcurrentHashMap<>();

    public ClanManager() {
        DatabaseManager.initTables();
    }

    public boolean isValidName(String name) {
        return name.matches("^[a-zA-Z0-9_]+$");
    }

    public ClanData getClan(Player player) {
        Database database = DatabasePlugin.getDatabase();

        String request = String.format("SELECT * FROM clan_members WHERE LOWER(player) = LOWER('%1$s')", player.getName());
        List<MemberData> memberData = database.query(request, MemberData.class);
        if(memberData != null) {
            request = "SELECT * FROM clans WHERE LOWER(name) = LOWER('%1$s')";
            request = String.format(request, memberData.get(0).getName());

            return database.query(request, ClanData.class).get(0);
        }
        return null;
    }

    public ClanData getClan(String name) {
        String request = "SELECT * FROM clans WHERE LOWER(name) = LOWER('%1$s');";
        request = String.format(request, name);

        List<ClanData> response = DatabasePlugin.getDatabase().query(request, ClanData.class);
        return response != null ? response.get(0) : null;
    }

    public boolean isClanNameExists(String name) {
        String request = "SELECT * FROM clans WHERE LOWER(name) = LOWER('%1$s');";
        request = String.format(request, name);

        return DatabasePlugin.getDatabase().query(request, ClanData.class) != null;
    }

    public void createClan(Player player, String name) {
        Database database = DatabasePlugin.getDatabase();

        String request = "INSERT INTO clans(name, startDate, level, experience, balance, wins, loses) VALUES ('%1$s', '%2$s', %3$s, %4$s, %5$s, %6$s, %7$s);";
        request = String.format(request, name, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), 1, 0, 0, 0, 0);

        database.query(request);

        request = "INSERT INTO clan_members(name, player, permission) VALUES ('%1$s', '%2$s', '%3$s');";
        request = String.format(request, name, player.getName(), "owner");

        database.query(request);
    }

    private static int getHashCode(Player from, Player to) {
        return from.hashCode() + to.hashCode();
    }

    public void requestInvite(Player from, Player to) {
        poolRequests.put(getHashCode(from, to), new InviteRequest(System.currentTimeMillis(), from, to));
    }

    public InviteRequest getInviteRequestBetween(Player from, Player to) {
        int key;
        if (poolRequests.containsKey(key = getHashCode(from, to))) {
            return poolRequests.get(key);
        }
        return null;
    }

    public void removeInviteRequestBetween(Player from, Player to) {
        poolRequests.remove(getHashCode(from, to));
    }
}