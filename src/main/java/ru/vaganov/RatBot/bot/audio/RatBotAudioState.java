package ru.vaganov.RatBot.bot.audio;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import java.util.Map;
import java.util.HashMap;
public class RatBotAudioState {

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;


    public RatBotAudioState(){
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);

    }

    public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }


    public void loadAndPlay(Guild guild, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(guild);

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                //channel.sendMessage("Adding to queue " + track.getInfo().title).queue();
               // System.out.println("Adding to queue " + track.getInfo().title);
                play(guild, musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

               //channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();
                System.out.println("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")");
                play(guild, musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                //channel.sendMessage("Nothing found by " + trackUrl).queue();
                System.out.println("Nothing found by " + trackUrl);
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                //channel.sendMessage("Could not play: " + exception.getMessage()).queue();
                System.out.println("Could not play: " + exception.getMessage());
            }
        });



    }
    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        //connectToFirstVoiceChannel(guild.getAudioManager());
        if(!guild.getAudioManager().isConnected()) return;
        musicManager.scheduler.queue(track);
    }
}
