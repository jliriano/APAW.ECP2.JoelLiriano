package api.businesscontrollers;

import api.daos.DaoFactory;
import api.dtos.PublisherDto;
import api.entities.Publisher;
import api.exceptions.NotFoundException;

public class PublisherBusinessController {

    public String create(PublisherDto publisherDto) {
        Publisher publisher = new Publisher(publisherDto.getName());
        DaoFactory.getFactory().getPublisherDao().save(publisher);
        return publisher.getId();
    }

    public PublisherDto read(String id) {
        Publisher publisher = DaoFactory.getFactory().getPublisherDao().read(id).orElseThrow(
                () -> new NotFoundException("Playlist (" + id + ")"));
        return new PublisherDto(publisher);
    }

}