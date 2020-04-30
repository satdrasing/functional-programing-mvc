package com.satendra.functionalprogramingmvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.*;
import static org.springframework.web.servlet.function.RouterFunctions.route;


@Configuration
public class EmployeeRouterFunction {

    @Bean
    public RouterFunction<ServerResponse> routes(EmployeeHandler employeeHandler) {
        return route(GET("/employees"), employeeHandler::all)
                .andRoute(GET("/employees/{id}"), employeeHandler::get)
                .andRoute(POST("/employees"), employeeHandler::create)
                .andRoute(PUT("/employees/{id}"), employeeHandler::update)
                .andRoute(DELETE("/employees/{id}"), employeeHandler::delete);
    }
}
