package dev.denny.clan.command.argument;

import cn.nukkit.command.CommandSender;
import lombok.Getter;

abstract public class Argument {

    @Getter
    public String name;

    @Getter
    public Integer countPostArgs;

    @Getter
    public String prefixResponse = "§f[§aКланы§f] ";

    public abstract String getName();

    public abstract Boolean execute(CommandSender sender, String[] args);

    public Argument setCountPostArgs(Integer count) {
        countPostArgs = count;

        return this;
    }
}