package io.github.w1ll_du.MCUtilsBot.command;
import me.dilley.MineStat;

public abstract class ARconCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        // make sure server is online
        if (new MineStat(ctx.getConf().get("mc_server_ip"), 25565).isServerUp()) {
            handle2(ctx);
        } else {
            ctx.getChannel().sendMessage("Could not execute command at the time.").queue();
        }
    }

    protected abstract void handle2(CommandContext ctx);
}
