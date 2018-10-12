package api.apicontrollers;

public class GameApiController extends BasicApiController {

    private static final String GAMES = "/games";

    private GameBusinessController gameBusinessController = new GameBusinessController();

    public String create(GameDto gameDto) {
        this.validate(gameDto, "gameDto");
        this.validate(gameDto.getName(), "GameDto Name");
        this.validate(gameDto.getPublisher(), "GameDto Publisher");
        return this.gameBusinessController.create(gameDto);
    }

}
