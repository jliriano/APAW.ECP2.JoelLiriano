package api.apicontrollers;

import api.businesscontrollers.GameBusinessController;
import api.dtos.GameDto;
import api.entities.GameRating;
import api.exceptions.ArgumentNotValidException;

import java.util.List;

public class GameApiController extends BasicApiController {

    public static final String GAMES = "/games";
    public static final String ID_ID = "/{id}";
    public static final String NAME = "/name";
    public static final String SEARCH = "/search";

    private GameBusinessController gameBusinessController = new GameBusinessController();

    public String create(GameDto gameDto) {
        this.validate(gameDto, "gameDto");
        this.validate(gameDto.getName(), "GameDto Name");
        this.validate(gameDto.getPublisherId(), "GameDto PublisherId");
        return this.gameBusinessController.create(gameDto);
    }

    public void updateName(String publisherId, String gameId, GameDto gameDto) {
        this.validate(publisherId, "publisherId");
        this.validate(gameId, "gameId");
        this.validate(gameDto, "gameDto");
        this.validate(gameDto.getName(), "new Name");
        gameDto.setId(gameId);
        gameDto.setPublisherId(publisherId);
        this.gameBusinessController.updateName(gameDto);
    }

    public GameDto read(String publisherId, String gameId) {
        this.validate(publisherId, "PublisherId");
        this.validate(gameId, "gameId");
        return this.gameBusinessController.read(publisherId, gameId);
    }

    public List<String> findByCategory(String category) {
        this.validate(category, "Category");
        if(isValidCategory(category)) {
            return this.gameBusinessController.findByCategory(category);
        } else throw new ArgumentNotValidException("Not a valid Game Rating category for search");

    }

    public boolean isValidCategory(String category) {
        for(GameRating rating: GameRating.values()) {
            if(rating.name().equals(category)) {
                return true;
            }
        } return false;
    }

}
