package io.github.ardonplay.pbz.services;

import com.sun.net.httpserver.HttpHandler;

public interface HttpController {
    String getPath();
    HttpHandler getHandler();
}
