package base;

/**
 * Created by smike on 19.10.14.
 */
public class GameUser extends UserProfile{

    private String enemyEmail;
    private int myScore = 0;
    private int enemyScore = 0;
    public GameUser(UserProfile profile){
        super(profile);
    }

    public String getEnemyEmail(){
        return enemyEmail;
    }
    public void setEnemyEmail(String enemyEmail){
        this.enemyEmail = enemyEmail;
    }
    public int getMyScore(){
        return myScore;
    }
    public int getEnemyScore(){
        return enemyScore;
    }
    public void incrementMyScore(){
        myScore++;
    }
    public void incrementEnemyScore(){
        enemyScore++;
    }
}
