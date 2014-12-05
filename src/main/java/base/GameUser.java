package base;

/**
 * Created by smike on 19.10.14.
 */
public class GameUser extends UserProfile{

    private String enemyEmail;
    private int myScore = 0;
    private int enemyScore = 0;
    private int x;
    private int y;

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

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
