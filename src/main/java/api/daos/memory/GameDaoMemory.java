package api.daos.memory;

import api.daos.GameDao;
import api.entities.Game;

import java.util.HashMap;

public class GameDaoMemory extends GenericDaoMemory<Game> implements GameDao {

    public GameDaoMemory() {
        super(new HashMap<>());
    }

    @Override
    public String getId(Game game) {
        return game.getId();
    }

    @Override
    public void setId(Game game, String id) {
        game.setId(id);
    }

}
