package api.apicontrollers;

import api.businesscontrollers.PublisherBusinessController;
import api.dtos.PublisherDto;
import api.exceptions.ArgumentNotValidException;

public class PublisherApiController extends BasicApiController {

    public static final String PUBLISHERS = "/publishers";
    public static final String ID_ID = "/{id}";

    private PublisherBusinessController publisherBusinessController = new PublisherBusinessController();

    public String create(PublisherDto publisherDto) {
        this.validate(publisherDto, "publisherDto");
        this.validate(publisherDto.getName(), "PublisherDto Name");
        return this.publisherBusinessController.create(publisherDto);
    }

    public PublisherDto read(String id) {
        this.validate(id, "PublisherId");
        return this.publisherBusinessController.read(id);
    }

}