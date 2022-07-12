/*
    Copyright(c) 2021 AuroraLS3

    The MIT License(MIT)

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files(the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions :
    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
*/
package net.playeranalytics.extension.advancedban;

import com.djrapitops.plan.extension.Caller;
import me.leoko.advancedban.bungee.event.PunishmentEvent;
import me.leoko.advancedban.bungee.event.RevokePunishmentEvent;
import me.leoko.advancedban.utils.Punishment;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

public class AdvancedBanBungeeABListener implements ABListener, Listener {

    private final Caller caller;

    public AdvancedBanBungeeABListener(Caller caller) {
        this.caller = caller;
    }

    @Override
    public void register() {
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        Plugin plugin = pluginManager.getPlugin("Plan");
        pluginManager.registerListener(plugin, this);
    }

    @EventHandler
    public void onPunish(PunishmentEvent event) {
        Punishment punishment = event.getPunishment();
        caller.updatePlayerData(null, punishment.getName());
    }

    @EventHandler
    public void onRevoke(RevokePunishmentEvent event) {
        Punishment punishment = event.getPunishment();
        caller.updatePlayerData(null, punishment.getName());
    }
}