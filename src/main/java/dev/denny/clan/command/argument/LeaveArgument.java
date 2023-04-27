package dev.denny.clan.command.argument;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import dev.denny.clan.ClanPlugin;
import dev.denny.clan.manager.ClanManager;
import dev.denny.clan.utils.ClanData;
import lombok.Getter;

public class LeaveArgument extends Argument {

    @Getter
    public String name = "leave";

    @Override
    public Boolean execute(CommandSender _sender, String[] args) {
        Player sender = (Player) _sender;

        ClanManager manager = ClanPlugin.getInstance().getManager();
        ClanData clan = manager.getClan(sender);

        if(clan == null) {
            sender.sendMessage(getPrefixResponse() + "§aТы §fне состоишь в клане");

            return false;
        }

        if(clan.isOwner((sender))) {
            sender.sendMessage(getPrefixResponse() + "§aТы §fне можешь выйти из клана, так как ты его владелец");

            return false;
        }

        clan.getMember(sender).delete();

        sender.sendMessage(getPrefixResponse() + "§aТы §fвышел из клана §a" + clan.getName());
        return true;
    }
}