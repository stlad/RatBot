package ru.vaganov.RatBot.bot;


import io.github.cdimascio.dotenv.Dotenv;

public class BotEnvironment {
    private static Dotenv dotenv;
    public static void ConfigureBotEnv(){
        dotenv = Dotenv.configure()
                .directory("src/main/resources")
                .filename(".env")
                .load();
    }

    private static void checkEnvCreated(){
        if(dotenv==null)
            ConfigureBotEnv();
    }
    public static String getBotToken(){
        checkEnvCreated();
        return dotenv.get("TOKEN");
    }

}
