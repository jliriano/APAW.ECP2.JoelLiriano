package api.daos.memory;

import api.daos.DaoFactory;
import api.daos.PublisherDao;

public class DaoMemoryFactory extends DaoFactory {

    private PublisherDao publisherDao;

    @Override
    public PublisherDao getPublisherDao() {
        if (publisherDao == null) {
            publisherDao = new PublisherDaoMemory();
        }
        return publisherDao;
    }
}
