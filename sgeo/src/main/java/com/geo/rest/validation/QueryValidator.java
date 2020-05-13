package com.geo.rest.validation;

import com.geo.rest.model.search.Query;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QueryValidator implements ConstraintValidator<GeoQuery, Query> {

    private static final String QUERY_NAME_AND_CODE_SET = "{validation.query.both.set}";

    @Override
    public boolean isValid(Query query, ConstraintValidatorContext constraintValidatorContext) {
        // either name
        if (query.getCountryCode() == null || query.getCountryCode().length() == 0
                || query.getCountryName() == null || query.getCountryName().length() == 0) {
            return true;
        }
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(QUERY_NAME_AND_CODE_SET).addConstraintViolation();
        return false;
    }
}
