package test.PlayerManager;

import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.sql.*;

public class SqlQuery {
    String url;
    String user;
    String password;
    String query;
    Connection connection;

    public SqlQuery(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
        setConnection();
    }

    //ESTABLISHES CONNECTION TO SQL SERVER
    private void setConnection() {
        try{
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ResultSet executeQuery(String query){
        try{
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        }catch (Exception e){
            Bukkit.getLogger().warning(e.getMessage());
        }
        return null;
    }

    private void executeUpdate(String query){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }catch (Exception e){
            Bukkit.getLogger().warning(e.getMessage());
        }
    }

    /*
    UPDATE
     */

    public void updatePlayerData(String playerName, String column, int value){
        query = "UPDATE minecraft.playerData SET " + column + " = " + value + " WHERE playerName = " + "'" + playerName + "'";
        executeUpdate(query);
    }

    //FOR STRING
    public void updatePlayerMineData(String playerName, String column, String value){
        query = "UPDATE minecraft.playerMineData SET " + column + " = " + value + " WHERE playerName = " + "'" + playerName + "'";
        executeUpdate(query);
    }

    //FOR INT
    public void updatePlayerMineData(String playerName, String column, int value){
        query = "UPDATE minecraft.playerMineData SET " + column + " = " + value + " WHERE playerName = " + "'" + playerName + "'";
        executeUpdate(query);
    }

    /*
    INSERT
     */

    public void insertPlayerData(String playerName, int level, int xp, int etokens){
        query = "INSERT INTO minecraft.playerData(playerName, level, xp, etokens) VALUES(" + "'" + playerName + "', '" + level + "'," + xp + ',' + etokens + ')';
        executeUpdate(query);
    }

    public void insertMineData(String playerName, String mineType, int mineX, int mineY){
        query = "INSERT INTO minecraft.playerMineData(playerName, playerMineType, mineX, mineY) VALUES(" + "'" + playerName + "', '" + mineType + "'," + mineX + ',' + mineY + ')';
        executeUpdate(query);
    }

    /*
    SELECT
     */

    public String Select(String playerName, String column, String table) {
        query = "SELECT " + column + " FROM minecraft." + table + " WHERE playerName = '" + playerName + "'";
        ResultSet result = executeQuery(query);
        String value = null;

        try{
            if(result.next()){
                value = result.getString(column);
            }

            return value;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}