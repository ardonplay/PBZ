package io.github.ardonplay.pbz.controllers;

import io.github.ardonplay.pbz.services.AbstractHttpHandler;

public interface HttpController {
    String getPath();
    AbstractHttpHandler getHandler();
}
