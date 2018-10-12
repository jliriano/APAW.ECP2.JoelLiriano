package api.apicontrollers;

import api.businesscontrollers.GameBusinessController;
import api.dtos.GameDto;

public class GameApiController extends BasicApiController {

    public static final String GAMES = "/games";
    public static final String ID_ID = "/{id}";
    public static final String NAME = "/name";

    private GameBusinessController gameBusinessController = new GameBusinessController();

    public String create(GameDto gameDto) {
        this.validate(gameDto, "gameDto");
        this.validate(gameDto.getName(), "GameDto Name");
        this.validate(gameDto.getPublisherId(), "GameDto PublisherId");
        return this.gameBusinessController.create(gameDto);
    }

    public String update(String publisherId, String gameId, GameDto gameDto) {
        this.validate(publisherId, "publisherId");
        this.validate(gameId, "gameId");
        this.validate(gameDto, "gameDto");
        gameDto.setId(gameId);
        gameDto.setPublisherId(publisherId);
        return this.gameBusinessController.update(gameDto);
    }

}
