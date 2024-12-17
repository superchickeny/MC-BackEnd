package test.PlayerManager;

import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerManager extends JavaPlugin {

    SqlQuery sqlQuery;
    PlayerHandler playerHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        if(sqlQuery == null){
            sqlQuery = new SqlQuery("jdbc:mysql://localhost:3306/minecraft", "root", "ericky");
        }

        playerHandler = new PlayerHandler(sqlQuery, this);
        getServer().getPluginManager().registerEvents(new PlayerHandlerListeners(playerHandler), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        playerHandler.updateAllPlayerData();
    }

    public SqlQuery getSQLManager(){
        return sqlQuery;
    }

    public PlayerHandler getPlayerHandler(){
        return playerHandler;
    }
}
