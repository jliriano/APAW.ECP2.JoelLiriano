package api.apicontrollers;

import api.businesscontrollers.GameBusinessController;
import api.dtos.GameDto;

import java.util.ArrayList;

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

    public ArrayList<String> findByCategory(String category) {
        return this.gameBusinessController.findByCategory(category);
    }

}
