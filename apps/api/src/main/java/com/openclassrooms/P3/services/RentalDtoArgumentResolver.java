package com.openclassrooms.P3.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerMapping;

import com.openclassrooms.P3.dtos.RentalDto;
import com.openclassrooms.P3.model.Rental;

import java.util.Map;

@Component
public class RentalDtoArgumentResolver implements HandlerMethodArgumentResolver {

    private final RentalService rentalService;
    private final ModelMapper modelMapper;

    public RentalDtoArgumentResolver(RentalService rentalService, ModelMapper modelMapper) {
        this.rentalService = rentalService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RentalPathVar.class)
                && RentalDto.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {

        RentalPathVar ann = parameter.getParameterAnnotation(RentalPathVar.class);
        String variableName = ann.value();
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        @SuppressWarnings("unchecked")
        Map<String, String> uriVars = (Map<String, String>)
                request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String idStr = uriVars.get(variableName);

        final Integer id;
        try {
            id = Integer.valueOf(idStr);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid '%s' (%s) — expected integer".formatted(variableName, idStr));
        }

        return rentalService.getById(id);
    }
}
