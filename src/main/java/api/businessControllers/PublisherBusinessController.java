package api.businessControllers;

import api.daos.DaoFactory;
import api.dtos.PublisherDto;
import api.entities.Publisher;

public class PublisherBusinessController {

    public String create(PublisherDto publisherDto) {
        Publisher publisher = new Publisher(publisherDto.getName());
        DaoFactory.getFactory().getPublisherDao().save(publisher);
        return publisher.getId();
    }

}