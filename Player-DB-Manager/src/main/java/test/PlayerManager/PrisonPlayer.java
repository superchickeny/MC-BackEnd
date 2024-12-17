package test.PlayerManager;

public class PrisonPlayer {

    public String playerName;
    public String mineType;
    public int mineX;
    public int mineY;
    public int level;
    public int xp;
    public int etokens;

    SqlQuery sqlQuery;

    public PrisonPlayer(String playerName, SqlQuery sqlQuery){
        this.playerName = playerName;
        this.sqlQuery = sqlQuery;
        setPlayerInstance();
    }

    //CHECKS IF PLAYER IS IN DATABASE
    private boolean doesPlayerExist(){
        if(sqlQuery.Select(playerName, "playername", playerMineDataTable) != null){
            return true;
        }
        return false;
    }

    //CALLED INSIDE OF setPlayerInstance CREATES PLAYER IN DATABASE
    private void createPlayerInTables(){
        sqlQuery.insertMineData(playerName, null, 0, 0);
        sqlQuery.insertPlayerData(playerName, 0, 0, 0);
    }

    //table names
    private final String playerMineDataTable = "playerminedata";
    private final String playerData = "playerdata";

    //CALLED ON INSTANCE CREATION
    private void setPlayerInstance(){

        //IF PLAYER HAS A KEY IN THE DATABASE SELECT ALL THE PLAYERS STATS AND SETS THEM HERE IN THIS INSTANCE
        if(doesPlayerExist()){

            //PLAYERMINEDATA TABLE
            this.playerName = sqlQuery.Select(playerName, "playername", playerMineDataTable);
            this.mineType = sqlQuery.Select(playerName, "playerminetype", playerMineDataTable);
            this.mineX = Integer.parseInt(sqlQuery.Select(playerName, "minex", playerMineDataTable));
            this.mineY = Integer.parseInt(sqlQuery.Select(playerName, "miney", playerMineDataTable));

            //PLAYERDATA TABLE
            this.level = Integer.parseInt(sqlQuery.Select(playerName, "level", playerData));
            this.xp = Integer.parseInt(sqlQuery.Select(playerName, "xp", playerData));
            this.etokens = Integer.parseInt(sqlQuery.Select(playerName, "etokens", playerData));
        }

        //IF NO PLAYER IN TABLE MAKE ONE AND TRY AGAIN
        if(!doesPlayerExist()){
            createPlayerInTables();
            setPlayerInstance();
        }
    }

    /*
    BELOW ARE THE METHODS TO CALL TO UPDATE THE PLAYERS INSTANCE VARIABLES
    THE Diff VARIABLES KEEP TRACK OF HOW MUCH THE VALUE OF AN INSTANCE VARIABLE CHANGED SINCE UPDATED IN THE DATABASE
     */

    public int xpDiff;
    public void addXp(int xp){
        xpDiff += xp;
        this.xp += xp;
    }

    public int etokenDiff;
    public void addEtokens(int etokens){
        etokenDiff += etokens;
        this.etokens += etokens;
    }

    public String mineTypeDiff;
    public void addMineType(String mineType){
        mineTypeDiff = mineType;
        this.mineType = mineType;
    }

    public int levelDiff;
    public void addLevel(int level){
        levelDiff += level;
        this.level += level;
    }

    public int mineXDiff;
    public void addMineX(int x){
        mineXDiff += x;
        this.mineX += x;
    }

    public int mineYDiff;
    public void addMineY(int y){
        mineYDiff += y;
        this.mineY += y;
    }

}
