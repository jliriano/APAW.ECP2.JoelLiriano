package api.apicontrollers;

import api.exceptions.ArgumentNotValidException;

public class BasicApiController {

    protected void validate(Object property, String message) {
        if (property == null) {
            throw new ArgumentNotValidException(message + " is NULL");
        }
    }

}
