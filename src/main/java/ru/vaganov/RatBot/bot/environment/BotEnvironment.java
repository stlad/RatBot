package ru.vaganov.RatBot.bot.environment;

import io.github.cdimascio.dotenv.Dotenv;

public interface BotEnvironment {
    public  void ConfigureBotEnv();
    public String getBotToken();

    public  String getLibraryPath();

    public String getInfoDirPath();
}
