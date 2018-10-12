package api.businesscontrollers;

import api.daos.DaoFactory;
import api.dtos.GameDto;
import api.entities.Game;
import api.entities.Publisher;
import api.exceptions.NotFoundException;

public class GameBusinessController {

    private PublisherBusinessController publisherBusinessController = new PublisherBusinessController();

    private static final String GAME_NOT_FOUND = "Game not found for Publisher";

    public String create(GameDto gameDto) {
        Publisher publisher = publisherBusinessController.getPublisher(gameDto.getPublisherId());
        Game game = new Game(gameDto.getName(), publisher);
        if(gameDto.getLaunchDate()!=null) {
            game.setLaunchDate(gameDto.getLaunchDate());
        }
        if(gameDto.getGameRating()!=null) {
            game.setGameRating(gameDto.getGameRating());
        }
        DaoFactory.getFactory().getGameDao().save(game);
        publisher.addGame(game.getId());
        DaoFactory.getFactory().getPublisherDao().save(publisher);
        return game.getId();
    }

    public Game getGame(String publisherId, String gameId) {
        if(publisherBusinessController.getPublisher(publisherId).hasGame(gameId)) {
            return DaoFactory.getFactory().getGameDao().read(gameId).orElseThrow(
                    () -> new NotFoundException("[" + gameId +"] "+GAME_NOT_FOUND+" ["+publisherId+"]"));
        }
        else throw new NotFoundException("[" + gameId +"] "+GAME_NOT_FOUND+" ["+publisherId+"]");
    }

    public void updateName(GameDto gameDto) {
        Game game = getGame(gameDto.getPublisherId(), gameDto.getId());
        game.setName(gameDto.getName());
        DaoFactory.getFactory().getGameDao().save(game);
    }

    public GameDto read(String publisherId, String gameId) {
        if(publisherBusinessController.getPublisher(publisherId).hasGame(gameId)) {
            Game game = DaoFactory.getFactory().getGameDao().read(gameId).orElseThrow(
                    () -> new NotFoundException("[" + gameId +"] "+GAME_NOT_FOUND+" ["+publisherId+"]"));
            return new GameDto(game);
        } else throw new NotFoundException("[" + gameId +"] "+GAME_NOT_FOUND+" ["+publisherId+"]");
    }

}
