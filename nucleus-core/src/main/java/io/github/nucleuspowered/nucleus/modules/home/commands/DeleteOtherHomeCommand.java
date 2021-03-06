/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.home.commands;

import io.github.nucleuspowered.nucleus.api.module.home.data.Home;
import io.github.nucleuspowered.nucleus.modules.home.HomePermissions;
import io.github.nucleuspowered.nucleus.modules.home.parameters.HomeOtherArgument;
import io.github.nucleuspowered.nucleus.modules.home.services.HomeService;
import io.github.nucleuspowered.nucleus.scaffold.command.ICommandContext;
import io.github.nucleuspowered.nucleus.scaffold.command.ICommandExecutor;
import io.github.nucleuspowered.nucleus.scaffold.command.ICommandResult;
import io.github.nucleuspowered.nucleus.scaffold.command.annotation.Command;
import io.github.nucleuspowered.nucleus.services.INucleusServiceCollection;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.event.CauseStackManager;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.annotation.NonnullByDefault;

@Command(
        aliases = {"deleteother", "delother", "#deletehomeother", "#delhomeother"},
        basePermission = HomePermissions.BASE_HOME_DELETEOTHER,
        commandDescriptionKey = "home.deleteother",
        parentCommand = HomeCommand.class
)
@NonnullByDefault
public class DeleteOtherHomeCommand implements ICommandExecutor<CommandSource> {

    private final String homeKey = "home";

    @Override
    public CommandElement[] parameters(INucleusServiceCollection serviceCollection) {
        return new CommandElement[] {
                GenericArguments.onlyOne(new HomeOtherArgument(
                        Text.of(this.homeKey),
                        serviceCollection.getServiceUnchecked(HomeService.class),
                        serviceCollection))
        };
    }

    @Override
    public ICommandResult execute(ICommandContext<? extends CommandSource> context) throws CommandException {
        Home wl = context.requireOne(this.homeKey, Home.class);

        try (CauseStackManager.StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
            frame.pushCause(context.getCommandSource());
            context.getServiceCollection().getServiceUnchecked(HomeService.class).removeHomeInternal(frame.getCurrentCause(), wl);
        }

        context.sendMessage("command.home.delete.other.success", wl.getUser().getName(), wl.getName());
        return context.successResult();
    }
}
