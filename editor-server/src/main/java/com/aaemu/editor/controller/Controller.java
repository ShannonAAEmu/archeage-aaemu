package com.aaemu.editor.controller;

import com.aaemu.editor.service.dto.web.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shannon
 */
@RestController
@Log4j2
public class Controller {

    @PostMapping("ddems/sandbox/log")
    public ResponseEntity<Void> log(Log log) {
        Controller.log.info(log);
        return ResponseEntity.ok().build();
    }
}
