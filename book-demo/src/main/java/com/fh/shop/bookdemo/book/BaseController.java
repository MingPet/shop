package com.fh.shop.bookdemo.book;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    public String getRealPath(String path, HttpServletRequest request){
        return request.getServletContext().getRealPath(path);

    }
}
