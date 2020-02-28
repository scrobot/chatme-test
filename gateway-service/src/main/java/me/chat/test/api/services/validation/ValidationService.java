package me.chat.test.api.services.validation;

/**
 * A contract delegating requests to validation remote service, like a proxy.
 */
public interface ValidationService {

    /**
     * Terminal operation that starting listen to the validation response
     */
    void startValidationProcess();

}
