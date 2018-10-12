package api.businesscontrollers;

import api.daos.DaoFactory;
import api.dtos.GameDto;
import api.entities.Game;
import api.exceptions.NotFoundException;

public class GameBusinessController {

    public String create(GameDto gameDto) {
        Game game = new Game(gameDto.getName(), gameDto.getPublisher());
        DaoFactory.getFactory().getGameDao().save(game);
        return game.getId();
    }

}
