package com.aaemu.editor.service.dto.web;

import lombok.Data;

import java.util.Map;

/**
 * @author Shannon
 */
@Data
public class Log {
    private String user;
    private Map<String, String> log;
}