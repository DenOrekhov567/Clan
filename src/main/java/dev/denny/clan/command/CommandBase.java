package dev.denny.clan.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import dev.denny.clan.command.argument.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandBase extends Command {

    private List<Argument> argumentList;

    public CommandBase(String name, String disctiption, String usage) {
        super(name, disctiption, usage);

        getCommandParameters().clear();

        /*
         * accept player +
         * create string +
         * delete void +
         * info void|string ++
         * invite player +
         * leave void +
         * pay integer
         * kick player|string ++
         */

        commandParameters.put("parameter1", new CommandParameter[]{
                CommandParameter.newEnum(
                        "команда", new CommandEnum("command1", "accept", "invite", "kick")
                ),
                new CommandParameter("игрок", CommandParamType.TARGET, true)
                }
        );
        commandParameters.put("parameter2",
                new CommandParameter[]{
                    CommandParameter.newEnum(
                            "команда", new CommandEnum("command2", "create", "info")
                    ),
                    new CommandParameter("название", CommandParamType.STRING, true)
                }
        );
        commandParameters.put("parameter3",
                new CommandParameter[]{
                        CommandParameter.newEnum(
                                "команда", new CommandEnum("command3", "kick")
                        ),
                        new CommandParameter("игрок", CommandParamType.STRING, true)
                }
        );
        commandParameters.put("parameter4", new CommandParameter[]{
                CommandParameter.newEnum(
                        "команда", new CommandEnum("command4", "info", "delete", "leave")
                )
        });
        commandParameters.put("parameter5", new CommandParameter[]{
                CommandParameter.newEnum(
                        "команда", new CommandEnum("command5", "pay")
                ),
                new CommandParameter("сумма", CommandParamType.INT, true)
        });

        argumentList = new ArrayList<>();
        argumentList.add(new CreateArgument().setCountPostArgs(1));
        argumentList.add(new DeleteArgument().setCountPostArgs(0));
        argumentList.add(new InfoArgument().setCountPostArgs(0));
        argumentList.add(new InviteArgument().setCountPostArgs(1));
        argumentList.add(new AcceptArgument().setCountPostArgs(1));
        argumentList.add(new PayArgument().setCountPostArgs(1));
        argumentList.add(new LeaveArgument().setCountPostArgs(1));
        argumentList.add(new KickArgument().setCountPostArgs(1));
    }

    private Argument getArgument(String name) {
        name = name.toLowerCase();
        for (Argument argument : argumentList) {
            if (!argument.getName().equals(name)) {
                continue;
            }
            return argument;
        }
        return null;
    }

    public Boolean executeSafe(CommandSender sender, String s, String[] args) {
        if (!sender.isPlayer()) {
            sender.sendMessage("§7> §fИспользовать можно только игроку");
            return false;
        }

        if(!sender.hasPermission(getPermission())) {
            sender.sendMessage("§7> §fНедостаточно прав");
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage(getUsage());

            return false;
        }

        Argument argument = getArgument(args[0]);

        if (argument == null) {
            sender.sendMessage(getUsage());

            return false;
        }

        String[] postArgs = Arrays.stream(args).skip(1).toArray(String[]::new);

        if(postArgs.length < argument.getCountPostArgs()) {
            sender.sendMessage("§7> §fДля команды §a" + argument.getName() + " §fтребуются еще аргументы");

            return false;
        }
        return argument.execute(sender, postArgs);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return executeSafe(commandSender, s, strings);
    }
}