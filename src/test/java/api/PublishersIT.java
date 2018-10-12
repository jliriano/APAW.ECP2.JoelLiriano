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

class PublishersIT extends CommonCore {

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
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/invalid").body(null).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testCreatePublisherWithoutPublisherDto() {
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS).body(null).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testCreatePublisherWithoutPublisherDtoName() {
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS).body(new PublisherDto((String)null)).post();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testGetPublisherById() {
        String id = this.createPublisher();
        HttpRequest request = getPublisher(id);
        assertEquals(HttpStatus.OK, new Client().submit(request).getStatus());
    }

    @Test
    void testGetPublisherInvalidRequest(){
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/").get();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void testGetPublisherNotFoundException(){
        HttpRequest request = HttpRequest.builder(PublisherApiController.PUBLISHERS+"/238").get();
        HttpException exception = assertThrows(HttpException.class, () -> new Client().submit(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}
