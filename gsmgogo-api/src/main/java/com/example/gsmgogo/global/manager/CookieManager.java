package com.example.gsmgogo.global.manager;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieManager {

    @Value("${cookie-domain}")
    private String cookieDomain;

    public void addTokenCookie(HttpServletResponse response, String name, String value, Long maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(Math.toIntExact(maxAge) / 1000);
        cookie.setHttpOnly(httpOnly);
        cookie.setDomain(cookieDomain);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public String getCookieValue(HttpServletRequest request, String name) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }
}
