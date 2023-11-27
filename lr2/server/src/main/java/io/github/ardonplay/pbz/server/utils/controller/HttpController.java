package io.github.ardonplay.pbz.server.utils.controller;

import io.github.ardonplay.pbz.server.utils.AbstractHttpHandler;

public interface HttpController {
    String getPath();
    AbstractHttpHandler getHandler();
}
