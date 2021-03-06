package io.github.w1ll_du.MCUtilsBot.command;

import io.github.w1ll_du.MCUtilsBot.CommandManager;
import me.duncte123.botcommons.commands.ICommandContext;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Map;

public class CommandContext implements ICommandContext {

    private final GuildMessageReceivedEvent event;
    private final List<String> args;
    private Map<String, String> conf;
    private CommandManager manager;

    public CommandContext(GuildMessageReceivedEvent event, List<String> args, Map<String, String> conf, CommandManager manager) {
        this.event = event;
        this.args = args;
        this.conf = conf;
        this.manager = manager;
    }

    @Override
    public Guild getGuild() {
        return this.getEvent().getGuild();
    }

    @Override
    public GuildMessageReceivedEvent getEvent() {
        return this.event;
    }

    public List<String> getArgs() {
        return this.args;
    }

    public Map<String, String> getConf() {
        return conf;
    }

    public CommandManager getManager() {
        return manager;
    }
}
