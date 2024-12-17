package test.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.util.ArrayList;
import java.util.List;

public class PlayerHandler {

    List<PrisonPlayer> players;
    SqlQuery sqlQuery;
    Plugin plugin;

    public PlayerHandler(SqlQuery sqlQuery, Plugin plugin){
        players = new ArrayList<>();
        this.sqlQuery = sqlQuery;
        this.plugin = plugin;
        start();
    }

    //RUNS EVERY 5 MINUTES
    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            //UPDATES ALL STATS THAT HAVE CHANGED IN THE LAST 5 MINUTES FOR ALL PLAYERS
            updateAllPlayerData();

        }, 0L, 6000L); // 6000L = 5 minutes
    }

    //CALLED ON JOIN CREATES A PrisonPlayer FOR THE PLAYER THAT IS SPECIFIED AND ADDS IT TO THE ARRAY
    public void addPlayerToHandler(Player player){

        String playerName = player.getName();
        players.add(new PrisonPlayer(playerName, sqlQuery));

    }

    //CALLED ON DISCONNECT REMOVES THE PLAYER FROM THE ARRAY
    public void removePlayerFromHandler(PrisonPlayer prisonPlayer){
        players.remove(prisonPlayer);
    }

    //CALLED IN THE RUNNABLE AND ON SHUTDOWN
    public void updateAllPlayerData(){
        for(int i = 0; i < players.size(); i++){

            if(players.get(i) != null){
                PrisonPlayer prisonPlayer = players.get(i);
                updatePlayerData(prisonPlayer);
            }

        }
    }

    //UPDATES THE SPECIFIED PLAYERS STATS IN DATABASE
    public void updatePlayerData(PrisonPlayer player){

        if(player.xpDiff != 0){
            sqlQuery.updatePlayerData(player.playerName, "xp", player.xp);
            player.xpDiff = 0;
        }

        if(player.levelDiff != 0){
            sqlQuery.updatePlayerData(player.playerName, "level", player.level);
            player.levelDiff = 0;
        }

        if(player.etokenDiff != 0){
            sqlQuery.updatePlayerData(player.playerName, "etokens", player.etokens);
            player.etokenDiff = 0;
        }

        if(player.mineTypeDiff != null){
            sqlQuery.updatePlayerMineData(player.playerName, "playerminetype", player.mineType);
            player.mineTypeDiff = null;
        }

        if(player.mineXDiff != 0){
            sqlQuery.updatePlayerMineData(player.playerName, "minex", player.mineX);
            player.mineXDiff = 0;
        }

        if(player.mineY != 0){
            sqlQuery.updatePlayerMineData(player.playerName, "miney", player.mineY);
            player.mineYDiff = 0;
        }

        Bukkit.getLogger().info("Updated " + player.playerName + " in all tables");
    }

    //RETURNS LIST OF ALL PLAYERS IN THE ARRAY
    public List<PrisonPlayer> getPlayers(){
        return players;
    }

    //GETS SPECIFIED PrisonPlayer FROM THE ARRAY
    public PrisonPlayer getPlayer(Player player){

        String playerName = player.getName();

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).playerName.equals(playerName)) {
                return players.get(i);
            }
        }

        return null;
    }
}
