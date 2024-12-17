package test.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerHandlerListeners implements Listener {

    PlayerHandler playerHandler;

    public PlayerHandlerListeners(PlayerHandler playerHandler){
        this.playerHandler = playerHandler;
    }

    @EventHandler
    public void onConnect(PlayerJoinEvent e){
        Player player = e.getPlayer();

        playerHandler.addPlayerToHandler(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        PrisonPlayer prisonPlayer = playerHandler.getPlayer(player);

        playerHandler.updatePlayerData(prisonPlayer);
        playerHandler.removePlayerFromHandler(prisonPlayer);
    }

}
