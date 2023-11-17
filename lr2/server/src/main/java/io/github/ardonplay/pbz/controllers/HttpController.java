package io.github.ardonplay.pbz.services;

public interface HttpController {
    String getPath();
    AbstractHttpHandler getHandler();
}
