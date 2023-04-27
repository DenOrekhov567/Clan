package dev.denny.clan;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import dev.denny.clan.manager.ClanManager;
import dev.denny.clan.utils.ClanData;

public class EventListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        //Если событие битвы существа и существа, то
        if(event instanceof EntityDamageByEntityEvent) {
            Entity victum = event.getEntity();
            Entity killer = ((EntityDamageByEntityEvent) event).getDamager();

            if(victum.getHealth() <= event.getDamage() && killer != null && killer instanceof Player) {
                ClanManager manager = ClanPlugin.getInstance().getManager();
                ClanData clanKiller = manager.getClan((Player) killer);
                if (victum instanceof Player) {
                    ClanData clanVictum = manager.getClan((Player) victum);

                    if (clanKiller.getName().equals(clanVictum.getName())) {
                        return;
                    }
                    clanKiller.addMoney(5);
                    clanKiller.addExperience(5);

                    return;
                }
                clanKiller.addMoney(1);
                clanKiller.addExperience(1);
            }
        }
    }
}