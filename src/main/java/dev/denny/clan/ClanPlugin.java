package dev.denny.clan;

import cn.nukkit.plugin.PluginBase;
import dev.denny.clan.command.ClanCommand;
import dev.denny.clan.manager.ClanManager;
import lombok.Getter;

public class ClanPlugin extends PluginBase {

    @Getter
    public static ClanPlugin instance;

    @Getter
    private ClanManager manager;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        instance = this;
        manager = new ClanManager();

        getServer().getCommandMap().register("", new ClanCommand());
    }
}