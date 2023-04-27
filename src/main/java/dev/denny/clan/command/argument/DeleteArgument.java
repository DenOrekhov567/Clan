package dev.denny.clan.command.argument;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import dev.denny.clan.ClanPlugin;
import dev.denny.clan.utils.ClanData;
import lombok.Getter;

public class DeleteArgument extends Argument {

    @Getter
    public final String name = "delete";

    @Override
    public Boolean execute(CommandSender _sender, String[] args) {
        Player sender = (Player) _sender;
        ClanData clan = ClanPlugin.getInstance().getManager().getClan(sender);
        if(clan == null) {
            sender.sendMessage(getPrefixResponse() + "§aТы §fне состоишь в клане");

            return false;
        }

        if(!clan.isOwner(sender)) {
            sender.sendMessage(getPrefixResponse() + "§aТы §fне владелец клана");

            return false;
        }

        clan.delete();
        sender.sendMessage(getPrefixResponse() + "§fКлан §a" + clan.getName() + " §fбыл удален");

        return true;
    }
}