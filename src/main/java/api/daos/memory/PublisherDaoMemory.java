package api.daos.memory;

import api.daos.PublisherDao;
import api.entities.Publisher;

import java.util.HashMap;

public class PublisherDaoMemory extends GenericDaoMemory<Publisher> implements PublisherDao {

    public PublisherDaoMemory() {
        super(new HashMap<>());
    }

    @Override
    public String getId(Publisher publisher) {
        return publisher.getId();
    }

    @Override
    public void setId(Publisher publisher, String id) {
        publisher.setId(id);
    }
}
