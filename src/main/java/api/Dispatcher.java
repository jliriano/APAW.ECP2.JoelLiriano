package api;

import api.apiControllers.PublisherApiController;
import api.dtos.PublisherDto;
import api.exceptions.ArgumentNotValidException;
import api.exceptions.RequestInvalidException;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import org.apache.logging.log4j.LogManager;

public class Dispatcher {

    private static final String REQUEST_ERROR = "request error: ";
    private static final String METHOD_ERROR = "method error: ";
    private PublisherApiController publisherApiController = new PublisherApiController();

    public void submit(HttpRequest request, HttpResponse response) {
        String errorMessage = "{'error':'%S'}";
        try {
            switch (request.getMethod()) {
                case POST:
                    this.doPost(request, response);
                    break;
                case GET:
                    throw new RequestInvalidException(REQUEST_ERROR + request.getMethod() + ' ' + request.getPath());
                case PUT:
                    throw new RequestInvalidException(REQUEST_ERROR + request.getMethod() + ' ' + request.getPath());
                case PATCH:
                    throw new RequestInvalidException(REQUEST_ERROR + request.getMethod() + ' ' + request.getPath());
                case DELETE:
                    throw new RequestInvalidException(REQUEST_ERROR + request.getMethod() + ' ' + request.getPath());
                default:
                    throw new RequestInvalidException(METHOD_ERROR + request.getMethod());
            }
        } catch (ArgumentNotValidException | RequestInvalidException exception) {
            response.setBody(String.format(errorMessage, exception.getMessage()));
            response.setStatus(HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {  // Unexpected
            LogManager.getLogger().error(exception.getStackTrace());
            response.setBody(String.format(errorMessage, exception));
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void doPost(HttpRequest request, HttpResponse response) {
        if (request.isEqualsPath(PublisherApiController.PUBLISHERS)) {
            response.setBody(this.publisherApiController.create((PublisherDto) request.getBody()));
        } else {
            throw new RequestInvalidException("method error: " + request.getMethod());
        }
    }

}
