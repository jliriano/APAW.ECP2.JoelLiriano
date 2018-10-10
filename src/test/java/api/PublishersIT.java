package api;

import api.apicontrollers.PublisherApiController;
import api.daos.DaoFactory;
import api.daos.memory.DaoMemoryFactory;
import api.dtos.PublisherDto;
import http.Client;
import http.HttpException;
import http.HttpRequest;
import http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PublishersIT {

    @BeforeAll
    static void before() {
        DaoFactory.setFactory(new DaoMemoryFactory());
    }

    @Test
    void testCreatePublisher() {
        this.createPublisher();
    }

    @Test
    void testPublisherInvalidRequest(){
        HttpRequest request = HttpRequest.builder().path(PublisherApiController.PUBLISHERS+"/invalid").body(null).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testCreatePublisherWithoutPublisherDto() {
        HttpRequest request = HttpRequest.builder().path(PublisherApiController.PUBLISHERS).body(null).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testCreatePublisherWithoutPublisherDtoName() {
        HttpRequest request = HttpRequest.builder().path(PublisherApiController.PUBLISHERS).body(new PublisherDto(null)).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    private String createPublisher() {
        HttpRequest request = HttpRequest.builder().path(PublisherApiController.PUBLISHERS).body(new PublisherDto("Publisher1")).post();
        return (String) new Client().submit(request).getBody();
    }
}
