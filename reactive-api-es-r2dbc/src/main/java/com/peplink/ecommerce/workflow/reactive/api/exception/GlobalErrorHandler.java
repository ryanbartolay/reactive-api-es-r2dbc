package com.peplink.ecommerce.workflow.reactive.api.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peplink.ecommerce.workflow.reactive.api.model.CommonErrorResponse;
import com.peplink.ecommerce.workflow.reactive.api.model.Message;
import com.peplink.ecommerce.workflow.reactive.api.util.JsonUtility;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@Order(-2)
@Slf4j
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    private ObjectMapper objectMapper;

    public GlobalErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {

        DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();
        if (throwable instanceof IOException) {
            serverWebExchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            DataBuffer dataBuffer = null;
            try {
                dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(new Exception("Custom message")));
            } catch (JsonProcessingException e) {
                dataBuffer = bufferFactory.wrap("".getBytes());
            }
            serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
        }

        return handleAllExceptions(serverWebExchange, throwable);
    }

    public final Mono<Void> handleAllExceptions(ServerWebExchange serverWebExchange, Throwable ex) {
        printErrorLogs(ex, serverWebExchange, HttpStatus.INTERNAL_SERVER_ERROR);
        String msg = HttpStatus.INTERNAL_SERVER_ERROR.toString() + ": " + ex.getMessage();
        String rootCause = ExceptionUtils.getRootCauseMessage(ex);
        return getCommonErrorResponse(serverWebExchange, msg, HttpStatus.INTERNAL_SERVER_ERROR, rootCause);
    }

    public final Mono<Void> handleObjectNotFoundException(ServerWebExchange serverWebExchange, Throwable ex) {
        String rootCause = ExceptionUtils.getRootCauseMessage(ex);
        printErrorLogs(ex, serverWebExchange, HttpStatus.NOT_FOUND);
        String msg = HttpStatus.NOT_FOUND.toString() + ": " + ex.getMessage();
        return getCommonErrorResponse(serverWebExchange, msg, HttpStatus.NOT_FOUND, rootCause);
    }

    private Mono<Void> getCommonErrorResponse(ServerWebExchange serverWebExchange, String errorMessage,
            HttpStatus status, String stacktrace) {
        List<Message> errors = Arrays.asList(new Message(errorMessage));
        CommonErrorResponse errorResponse = new CommonErrorResponse();
        errorResponse.setErrors(errors);
        errorResponse.setRootCause(stacktrace);
        DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(JsonUtility.objectToJsonString(errorResponse).getBytes());;
        serverWebExchange.getResponse().setStatusCode(status);
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    private void printErrorLogs(Throwable exception, ServerWebExchange serverWebExchange, HttpStatus status) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        String errorMessage = sw.toString();
        String details = status.toString() + " - " + request.getMethod() + " (" + request.getPath() + ")";
        log.error("-------- Error Start for URL: " + details + " -------------");
        log.error("Error Description : " + errorMessage);
        log.error("-------- Error End for URL: " + details + " -------------");
    }

}