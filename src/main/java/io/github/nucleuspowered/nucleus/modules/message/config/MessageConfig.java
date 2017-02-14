/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.message.config;

import io.github.nucleuspowered.nucleus.configurate.annotations.Default;
import io.github.nucleuspowered.nucleus.internal.text.NucleusTextTemplate;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class MessageConfig {

    @Setting(value = "helpop-prefix", comment = "config.message.helpop.prefix")
    @Default(value = "&7HelpOp: {{name}} &7> &r", saveDefaultIfNull = true)
    private NucleusTextTemplate helpOpPrefix;

    @Setting(value = "msg-receiver-prefix", comment = "config.message.receiver.prefix")
    @Default(value = "&7[{{fromDisplay}}&7 -> me]: &r", saveDefaultIfNull = true)
    private NucleusTextTemplate messageReceiverPrefix;

    @Setting(value = "msg-sender-prefix", comment = "config.message.sender.prefix")
    @Default(value = "&7[me -> {{toDisplay}}&7]: &r", saveDefaultIfNull = true)
    private NucleusTextTemplate messageSenderPrefix;

    @Setting(value = "socialspy")
    private SocialSpy socialSpy = new SocialSpy();

    public NucleusTextTemplate getHelpOpPrefix() {
        return helpOpPrefix;
    }

    public NucleusTextTemplate getMessageReceiverPrefix() {
        return messageReceiverPrefix;
    }

    public NucleusTextTemplate getMessageSenderPrefix() {
        return messageSenderPrefix;
    }

    public NucleusTextTemplate getMessageSocialSpyPrefix() {
        return socialSpy.messageSocialSpyPrefix;
    }

    public boolean isSocialSpyLevels() {
        return socialSpy.socialSpyLevels;
    }

    public boolean isSocialSpySameLevel() {
        return socialSpy.socialSpySameLevel;
    }

    public int getServerLevel() {
        return socialSpy.serverLevel;
    }

    public boolean isShowMessagesInSocialSpyWhileMuted() {
        return socialSpy.showMessagesInSocialSpyWhileMuted;
    }

    public String getMutedTag() {
        return socialSpy.mutedTag;
    }

    public boolean isOnlyPlayerSocialSpy() {
        return socialSpy.onlyPlayerSocialSpy;
    }

    @ConfigSerializable
    public static class SocialSpy {
        @Setting(value = "msg-prefix", comment = "config.message.socialspy.prefix")
        @Default(value = "&7[SocialSpy] [{{fromDisplay}}&7 -> {{toDisplay}}&7]: &r", saveDefaultIfNull = true)
        private NucleusTextTemplate messageSocialSpyPrefix;

        @Setting(value = "use-levels", comment = "config.message.socialspy.levels")
        private boolean socialSpyLevels = false;

        @Setting(value = "same-levels-can-see-each-other", comment = "config.message.socialspy.samelevel")
        private boolean socialSpySameLevel = true;

        @Setting(value = "server-level", comment = "config.message.socialspy.serverlevel")
        private int serverLevel = Integer.MAX_VALUE;

        @Setting(value = "show-cancelled-messages", comment = "config.message.socialspy.mutedshow")
        private boolean showMessagesInSocialSpyWhileMuted = false;

        @Setting(value = "cancelled-messages-tag", comment = "config.message.socialspy.mutedtag")
        private String mutedTag = "&c[cancelled] ";

        @Setting(value = "show-only-players", comment = "config.message.socialspy.playeronly")
        private boolean onlyPlayerSocialSpy = false;
    }
}
