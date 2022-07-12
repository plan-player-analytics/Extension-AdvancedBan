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

import com.djrapitops.plan.extension.CallEvents;
import com.djrapitops.plan.extension.DataExtension;
import com.djrapitops.plan.extension.FormatType;
import com.djrapitops.plan.extension.NotReadyException;
import com.djrapitops.plan.extension.annotation.*;
import com.djrapitops.plan.extension.icon.Color;
import com.djrapitops.plan.extension.icon.Family;
import me.leoko.advancedban.manager.PunishmentManager;
import me.leoko.advancedban.manager.UUIDManager;
import me.leoko.advancedban.utils.PunishmentType;

/**
 * DataExtension for AdvancedBan plugin.
 * <p>
 * Adapted from PluginData implementation by Vankka.
 *
 * @author AuroraLS3
 */
@PluginInfo(name = "AdvancedBan", iconName = "gavel", iconFamily = Family.SOLID, color = Color.RED)
public class AdvancedBanExtension implements DataExtension {

    public AdvancedBanExtension() {
    }

    @Override
    public CallEvents[] callExtensionMethodsOn() {
        return new CallEvents[]{
                CallEvents.PLAYER_JOIN, CallEvents.PLAYER_LEAVE
        };
    }

    private String abUUID(String playerName) {
        return UUIDManager.get().getUUID(playerName);
    }

    private PunishmentManager getPunishmentManager() {
        try {
            return PunishmentManager.get();
        } catch (Exception e) {
            throw new NotReadyException();
        }
    }

    @BooleanProvider(
            text = "Banned",
            description = "Is the player banned on AdvancedBan",
            priority = 100,
            conditionName = "banned",
            iconName = "gavel",
            iconColor = Color.RED
    )
    public boolean isBanned(String playerName) {
        return getPunishmentManager().isBanned(abUUID(playerName));
    }

    @Conditional("banned")
    @StringProvider(
            text = "Operator",
            description = "Who banned the player",
            priority = 99,
            iconName = "user",
            iconColor = Color.RED,
            playerName = true
    )
    public String banIssuer(String playerName) {
        return getPunishmentManager().getBan(abUUID(playerName)).getOperator();
    }

    @Conditional("banned")
    @NumberProvider(
            text = "Date",
            description = "When the ban was issued",
            priority = 98,
            iconName = "calendar",
            iconFamily = Family.REGULAR,
            iconColor = Color.RED,
            format = FormatType.DATE_YEAR
    )
    public long banIssueDate(String playerName) {
        return getPunishmentManager().getBan(abUUID(playerName)).getStart();
    }

    @Conditional("banned")
    @BooleanProvider(
            text = "Will Expire",
            description = "Is the ban permanent",
            priority = 97,
            conditionName = "ban_expires",
            iconName = "calendar-check",
            iconFamily = Family.REGULAR,
            iconColor = Color.RED
    )
    public boolean banWillExpire(String playerName) {
        PunishmentType type = getPunishmentManager().getBan(abUUID(playerName)).getType();
        return !(type == PunishmentType.BAN
                || type == PunishmentType.IP_BAN
                || type == PunishmentType.MUTE);
    }

    @Conditional("ban_expires")
    @NumberProvider(
            text = "Ends",
            description = "When the ban expires",
            priority = 96,
            iconName = "calendar-check",
            iconFamily = Family.REGULAR,
            iconColor = Color.RED,
            format = FormatType.DATE_YEAR
    )
    public long banExpireDate(String playerName) {
        return getPunishmentManager().getBan(abUUID(playerName)).getEnd();
    }

    @Conditional("banned")
    @StringProvider(
            text = "Reason",
            description = "Why the ban was issued",
            priority = 95,
            iconName = "comment",
            iconFamily = Family.REGULAR,
            iconColor = Color.RED
    )
    public String banReason(String playerName) {
        return getPunishmentManager().getBan(abUUID(playerName)).getReason();
    }

    @BooleanProvider(
            text = "Muted",
            description = "Is the player muted on AdvancedBan",
            priority = 50,
            conditionName = "muted",
            iconName = "bell-slash",
            iconColor = Color.DEEP_ORANGE,
            showInPlayerTable = true
    )
    public boolean isMuted(String playerName) {
        return getPunishmentManager().isMuted(abUUID(playerName));
    }

    @Conditional("muted")
    @StringProvider(
            text = "Operator",
            description = "Who muted the player",
            priority = 49,
            iconName = "user",
            iconColor = Color.DEEP_ORANGE
    )
    public String muteIssuer(String playerName) {
        return getPunishmentManager().getMute(abUUID(playerName)).getOperator();
    }

    @Conditional("muted")
    @NumberProvider(
            text = "Date",
            description = "When the mute was issued",
            priority = 48,
            iconName = "calendar",
            iconFamily = Family.REGULAR,
            iconColor = Color.DEEP_ORANGE,
            format = FormatType.DATE_YEAR
    )
    public long muteIssueDate(String playerName) {
        return getPunishmentManager().getMute(abUUID(playerName)).getStart();
    }

    @Conditional("muted")
    @BooleanProvider(
            text = "Will Expire",
            description = "Is the mute permanent",
            priority = 47,
            conditionName = "mute_expires",
            iconName = "calendar-check",
            iconFamily = Family.REGULAR,
            iconColor = Color.DEEP_ORANGE
    )
    public boolean muteWillExpire(String playerName) {
        PunishmentType type = getPunishmentManager().getMute(abUUID(playerName)).getType();
        return !(type == PunishmentType.BAN
                || type == PunishmentType.IP_BAN
                || type == PunishmentType.MUTE);
    }

    @Conditional("mute_expires")
    @NumberProvider(
            text = "Ends",
            description = "When the mute expires",
            priority = 46,
            iconName = "calendar-check",
            iconFamily = Family.REGULAR,
            iconColor = Color.DEEP_ORANGE,
            format = FormatType.DATE_YEAR
    )
    public long muteExpireDate(String playerName) {
        return getPunishmentManager().getMute(abUUID(playerName)).getEnd();
    }

    @Conditional("muted")
    @StringProvider(
            text = "Reason",
            description = "Why the mute was issued",
            priority = 45,
            iconName = "comment",
            iconFamily = Family.REGULAR,
            iconColor = Color.DEEP_ORANGE
    )
    public String muteReason(String playerName) {
        return getPunishmentManager().getMute(abUUID(playerName)).getReason();
    }

    @NumberProvider(
            text = "Warnings",
            description = "How many unexpired warnings player has on AdvancedBan",
            priority = 25,
            iconName = "flag",
            iconColor = Color.AMBER,
            showInPlayerTable = true
    )
    public long warnings(String playerName) {
        return getPunishmentManager().getWarns(abUUID(playerName))
                .stream().filter(warning -> !warning.isExpired()).count();
    }

}