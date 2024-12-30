package org.example.init;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class GlobalBindingInitializer {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        String[] disallowedFields = {"class.*", "Class.*", "*.class.*", "*.Class.*"};
        binder.setDisallowedFields(disallowedFields);
    }
}
