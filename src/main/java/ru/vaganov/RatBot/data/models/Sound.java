package ru.vaganov.RatBot.data.models;


import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import ru.vaganov.RatBot.bot.BotEnvironment;

@Entity
public class Sound {

    @Id
    @GeneratedValue
    private Long id;

    private String guildid;

    private String filename;

    private String title;
    public Sound(){}

    public Sound(String guildid, String filename){
        this.guildid = guildid;
        this.filename = filename;
    }

    public Sound(String guildid, String filename, String title) {
        this(guildid,filename);
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTitle() {
        if(title==null) return filename;
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGuildid() {
        return guildid;
    }

    public void setGuildid(String guildid) {
        this.guildid = guildid;
    }

    public String getAbsolutePath(){
        return BotEnvironment.getLibraryPath()+"\\" + getGuildid()+"\\"+getFilename();
    }
}
