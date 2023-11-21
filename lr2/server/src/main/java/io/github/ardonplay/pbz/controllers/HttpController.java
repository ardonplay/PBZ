package io.github.ardonplay.pbz.controllers;

import io.github.ardonplay.pbz.controllers.impl.handler.AbstractHttpHandler;

public interface HttpController {
    String getPath();
    AbstractHttpHandler getHandler();
}
