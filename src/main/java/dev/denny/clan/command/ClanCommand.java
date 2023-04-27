package dev.denny.clan.command;

import cn.nukkit.command.CommandSender;
import dev.denny.pattern.PatternPlugin;

public class ClanCommand extends CommandBase {

    public ClanCommand() {
        super(
                "clan",
             "Кланы",
                "§7> §fИспользовать: §a/clan <команда> <аргумент или пусто>"
        );

        setPermission("command.clan.use");
        PatternPlugin.getInstance().getManager().addToPermissionList(getPermission(), 0);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return executeSafe(commandSender, s, strings);
    }
}