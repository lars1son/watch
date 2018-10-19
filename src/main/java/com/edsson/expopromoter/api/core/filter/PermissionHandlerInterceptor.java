package com.edsson.expopromoter.api.core.filter;

import com.edsson.expopromoter.api.model.Apk;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Intercept http request and validate permission annotation against user roles.
 */
public class PermissionHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
//
////        Apk currentUser = (Apk) request.getAttribute("user");
//
//        HandlerMethod method = (HandlerMethod) handler;

//        if (currentUser == null) {
//            response.sendError(HttpStatus.FORBIDDEN.value());
//            return false;
//        }

         return true;
    }


}