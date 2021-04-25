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
package com.djrapitops.extension;

import com.djrapitops.plan.capability.CapabilityService;
import com.djrapitops.plan.extension.Caller;
import com.djrapitops.plan.extension.DataExtension;

import java.util.Optional;

/**
 * Factory for DataExtension.
 *
 * @author AuroraLS3
 */
public class AdvancedBanExtensionFactory {

    private boolean isAvailable(String className) {
        try {
            Class.forName(className);
            return CapabilityService.getInstance().hasCapability("DATA_EXTENSION_SHOW_IN_PLAYER_TABLE");
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private boolean isAvailable() {
        return isAvailable("me.leoko.advancedban.Universal");
    }

    public Optional<DataExtension> createExtension() {
        if (isAvailable()) {
            return Optional.of(new AdvancedBanExtension());
        }
        return Optional.empty();
    }

    public void registerListener(Caller caller) {
        // Additional classes used to avoid NoClassDefFoundErrors
        if (isAvailable("org.bukkit.event.EventHandler")) {
            ABBukkitListenerFactory.createBukkitListener(caller).register();
        }
        if (isAvailable("net.md_5.bungee.event.EventHandler")) {
            ABBungeeListenerFactory.createBungeeListener(caller).register();
        }
    }
}