package serverResponse;

import mechanic.GameSession;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by smike on 02.12.14.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("email")
    private String email;

    @JsonProperty("enemy")
    private String enemy;

    @JsonProperty("x")
    private int x;

    @JsonProperty("y")
    private int y;

    @JsonProperty("field")
    private GameSession.GameMap[][] field;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GameSession.GameMap[][] getField() {
        return field;
    }

    public void setField(GameSession.GameMap[][] field) {
        this.field = field;
    }

    public void setEnemy(String enemy) {
        this.enemy = enemy;
    }
}
