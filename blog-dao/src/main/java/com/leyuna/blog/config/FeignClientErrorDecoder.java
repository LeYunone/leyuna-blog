//package com.leyuna.blog.config;
//
//import feign.Response;
//import feign.Util;
//import feign.codec.ErrorDecoder;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//
///**
// * feign异常拦截器，当从feign抛出异常时走这个对象.
// */
//@Configuration
//@Log4j2
//public class FeignClientErrorDecoder implements ErrorDecoder {
//
//  @Override
//  public Exception decode(String methodKey, Response response) {
//    log.info("feign client response:", response);
//    String body = null;
//    try {
//      body = Util.toString(response.body().asReader());
//    } catch (IOException e) {
//      log.error("feign.IOException", e);
//    }
//    if (response.status() >= 400 && response.status() <= 500) {
//    }
//    return new RuntimeException(response.toString());
//  }
//}