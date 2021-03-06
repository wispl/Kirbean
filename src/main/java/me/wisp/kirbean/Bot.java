package me.wisp.kirbean;

import me.wisp.kirbean.audio.PlayerRepository;
import me.wisp.kirbean.framework.CommandClient;
import me.wisp.kirbean.framework.collector.Collector;
import me.wisp.kirbean.messages.MessageListener;
import me.wisp.kirbean.polls.Polls;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class Bot {
    private final JDA jda;

    public Bot(String token) throws LoginException {
        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_VOICE_STATES
        );

        EnumSet<CacheFlag> disabledCache = EnumSet.of(
                CacheFlag.ACTIVITY,
                CacheFlag.CLIENT_STATUS,
                CacheFlag.EMOTE,
                CacheFlag.MEMBER_OVERRIDES,
                CacheFlag.ONLINE_STATUS,
                CacheFlag.ROLE_TAGS
        );

        jda = JDABuilder.createDefault(token, intents)
                .setChunkingFilter(ChunkingFilter.NONE)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.listening("Poyo poyo!"))
                .setBulkDeleteSplittingEnabled(false)
                .disableCache(disabledCache)
                .build();

        PlayerRepository.register(jda);
        Collector.register(jda);
        Polls.register(jda);
        jda.addEventListener(new MessageListener());
    }

    public void run() throws InterruptedException {
        jda.awaitReady();
        startCommands();
    }

    private void startCommands() {
        CommandClient client = new CommandClient();
        client.start(jda, false);
        //client.start(jda, jda.getGuildById(732677742021312553L));
    }
}