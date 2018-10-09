package api;

import api.exceptions.RequestInvalidException;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import org.apache.logging.log4j.LogManager;

public class Dispatcher {

    private final String REQUEST_ERROR = "request error: ";
    private final String METHOD_ERROR = "method error: ";

    public void submit(HttpRequest request, HttpResponse response) {
        String error_message = "{'error':'%S'}";
        try {
            switch (request.getMethod()) {
                case POST:
                    throw new RequestInvalidException(METHOD_ERROR + request.getMethod());
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
        } catch (Exception exception) {  // Unexpected
            LogManager.getLogger().error(exception.getStackTrace());
            response.setBody(String.format(error_message, exception));
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
