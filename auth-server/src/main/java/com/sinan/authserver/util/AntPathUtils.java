package com.sinan.authserver.util;

import org.springframework.util.AntPathMatcher;

public class AntPathUtils {

    public static void antPathMatch() {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
//        return antPathMatcher.matchStart(registeredPath, requestPath);
        boolean matched = antPathMatcher.match("/", "/admin/user");
        boolean matched1 = antPathMatcher.match("/*", "/admin/user");
        boolean matched2 = antPathMatcher.match("/**", "/admin/user");
        boolean matched3 = antPathMatcher.match("*", "/admin/user");
        boolean matched4 = antPathMatcher.match("**", "/admin/user");
        System.out.println(matched);
        System.out.println(matched1);
        System.out.println(matched2);
        System.out.println(matched3);
        System.out.println(matched4);
//        System.out.println(matched5);
    }

    public static void main(String[] args) {
        antPathMatch();
    }
}
