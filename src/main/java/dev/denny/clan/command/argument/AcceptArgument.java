package dev.denny.clan.command.argument;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import dev.denny.clan.ClanPlugin;
import dev.denny.clan.manager.ClanManager;
import dev.denny.clan.utils.ClanData;
import lombok.Getter;

public class AcceptArgument extends Argument {

    @Getter
    public String name = "accept";

    @Override
    public Boolean execute(CommandSender _sender, String[] args) {
        Player sender = (Player) _sender;

        if (args.length < 1) {
            sender.sendMessage(getPrefixResponse() + "§fИспользовать: §a/clan accept <игрок:список>");

            return false;
        }

        ClanManager manager = ClanPlugin.getInstance().getManager();

        if(manager.getClan(sender) != null) {
            sender.sendMessage(getPrefixResponse() + "§fТы уже состоишь в клане");

            return false;
        }

        String recipientName = args[0];

        //От кого запрос
        Player from = sender.getServer().getPlayer(recipientName);

        if(from == null) {
            sender.sendMessage(getPrefixResponse() + "§fПриглашение в клан недоступно, §a" + recipientName + " §fне в сети");

            return false;
        }

        if (manager.getInviteRequestBetween(sender, from) == null) {
            sender.sendMessage(getPrefixResponse() + "§fНет приглашения в клан от §a" + from.getName());

            return false;
        }

        ClanData clan = manager.getClan(from);
        if(clan == null) {
            sender.sendMessage(getPrefixResponse() + "§fПриглашение в клан недоступно, §a" + from.getName() + " §fвышел из клана");

            return false;
        }

        clan.addMember(sender);
        manager.removeInviteRequestBetween(from, sender);

        from.sendMessage(getPrefixResponse() + "§a" + sender.getName() + " §fвступил в клан");
        sender.sendMessage(getPrefixResponse() + "§aТы §fвступил в клан §a" + clan.getName());

        return true;
    }
}