package com.aaemu.editor.service;

import com.aaemu.editor.service.dto.packet.client.CELogin;

/**
 * @author Shannon
 */
public interface AuthService {

    void login(CELogin packet);
}
