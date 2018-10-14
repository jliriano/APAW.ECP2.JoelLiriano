package api;

import api.apicontrollers.GameApiController;
import api.apicontrollers.PublisherApiController;
import api.apicontrollers.ReviewApiController;
import api.dtos.PublisherDto;
import api.dtos.GameDto;
import api.dtos.ReviewDto;
import api.exceptions.ArgumentNotValidException;
import api.exceptions.NotFoundException;
import api.exceptions.RequestInvalidException;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import sun.misc.Request;

public class Dispatcher {

    private static final String REQUEST_ERROR = "request error: ";
    private static final String METHOD_ERROR = "method error: ";
    private PublisherApiController publisherApiController = new PublisherApiController();
    private GameApiController gameApiController = new GameApiController();
    private ReviewApiController reviewApiController = new ReviewApiController();

    public void submit(HttpRequest request, HttpResponse response) {
        String errorMessage = "{'error':'%S'}";
        try {
            switch (request.getMethod()) {
                case POST:
                    this.doPost(request, response);
                    break;
                case GET:
                    this.doGet(request, response);
                    break;
                case PUT:
                    this.doPut(request);
                    break;
                case PATCH:
                    this.doPatch(request);
                    break;
                case DELETE:
                    this.doDelete(request);
                    break;
                default:
                    throw new RequestInvalidException(METHOD_ERROR + request.getMethod());
            }
        } catch (ArgumentNotValidException | RequestInvalidException exception) {
            response.setBody(String.format(errorMessage, exception.getMessage()));
            response.setStatus(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException exception){
            response.setBody(String.format(errorMessage, exception.getMessage()));
            response.setStatus(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {  // Unexpected
            LogManager.getLogger().error(exception.getStackTrace());
            response.setBody(String.format(errorMessage, exception));
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void doPost(HttpRequest request, HttpResponse response) {
        if (request.isEqualsPath(PublisherApiController.PUBLISHERS)) {
            response.setBody(this.publisherApiController.create((PublisherDto) request.getBody()));
        } else if(request.isEqualsPath(PublisherApiController.PUBLISHERS+PublisherApiController.ID_ID+
                GameApiController.GAMES)) {
            response.setBody(this.gameApiController.create((GameDto) request.getBody()));

        } else if(request.isEqualsPath(PublisherApiController.PUBLISHERS+PublisherApiController.ID_ID+
                ReviewApiController.REVIEWS)) {
            response.setBody(this.reviewApiController.create((ReviewDto) request.getBody(), request.getPath(1)));
        } else {
            throw new RequestInvalidException(METHOD_ERROR + request.getMethod());
        }
    }

    private void doGet(HttpRequest request, HttpResponse response) {
        if (request.isEqualsPath(PublisherApiController.PUBLISHERS + PublisherApiController.ID_ID)) {
            response.setBody(this.publisherApiController.read(request.getPath(1)));
        } else if(request.isEqualsPath(PublisherApiController.PUBLISHERS + PublisherApiController.ID_ID
        +GameApiController.GAMES+GameApiController.ID_ID)) {
            response.setBody(this.gameApiController.read(request.getPath(1), request.getPath(3)));
        } else if(request.isEqualsPath(PublisherApiController.PUBLISHERS + PublisherApiController.ID_ID
        + ReviewApiController.REVIEWS + ReviewApiController.ID_ID)) {
            response.setBody(this.reviewApiController.read(request.getPath(1), request.getPath(3)));
        } else {
            throw new RequestInvalidException(METHOD_ERROR + request.getMethod() + ' ' + request.getPath());
        }
    }

    private void doPatch(HttpRequest request) {
        if (request.isEqualsPath(PublisherApiController.PUBLISHERS + PublisherApiController.ID_ID
        + GameApiController.GAMES + GameApiController.ID_ID + GameApiController.NAME)) {
            this.gameApiController.updateName(request.getPath(1), request.getPath(3), (GameDto) request.getBody());
        } else {
            throw new RequestInvalidException(METHOD_ERROR + request.getMethod() + ' ' + request.getPath());
        }
    }

    private void doPut(HttpRequest request) {
        if (request.isEqualsPath(PublisherApiController.PUBLISHERS + PublisherApiController.ID_ID
        + ReviewApiController.REVIEWS + ReviewApiController.ID_ID)) {
            this.reviewApiController.updateReview(request.getPath(1), request.getPath(3), (ReviewDto) request.getBody());
        } else {
            throw new RequestInvalidException(METHOD_ERROR + request.getMethod() + ' ' + request.getPath());
        }
    }

    private void doDelete(HttpRequest request) {
        if (request.isEqualsPath(PublisherApiController.PUBLISHERS + PublisherApiController.ID_ID
        + ReviewApiController.REVIEWS + ReviewApiController.ID_ID)) {
            this.reviewApiController.delete(request.getPath(1), request.getPath(3));
        } else {
            throw new RequestInvalidException(METHOD_ERROR + request.getMethod() + ' ' + request.getPath());
        }
    }

}
