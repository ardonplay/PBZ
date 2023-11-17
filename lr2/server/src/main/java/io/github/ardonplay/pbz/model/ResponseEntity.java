package io.github.ardonplay.pbz.model;

import com.sun.net.httpserver.Headers;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseEntity {
    private final int status;

    private final Headers headers;

    private final Object body;
    public ResponseEntity(int status) {
        this.status = status;
        this.body = null;
        this.headers = null;
    }
    public ResponseEntity(){
        this.status = 405;
        this.body = null;
        this.headers = null;
    }
    public ResponseEntity(Object body){
        this.status = 200;
        this.body = body;
        this.headers = null;
    }

    public ResponseEntity(Headers headers){
        this.status = 200;
        this.headers = headers;
        this.body = null;
    }
}
