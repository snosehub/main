/**
 * Copyright (c) 2020, Sergey Petrov
 */

/**
 * Required if GET is used for Query object
 */

//package com.geo.rest.converter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.geo.rest.model.search.Query;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class QueryConverter implements Converter<String, Query> {
//
//    private ObjectMapper objectMapper;
//
//    QueryConverter(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public Query convert(String s) {
//        try {
//            return objectMapper.readValue(s, Query.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException();
//        }
//    }
//}
