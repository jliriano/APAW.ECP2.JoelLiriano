package api.daos.memory;

import api.daos.DaoFactory;
import api.daos.GameDao;
import api.daos.PublisherDao;
import api.daos.ReviewDao;

public class DaoMemoryFactory extends DaoFactory {

    private PublisherDao publisherDao;
    private GameDao gameDao;
    private ReviewDao reviewDao;

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

    @Override
    public ReviewDao getReviewDao() {
        if (reviewDao == null) {
            reviewDao = new ReviewDaoMemory();
        }
        return reviewDao;
    }

}
