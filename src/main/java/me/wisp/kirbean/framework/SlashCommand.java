package me.wisp.kirbean.framework;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommand {
    void execute(SlashCommandInteractionEvent event);
}
