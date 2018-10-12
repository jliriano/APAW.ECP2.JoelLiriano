package api.apicontrollers;

import api.businesscontrollers.GameBusinessController;
import api.dtos.GameDto;

public class GameApiController extends BasicApiController {

    private static final String GAMES = "/games";

    private GameBusinessController gameBusinessController = new GameBusinessController();

    public String create(GameDto gameDto) {
        this.validate(gameDto, "gameDto");
        this.validate(gameDto.getName(), "GameDto Name");
        this.validate(gameDto.getPublisherId(), "GameDto PublisherId");
        return this.gameBusinessController.create(gameDto);
    }

}
