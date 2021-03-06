/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.scaffold.command.control;

import com.google.common.collect.ImmutableList;
import io.github.nucleuspowered.nucleus.scaffold.command.ICommandExecutor;
import io.github.nucleuspowered.nucleus.scaffold.command.annotation.Command;
import io.github.nucleuspowered.nucleus.scaffold.command.annotation.EssentialsEquivalent;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

public final class CommandMetadata {

    private static void aliases(String[] v, boolean isRoot, boolean addPrefix,
            ImmutableList.Builder<String> root,
            ImmutableList.Builder<String> sub,
            ImmutableList.Builder<String> disabled) {
        for (String alias : v) {
            if (alias.startsWith("#")) {
                root.add(alias.substring(1).toLowerCase());
                if (addPrefix) {
                    root.add("n" + alias.substring(1).toLowerCase());
                }
            } else if (alias.startsWith("$")) {
                String x = alias.substring(1).toLowerCase();
                root.add(x);
                disabled.add(x);
                if (addPrefix) {
                    root.add("n" + x);
                }
            } else if (isRoot) {
                root.add(alias.toLowerCase());
                if (addPrefix) {
                    root.add("n" + alias.toLowerCase());
                }
            } else {
                sub.add(alias.toLowerCase());
            }
        }
    }


    private final String moduleid;
    private final String modulename;
    private final String metadataKey;
    private final Command annotation;
    private final Class<? extends ICommandExecutor<?>> executor;
    private final String commandKey;
    private final List<String> root;
    private final List<String> sub;
    private final List<String> disabledByDefault;
    private final boolean isRoot;
    private final boolean modifierKeyRedirect;
    @Nullable private final EssentialsEquivalent essentialsEquivalent;

    public CommandMetadata(
            String moduleid,
            String modulename,
            Command annotation,
            Class<? extends ICommandExecutor<?>> executor,
            String commandKey,
            @Nullable EssentialsEquivalent essentialsEquivalent) {
        this.moduleid = moduleid;
        this.modulename = modulename;
        this.annotation = annotation;
        this.executor = executor;
        this.commandKey = commandKey;
        ImmutableList.Builder<String> rootBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<String> subBuilder = new ImmutableList.Builder<>();
        ImmutableList.Builder<String> disabledRootBuilder = new ImmutableList.Builder<>();
        aliases(annotation.aliases(),
                annotation.parentCommand() == ICommandExecutor.class,
                annotation.prefixAliasesWithN(),
                rootBuilder,
                subBuilder,
                disabledRootBuilder);
        this.root = rootBuilder.build();
        this.sub = subBuilder.build();
        this.disabledByDefault = disabledRootBuilder.build();
        this.isRoot = annotation.parentCommand() == ICommandExecutor.class;
        this.essentialsEquivalent = essentialsEquivalent;
        this.modifierKeyRedirect = !annotation.modifierOverride().isEmpty();
        this.metadataKey = this.modifierKeyRedirect ? annotation.modifierOverride() : this.commandKey;
    }

    public String getModuleid() {
        return this.moduleid;
    }

    public String getModulename() {
        return this.modulename;
    }

    public Command getCommandAnnotation() {
        return this.annotation;
    }

    public String getCommandKey() {
        return this.commandKey;
    }

    public String[] getAliases() {
        return this.annotation.aliases();
    }

    public List<String> getRootAliases() {
        return this.root;
    }

    public List<String> getDisabledByDefaultRootAliases() {
        return this.disabledByDefault;
    }

    public List<String> getAtLevelAliases() {
        return this.sub;
    }

    public Class<? extends ICommandExecutor<?>> getExecutor() {
        return this.executor;
    }

    public boolean isRoot() {
        return this.isRoot;
    }

    public String getMetadataKey() {
        return this.metadataKey;
    }

    public boolean isModifierKeyRedirect() {
        return this.modifierKeyRedirect;
    }

    @Nullable
    public EssentialsEquivalent getEssentialsEquivalent() {
        return this.essentialsEquivalent;
    }
}
