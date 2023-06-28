package ru.vaganov.RatBot.bot.environment;


import io.github.cdimascio.dotenv.Dotenv;

public class BotDotEnvironment implements BotEnvironment{
    private static Dotenv dotenv;

    public BotDotEnvironment(){
        ConfigureBotEnv();
    }

    public void ConfigureBotEnv(){
        dotenv = Dotenv.configure()
                //.directory("src/main/resources")
                .filename(".env")
                .load();
    }

    private void checkEnvCreated(){
        if(dotenv==null)
            ConfigureBotEnv();
    }
    public String getBotToken(){
        checkEnvCreated();
        return dotenv.get("TOKEN");
    }

    public String getLibraryPath(){
        checkEnvCreated();
        return dotenv.get("SoundLibraryPath");
    }

    @Override
    public String getInfoDirPath() {
        checkEnvCreated();
        return dotenv.get("InfoDirectoryPath");
    }


}
