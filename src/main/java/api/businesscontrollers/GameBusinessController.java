package api.businesscontrollers;

import api.daos.DaoFactory;
import api.dtos.GameDto;
import api.entities.Game;
import api.exceptions.NotFoundException;

public class GameBusinessController {

    public String create(GameDto gameDto) {
        Game game = new Game(gameDto.getName(), gameDto.getPublisher());
        if(gameDto.getLaunchDate()!=null) {
            game.setLaunchDate(gameDto.getLaunchDate());
        }
        if(gameDto.getGameRating()!=null) {
            game.setGameRating(gameDto.getGameRating());
        }
        DaoFactory.getFactory().getGameDao().save(game);
        return game.getId();
    }

}
