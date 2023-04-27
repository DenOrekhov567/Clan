package dev.denny.clan.command.argument;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import dev.denny.clan.ClanPlugin;
import dev.denny.clan.manager.ClanManager;
import dev.denny.clan.utils.ClanData;
import dev.denny.account.player.Account;
import dev.denny.account.player.Gamer;
import lombok.Getter;

public class PayArgument extends Argument {

    @Getter
    public String name = "pay";

    @Override
    public Boolean execute(CommandSender _sender, String[] args) {
        Player sender = (Player) _sender;

        if (args.length < 1) {
            sender.sendMessage(getPrefixResponse() + "§fИспользовать: §a/clan pay <количество:число>");

            return false;
        }

        String sum = args[0];
        ClanManager manager = ClanPlugin.getInstance().getManager();
        ClanData clan = manager.getClan(sender);

        if(clan == null) {
            sender.sendMessage(getPrefixResponse() + "§aТы §fне состоишь в клане");

            return false;
        }


        Integer count = null;
        try {
            count = Integer.parseInt(sum);
        } catch (NumberFormatException exception) {
            sender.sendMessage(getPrefixResponse() + "§fКоличество монет может быть только §aчислом");
            return false;
        }

        Account account = ((Gamer) sender).getAccount();
        if(account.getData().getCoins() <= count) {
            sender.sendMessage(getPrefixResponse() + "§fУ §aтебя §fнедостаточно монет");
            return false;
        }
        ((Gamer) sender).getServer().getLogger().emergency(String.valueOf(account.getData().coins));
        account.getData().coins -= count;
        account.update("coins");
        clan.addMoney(count);

        sender.sendMessage(getPrefixResponse() + "§aТы §fпожертвовал §a" + sum + " §fмонет клану §a" + clan.getName());
        return true;
    }
}