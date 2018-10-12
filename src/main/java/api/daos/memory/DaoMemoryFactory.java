package api.daos.memory;

import api.daos.DaoFactory;
import api.daos.GameDao;
import api.daos.PublisherDao;

public class DaoMemoryFactory extends DaoFactory {

    private PublisherDao publisherDao;
    private GameDao gameDao;

    @Override
    public PublisherDao getPublisherDao() {
        if (publisherDao == null) {
            publisherDao = new PublisherDaoMemory();
        }
        return publisherDao;
    }

    @Override
    public GameDao getGameDao() {
        if (gameDao == null) {
            gameDao = new GameDaoMemory();
        }
        return gameDao;
    }
}
