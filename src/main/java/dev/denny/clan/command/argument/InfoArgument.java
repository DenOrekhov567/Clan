package dev.denny.clan.command.argument;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import dev.denny.clan.ClanPlugin;
import dev.denny.clan.utils.ClanData;
import dev.denny.clan.utils.MemberData;
import lombok.Getter;

import java.util.List;

public class InfoArgument extends Argument {

    @Getter
    public final String name = "info";

    @Override
    public Boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            ClanData clan = ClanPlugin.getInstance().getManager().getClan((Player) sender);
            if (clan == null) {
                sender.sendMessage(getPrefixResponse() + "§aТы §fне состоишь в клане");

                return false;
            }

            String allMemberNames = "";
            List<MemberData> list = clan.getMembers();
            StringBuilder sb = new StringBuilder();
            Integer count = 1;
            for (MemberData member : list) {
                String permission = member.getPermission();
                String entry = "";
                if (permission.equals("owner")) {
                    entry = "\n§f" + String.valueOf(count) + ". §a" + member.getPlayer() + " §f— Владелец, ";
                    sb.insert(0, entry); // добавляем строку в начало списка
                } else {
                    entry = "\n§f" + String.valueOf(count) + ". §7" + member.getPlayer() + " §f— Участник, ";
                    sb.append(entry); // добавляем строку в конец списка
                }
                count++;
            }
            allMemberNames = sb.toString().trim();
            allMemberNames = allMemberNames.substring(0, allMemberNames.length() - 1); // удаляем последнюю запятую

            sender.sendMessage(
                 getPrefixResponse() + "§fИнформация §aтвоего §fклана\n" +
                    "§7• §fНазвание — §a" + clan.getName() + "\n" +
                    "§7• §fДата создания — §a" + clan.getStartDate() + "\n" +
                    "§7• §fУровень — §a" + clan.getLevel() + "\n" +
                    "§7• §fОпыт — §a" + clan.getExperience() + "\n" +
                    "§7• §fБаланс — §a" + clan.getBalance() + "\n" +
                    "§7• §fПобед — §a" + clan.getWins() + "\n" +
                    "§7• §fПоражений — §a" + clan.getLoses() + "\n" +
                    "§7• §fУчастники: §a\n" + allMemberNames
            );
            return true;
        }

        String clanName = args[0];

        ClanData clan = ClanPlugin.getInstance().getManager().getClan(clanName);
        if (clan == null) {
            sender.sendMessage(getPrefixResponse() + "§fКлан §a" + clanName + " §fне существует");

            return false;
        }

        String allMemberNames = "";
        List<MemberData> list = clan.getMembers();
        StringBuilder sb = new StringBuilder();
        Integer count = 1;
        for (MemberData member : list) {
            String permission = member.getPermission();
            String entry = "";
            if (permission.equals("owner")) {
                entry = "\n§f" + String.valueOf(count) + ". §a" + member.getPlayer() + " §f— Владелец, ";
                sb.insert(0, entry); // добавляем строку в начало списка
            } else {
                entry = "\n§f" + String.valueOf(count) + ". §7" + member.getPlayer() + " §f— Участник, ";
                sb.append(entry); // добавляем строку в конец списка
            }
            count++;
        }
        allMemberNames = sb.toString().trim();
        allMemberNames = allMemberNames.substring(0, allMemberNames.length() - 1); // удаляем последнюю запятую

        sender.sendMessage(
            getPrefixResponse() + "Информация о §a" + clanName + "\n" +
               "§7• §fНазвание — §a" + clan.getName() + "\n" +
               "§7• §fДата создания — §a" + clan.getStartDate() + "\n" +
               "§7• §fУровень — §a" + clan.getLevel() + "\n" +
               "§7• §fОпыт — §a" + clan.getExperience() + "\n" +
               "§7• §fБаланс — §a" + clan.getBalance() + "\n" +
               "§7• §fПобед — §a" + clan.getWins() + "\n" +
               "§7• §fПоражений — §a" + clan.getLoses() + "\n" +
               "§7• §fУчастники: §a\n" + allMemberNames
        );
        return true;
    }
}