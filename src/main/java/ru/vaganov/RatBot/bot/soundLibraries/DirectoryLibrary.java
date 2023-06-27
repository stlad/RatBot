package ru.vaganov.RatBot.bot.soundLibraries;

import org.springframework.stereotype.Component;
import ru.vaganov.RatBot.bot.BotEnvironment;
import ru.vaganov.RatBot.data.models.Sound;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DirectoryLibrary implements SoundLibrary{

    private String basePath;

    public DirectoryLibrary(){
        basePath = BotEnvironment.getLibraryPath();
    }

    @Override
    public List<Sound> getSoundsByGuildId(String guildId) {
        File dir = new File(basePath+guildId);
        File[] arrFiles = dir.listFiles();
        List<Sound> sounds = new ArrayList<>();
        if(arrFiles == null) return sounds;
        for(File file:arrFiles){
            Sound sound = new Sound();
            sound.setFilename(file.getName());
            sound.setGuildid(guildId);
            sounds.add(sound);
        }
        return sounds;
    }

    @Override
    public Integer getCountByGuildID(String guildID) {
        File dir = new File(basePath+guildID); //path указывает на директорию
        return dir.listFiles().length;
    }

    @Override
    public void addToGuild(Sound sound) {
        //TODO сделать добавление звука, а не только директории
        try{
            addDirectory(sound.getGuildid());
        }
        catch (IOException e){return;}
    }

    @Override
    public Sound getSound(String guildID, String filename) {
        var s = basePath+guildID + "\\" + filename;
        File soundFile = new File(basePath+guildID + "\\" + filename);
        if(!soundFile.exists()) return null;
        Sound sound = new Sound(guildID, filename);
        return sound;
    }

    private void addDirectory(String dirName) throws IOException {
        Files.createDirectories(Paths.get(basePath + dirName));
    }


}
