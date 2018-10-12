package api.dtos;

import api.entities.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PublisherDtoTest {

    Publisher publisher;
    PublisherDto publisherDto;

    @BeforeEach
    void setUp() {
        publisher = new Publisher("PublisherName");
        publisher.setId("1");
        publisher.setWebsite("website.com");
        publisher.addGame("1");
        publisher.addGame("2");
        publisherDto = null;
    }

    @Test
    void testPublisherDtoConstructor() {
        publisherDto = new PublisherDto("PublisherName");
        publisherDto.setWebsite("Publisher.com");
        publisherDto.addGame("1");
        assertEquals("PublisherName",publisherDto.getName());
        assertEquals("Publisher.com",publisherDto.getWebsite());
        assertTrue(publisherDto.hasGame("1"));
    }

    @Test
    void testPublisherDtoPublisherConstructor() {
        publisherDto = new PublisherDto(publisher);
        assertEquals(publisher.getId(), publisherDto.getId());
        assertEquals(publisher.getName(), publisherDto.getName());
        assertEquals(publisher.getWebsite(), publisherDto.getWebsite());
        assertEquals(publisher.hasGame("1"), publisherDto.hasGame("1"));
        assertEquals(publisher.hasGame("2"), publisherDto.hasGame("2"));
    }
}
