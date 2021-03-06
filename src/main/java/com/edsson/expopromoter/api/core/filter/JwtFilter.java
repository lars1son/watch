package com.edsson.expopromoter.api.core.filter;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

@Component
public class JwtFilter extends GenericFilterBean {


    public JwtFilter() {

    }

    @Transactional
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;
        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpResponse.addHeader("Access-Control-Allow-Methods", "*");
        httpResponse.addHeader("Access-Control-Allow-Headers", "authorization, Accept, Content-Type, Accept-Encoding, Accept-Language, Access-Control-Request-Headers, Access-Control-Request-Method");
        httpResponse.addHeader("Access-Control-Max-Age", "3600");


        Set<String> allowedPaths = new TreeSet<>();

//        allowedPaths.add("/auth/login");
//        allowedPaths.add("/auth/registration");
//        allowedPaths.add("/auth/forgot_password");
//        allowedPaths.add("/auth/update_password");
//        allowedPaths.add("/auth/device_register");
//        allowedPaths.add("/auth/device_login");
//        allowedPaths.add("/auth/update_token");
//        allowedPaths.add("/admin/login");
//
//        if (allowedPaths.contains(httpRequest.getRequestURI())) {
            filterChain.doFilter(httpRequest, res);
            return;
//        }


//
//        String authHeader = httpRequest.getHeader("Authorization");
//
//        if (authHeader != null && !authHeader.equals("")) {
//            try {
//                if (tokenRepository.findOneByToken(authHeader) != null) {
//
//
//                    Date expirationDate = tokenRepository.findOneByToken(authHeader).getUpdatedAt();
//                    LocalDateTime ldt = LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault());
//                    ldt = ldt.plusHours(2);
//                    if (ldt.isBefore(LocalDateTime.now())) {
//                        logger.info(new TokenNotExistException());
//                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//                    } else {
//
//                        Claims c = jwtUtil.parseToken(authHeader);
//                        httpRequest.setAttribute("claims", c);
//                        LinkedHashMap user = c.get("user", LinkedHashMap.class);
//                        httpRequest.setAttribute("user_roles", user.get("roles"));
//
//                        Apk u = userService.findOneById(Long.valueOf((Integer) user.get("id")));
//                        httpRequest.setAttribute("user", u);
//
//                        if (httpRequest.getRequestURI().contains("admin/")) {
//                            if (!u.getRole().getRole().equals("ROLE_ADMIN")) {
//                                res.reset();
//                                httpResponse.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
//                            }
//
//                        }
//
//                        filterChain.doFilter(httpRequest, res);
//                    }
//                } else {
//                    logger.info(new TokenNotExistException());
//                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//
//                    throw new TokenNotExistException();
//                }
//
//            } catch (JwtException e) {
//                res.reset();
//                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            } catch (TokenNotExistException e) {
//                e.printStackTrace();
//            }
//        } else {
//        res.reset();
//        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
////        }


    }


}
