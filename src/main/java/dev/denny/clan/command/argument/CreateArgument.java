package dev.denny.clan.command.argument;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import dev.denny.clan.ClanPlugin;
import dev.denny.clan.manager.ClanManager;
import lombok.Getter;

public class CreateArgument extends Argument {

    @Getter
    public final String name = "create";

    @Override
    public Boolean execute(CommandSender _sender, String[] args) {
        Player sender = (Player) _sender;

        if (args.length < 1) {
            sender.sendMessage(getPrefixResponse() + "§fИспользовать: §a/clan create <название:слово>");

            return false;
        }

        String clanName = args[0];
        ClanManager manager = ClanPlugin.getInstance().getManager();

        if(manager.getClan(sender) != null) {
            sender.sendMessage(getPrefixResponse() + "§aТы §fуже состоишь в клане");

            return false;
        }

        if(clanName.length() > 8) {
            sender.sendMessage(getPrefixResponse() + "§fДлина названия клана не должна превышать 8 символов");

            return false;
        }

        if(!manager.isValidName(clanName)) {
            sender.sendMessage(getPrefixResponse() + "§fВ названии клана могут быть только §aбуквы§f, §aцифры §fи §aнижние подчеркивания");

            return false;
        }

        if(manager.isClanNameExists(clanName)) {
            sender.sendMessage(getPrefixResponse() + "§fКлан §a" + clanName + " §fуже существует");

            return false;
        }

        manager.createClan(sender, clanName);

        sender.sendMessage(getPrefixResponse() + "§fКлан §а" + clanName + " §fсоздан");
        return true;
    }
}