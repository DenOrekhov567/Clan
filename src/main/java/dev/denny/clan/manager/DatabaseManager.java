package dev.denny.clan.manager;

import dev.denny.database.DatabasePlugin;
import dev.denny.database.utils.Database;

public class DatabaseManager {

    public static void initTables() {
        Database database = DatabasePlugin.getDatabase();

        String request = "CREATE TABLE IF NOT EXISTS clans(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(6) NOT NULL, " +
                "startDate VARCHAR(20) NOT NULL, " +
                "level INTEGER NOT NULL, " +
                "experience INTEGER NOT NULL, " +
                "balance INTEGER NOT NULL, " +
                "wins INTEGER NOT NULL, " +
                "loses INTEGER NOT NULL" +
                ");";
        database.executeScheme(request);

        request = "CREATE TABLE IF NOT EXISTS clan_members(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(6) NOT NULL, " +
                "player VARCHAR(15) NOT NULL, " +
                "permission VARCHAR(10) NOT NULL" +
                ");";
        database.executeScheme(request);
    }
}