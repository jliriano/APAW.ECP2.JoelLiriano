package api.businesscontrollers;

import api.daos.DaoFactory;
import api.dtos.GameDto;
import api.entities.Game;
import api.entities.Publisher;

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

}
