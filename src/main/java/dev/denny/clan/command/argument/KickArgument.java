package dev.denny.clan.command.argument;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import dev.denny.clan.ClanPlugin;
import dev.denny.clan.manager.ClanManager;
import dev.denny.clan.utils.ClanData;
import lombok.Getter;

public class KickArgument extends Argument {

    @Getter
    public String name = "kick";

    @Override
    public Boolean execute(CommandSender _sender, String[] args) {
        Player sender = (Player) _sender;

        ClanManager manager = ClanPlugin.getInstance().getManager();
        ClanData clan = manager.getClan(sender);

        if(clan == null) {
            sender.sendMessage(getPrefixResponse() + "§aТы §fне состоишь в клане...");

            return false;
        }

        if (!clan.isOwner(sender)) {
            sender.sendMessage(getPrefixResponse() + "§fТы не владелец клана");

            return false;
        }

        String recipientName = args[0];
        Player recipient = sender.getServer().getPlayer(recipientName);

        if(recipientName.toLowerCase().equals(sender.getName())) {
            sender.sendMessage(getPrefixResponse() + "§aТы §fне можешь выгнать из клана §aсебя");

            return false;
        }

        ClanData clanRecipient;
        if(recipient == null) {
             clanRecipient = manager.getClan(recipientName);

            if(clanRecipient == null || !clan.getName().equals(clanRecipient.getName())) {
                sender.sendMessage(getPrefixResponse() + "§a" + recipientName + "§f не состоит в твоем клане");

                return false;
            }

            clan.getMember(recipientName).delete();
            sender.sendMessage(getPrefixResponse() + "§aТы §fвыгнал §a" + recipientName + " §fиз клана");

            return true;
        }


        clanRecipient = manager.getClan(recipient);

        if(clanRecipient == null || !clan.getName().equals(clanRecipient.getName())) {
            sender.sendMessage(getPrefixResponse() + "§a" + recipient.getName() + "§f не состоит в твоем клане");

            return false;
        }

        clan.getMember(recipient).delete();
        sender.sendMessage(getPrefixResponse() + "§aТы §fвыгнал §a" + recipient.getName() + " §fиз клана");
        recipient.sendMessage(getPrefixResponse() + "§a" + sender.getName() + " §fвыгнал §aтебя §fиз клана");

        return true;
    }
}