package api;

import api.daos.DaoFactory;
import api.daos.memory.DaoMemoryFactory;
import http.Client;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewsIT extends CommonCore {

    @BeforeAll
    static void before() {
        DaoFactory.setFactory(new DaoMemoryFactory());
    }

    @Test
    void testCreateReview() {
        String publisherId = this.createPublisher();
        LogManager.getLogger().info("publisherId: "+publisherId);
        String reviewId = this.createReview(publisherId, "This is my review message",
                null, null, 0);
        assertNotNull(reviewId);
        LogManager.getLogger().info(new Client().submit(this.getPublisher(publisherId)).getBody());
    }

}
