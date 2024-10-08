package com.geo.rest.validation;

import com.geo.rest.model.search.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QueryValidator implements ConstraintValidator<GeoQuery, Query> {

    private static final String QUERY_NAME_AND_CODE_SET = "{validation.query.both.set}";

    @Override
    public boolean isValid(Query query, ConstraintValidatorContext constraintValidatorContext) {
        // either name
        if (query.getCountryCode() == null || query.getCountryCode().isEmpty()
                || query.getCountryName() == null || query.getCountryName().isEmpty()) {
            return true;
        }
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(QUERY_NAME_AND_CODE_SET).addConstraintViolation();
        return false;
    }
}
