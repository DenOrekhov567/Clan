package dev.denny.clan.command.argument;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import dev.denny.clan.ClanPlugin;
import dev.denny.clan.manager.ClanManager;
import dev.denny.clan.utils.ClanData;
import lombok.Getter;

public class InviteArgument extends Argument {

    @Getter
    public String name = "invite";

    @Override
    public Boolean execute(CommandSender _sender, String[] args) {
        Player sender = (Player) _sender;

        ClanManager manager = ClanPlugin.getInstance().getManager();
        ClanData clan = manager.getClan(sender);

        if(clan == null) {
            sender.sendMessage(getPrefixResponse() + "§aТы §fне состоишь в клане");

            return false;
        }

        if (!clan.isOwner(sender)) {
            sender.sendMessage(getPrefixResponse() + "§fТы не владелец клана");

            return false;
        }

        String recipientName = args[0];
        Player recipient = sender.getServer().getPlayer(recipientName);

        if(recipientName.toLowerCase().equals(sender.getName())) {
            sender.sendMessage(getPrefixResponse() + "§aТы §fне можешь отправить приглашение §aсебе");

            return false;
        }

        if(recipient == null) {
            sender.sendMessage(getPrefixResponse() + "§a" + recipientName + " §fне в сети");

            return false;
        }

        ClanData clanRecipient = manager.getClan(recipient);

        if(clanRecipient.getName().equals(manager.getClan(sender).getName())) {
            sender.sendMessage(getPrefixResponse() + "§a" + recipient.getName() + " §fуже в §aтвоем §fклане");

            return false;
        }

        if(clanRecipient != null) {
            sender.sendMessage(getPrefixResponse() + "§a" + recipient.getName() + " §fуже состоит в другом клане");

            return false;
        }

        if(clan.isFull()) {
            sender.sendMessage(getPrefixResponse() + "§fКлан §aпереполнен");

            return false;
        }

        manager.requestInvite(sender, recipient);

        sender.sendMessage(getPrefixResponse() + "§aТы §fотправил приглашение §fигроку §a" + recipient.getName());
        recipient.sendMessage(getPrefixResponse() + "§а" + sender.getName() + " §fотправил тебе приглашение");

        return true;
    }
}