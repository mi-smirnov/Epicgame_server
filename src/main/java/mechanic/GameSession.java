package mechanic;

import base.GameUser;
import base.UserProfile;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by smike on 21.10.14.
 */
public class GameSession {

    private static final int KEY_CODE_ARROW_UP = 38;
    private static final int KEY_CODE_ARROW_DOWN = 40;
    private static final int KEY_CODE_ARROW_LEFT = 37;
    private static final int KEY_CODE_ARROW_RIGHT = 39;

    private GameUser first;
    private GameUser second;
    private long startTime;
    private Map<String, GameUser> users = new HashMap<>();
    public static enum GameMap {
        @JsonProperty("FIRST_TANK") FIRST_TANK,
        @JsonProperty("SECOND_TANK") SECOND_TANK,
        @JsonProperty("EMPTY_FIELD") EMPTY_FIELD,
        @JsonProperty("WALL") WALL
    }
    private GameMap[][] field;
    private int mapSize = 10;
    public GameSession(UserProfile user1, UserProfile user2){
        GameUser gameUser1 = new GameUser(user1);
        gameUser1.setEnemyEmail(user2.getEmail());

        GameUser gameUser2 = new GameUser(user2);
        gameUser2.setEnemyEmail(user1.getEmail());

        users.put(user1.getEmail(), gameUser1);
        users.put(user2.getEmail(), gameUser2);

        this.first = gameUser1;
        this.second = gameUser2;

        int size = getMapSize();
        this.field = new GameMap[size][size];
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                field[i][j] = GameMap.EMPTY_FIELD;
            }
        }
        int mid = size/2;
        for (int i = 0; i < size; i++){
            field[mid][i] = GameMap.WALL;
        }
        field[0][0] = GameMap.FIRST_TANK;
        field[size - 1][size - 1] = GameMap.SECOND_TANK;

        first.setX(0);
        first.setY(0);
        second.setX(size - 1);
        second.setY(size - 1);

        startTime = new Date().getTime();
    }

    public void moveTank(GameUser user, int key){
        int x = 0;
        int y = 0;
        int size = this.getMapSize();
        if (key == KEY_CODE_ARROW_UP){
            x = user.getX();
            y = user.getY() - 1;
            if(((x >= 0) && (x < size)) && ((y >= 0) && (y < size))){
                if(this.field[x][y] != GameMap.WALL){
                    user.setX(x);
                    user.setY(y);
                    if(user == getFirst()){
                        this.field[x][y] = GameMap.FIRST_TANK;
                        this.field[x][y + 1] = GameMap.EMPTY_FIELD;
                    }
                    if(user == getSecond()){
                        this.field[x][y] = GameMap.SECOND_TANK;
                        this.field[x][y + 1] = GameMap.EMPTY_FIELD;
                    }
                }
            }
        }
        if (key == KEY_CODE_ARROW_DOWN){
            x = user.getX();
            y = user.getY() + 1;
            if(((x >= 0) && (x < size)) && ((y >= 0) && (y < size))){
                if(this.field[x][y] != GameMap.WALL){
                    user.setX(x);
                    user.setY(y);
                    if(user == getFirst()){
                        this.field[x][y] = GameMap.FIRST_TANK;
                        this.field[x][y - 1] = GameMap.EMPTY_FIELD;
                    }
                    if(user == getSecond()){
                        this.field[x][y] = GameMap.SECOND_TANK;
                        this.field[x][y - 1] = GameMap.EMPTY_FIELD;
                    }
                }
            }
        }
        if (key == KEY_CODE_ARROW_LEFT){
            x = user.getX() - 1;
            y = user.getY();
            if(((x >= 0) && (x < size)) && ((y >= 0) && (y < size))){
                if(this.field[x][y] != GameMap.WALL){
                    user.setX(x);
                    user.setY(y);
                    if(user == getFirst()){
                        this.field[x][y] = GameMap.FIRST_TANK;
                        this.field[x + 1][y] = GameMap.EMPTY_FIELD;
                    }
                    if(user == getSecond()){
                        this.field[x][y] = GameMap.SECOND_TANK;
                        this.field[x + 1][y] = GameMap.EMPTY_FIELD;
                    }
                }
            }
        }
        if (key == KEY_CODE_ARROW_RIGHT){
            x = user.getX() + 1;
            y = user.getY();
            if(((x >= 0) && (x < size)) && ((y >= 0) && (y < size))){
                if(this.field[x][y] != GameMap.WALL){
                    user.setX(x);
                    user.setY(y);
                    if(user == getFirst()){
                        this.field[x][y] = GameMap.FIRST_TANK;
                        this.field[x - 1][y] = GameMap.EMPTY_FIELD;
                    }
                    if(user == getSecond()){
                        this.field[x][y] = GameMap.SECOND_TANK;
                        this.field[x - 1][y] = GameMap.EMPTY_FIELD;
                    }
                }
            }
        }
    }

    public GameUser getFirst(){
        return first;
    }

    public GameUser getSecond(){
        return second;
    }

    public boolean isFirstWin(){
        return first.getMyScore() > second.getMyScore();
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }

    public GameUser getSelf(String user){
        return users.get(user);
    }

    public GameUser getEnemy(String user){
        String enemyName = users.get(user).getEnemyEmail();
        return users.get(enemyName);
    }
    public GameMap[][] getField() {
        return field;
    }
    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }
    public int getMapSize() {
        return mapSize;
    }
}