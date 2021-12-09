package io.github.w1ll_du.MCUtilsBot;

import io.github.w1ll_du.MCUtilsBot.command.CommandContext;
import io.github.w1ll_du.MCUtilsBot.command.ICommand;
import io.github.w1ll_du.MCUtilsBot.command.commands.StatsCommand;
import io.github.w1ll_du.MCUtilsBot.command.commands.cmToMCommand;
import io.github.w1ll_du.MCUtilsBot.command.commands.ticksToHours;
import io.github.w1ll_du.MCUtilsBot.command.commands.ticksToMinutes;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.collections4.BidiMap;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new StatsCommand());
        addCommand(new cmToMCommand());
        addCommand(new ticksToHours());
        addCommand(new ticksToMinutes());
    }

    @Nullable
    private ICommand getCommand(String name) {
        for (ICommand cmd : commands) {
            if (cmd.getName().equals(name) || cmd.getAliases().contains(name)) {
                return cmd;
            }
        }
        return null;
    }

    private void addCommand(ICommand newCmd) {
        if (commands.stream().anyMatch(cmd -> cmd.getName().equals(newCmd.getName()))) {
            throw new IllegalArgumentException("A command with this name already exists");
        } else {
            commands.add(newCmd);
        }
    }

    void handle(GuildMessageReceivedEvent event, String prefix, Map<String, String> conf) throws IOException {

        String[] split = event.getMessage().getContentRaw()
            .replaceFirst("(?i)" + Pattern.quote(prefix), "")
            .split("\\s+");

        String name = split[0];
        ICommand cmd = getCommand(name);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args, conf);

            cmd.handle(ctx);
        }
    }
}
