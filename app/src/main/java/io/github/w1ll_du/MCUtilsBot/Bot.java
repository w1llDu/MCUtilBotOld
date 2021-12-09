package io.github.w1ll_du.MCUtilsBot;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class Bot {

    private Bot() throws LoginException {

        Map<String, String> conf = null;
        BidiMap<String, String> playerMap = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            conf = mapper.readValue(Paths.get("MCUtilsConfig.json").toFile(), Map.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ObjectWriter writer = new ObjectMapper().writer(new DefaultPrettyPrinter());
            Map<String, String> map = new LinkedHashMap<>();
            // discord cfg
            map.put("token", "YOUR TOKEN HERE");
            map.put("prefix", "w!");
            map.put("owner_id", "1234567890");
            map.put("server_id", "9012345678");
            map.put("bot_channel_id", "8901234567");
            // map.put("whitelist_role_id", "6789012345");
            map.put("changelog_channel_id", "3456789012");
            // fdlink cfg
            map.put("fdlink_bot_id", "0123456789");
            map.put("log_channel_id", "7890123456");
            // minecraft cfg
            map.put("server_ip", "5678901234");
            map.put("rcon_port", "25575");
            map.put("rcon_password", "password");
            try {
                writer.writeValue(Paths.get("MCUtilsConfig.json").toFile(), map);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            playerMap = new DualHashBidiMap<String, String>(mapper.readValue(Paths.get("playerMap.json").toFile(), Map.class));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = new HashMap<>();
            try {
                mapper.writeValue(Paths.get("playerMap.json").toFile(), map);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert conf != null;
        assert playerMap != null;
        Utils.conf = conf;
        JDABuilder
        .createDefault(conf.get("token"),
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_BANS,
                GatewayIntent.GUILD_MESSAGES)
        .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOTE)
        .enableIntents(GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_BANS,
                GatewayIntent.GUILD_MESSAGES)
        .addEventListeners(new Listener(conf, playerMap))
        .build();
    }

    public static void main(String[] args) throws LoginException {
        Bot bot = new Bot();
    }
}