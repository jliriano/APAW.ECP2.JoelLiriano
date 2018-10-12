package api.businesscontrollers;

import api.daos.DaoFactory;
import api.dtos.GameDto;
import api.entities.Game;
import api.entities.Publisher;
import api.exceptions.NotFoundException;

public class GameBusinessController {

    private PublisherBusinessController publisherBusinessController = new PublisherBusinessController();

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
        Game game;
        if(publisherBusinessController.getPublisher(publisherId).hasGame(gameId)) {
            return DaoFactory.getFactory().getGameDao().read(gameId).orElseThrow(
                    () -> new NotFoundException("Publisher (" + gameId +")"));
        }
        else new NotFoundException("Game ("+ gameId+") not found for Publisher ("+publisherId+")");
        return null;
    }

    public void updateName(GameDto gameDto) {
        Game game = getGame(gameDto.getPublisherId(), gameDto.getId());
        game.setName(gameDto.getName());
        DaoFactory.getFactory().getGameDao().save(game);
    }

}
