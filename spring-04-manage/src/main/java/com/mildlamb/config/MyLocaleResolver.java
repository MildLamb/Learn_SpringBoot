package com.mildlamb.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

//自定义地区解析
public class MyLocaleResolver implements LocaleResolver {
    //解析请求
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // 获取语言请求参数
        String language = request.getParameter("l");

        Locale locale = Locale.getDefault();  //如果没有使用默认的

        //如果请求的链接 携带了国际化参数
        if (StringUtils.hasLength(language)){
            //zh_CN
            String[] s = language.split("_");
            //国家 地区
            locale = new Locale(s[0],s[1]);
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
