package com.aaemu.login.service.impl;

import com.aaemu.login.service.AuthService;
import com.aaemu.login.service.LoginService;
import com.aaemu.login.service.WorldService;
import com.aaemu.login.service.annotation.TestData;
import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAListWorld;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import com.aaemu.login.service.dto.packet.server.ACEnterWorldDenied;
import com.aaemu.login.service.dto.packet.server.ACWorldCookie;
import com.aaemu.login.service.dto.packet.server.ACWorldList;
import com.aaemu.login.service.dto.packet.server.ACWorldQueue;
import com.aaemu.login.service.entity.Character;
import com.aaemu.login.service.entity.Server;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WorldServiceImpl implements WorldService {
    private final ByteBufUtil byteBufUtil;
    private final LoginService loginService;
    private final AuthService authService;
    private final Map<Channel, Integer> cookieMap;

    private boolean isValidListWorldFlag(CAListWorld listWorld) {
        return listWorld.getFlag() == 0;   // TODO check
    }

    private boolean isValidWorldFlag(CAEnterWorld enterWorld) {
        return enterWorld.getFlag() == 0;   // TODO check
    }

    private boolean isHasQueue(int worldId) {
        return false;    // TODO check
    }

    private void sendWorldList(Channel channel) {
        ACWorldList acWorldList = new ACWorldList();
        List<Server> serverList = new ArrayList<>();
        serverList.add(getTempServer());
        acWorldList.setServerList(serverList);
        acWorldList.setCount(acWorldList.getServerList().size());
        List<Character> characterList = new ArrayList<>();
//            characterDtoList.add(getTempCharacter());
        acWorldList.setCharacterList(characterList);
        acWorldList.setChCount(acWorldList.getCharacterList().size());
        channel.writeAndFlush(acWorldList.build(byteBufUtil));
    }

    @Override
    public void requestList(CAListWorld listWorld, Channel channel) {
        if (isValidListWorldFlag(listWorld)) {
            sendWorldList(channel);
        } else {
            cookieMap.remove(channel);
            loginService.rejectLogin(channel, 14, "Invalid world list flag");
        }
    }

    @Override
    public void enterWorld(CAEnterWorld enterWorld, Channel channel) {
        if (isValidWorldFlag(enterWorld)) {
            int worldId = enterWorld.getWid();
            if (isHasQueue(worldId)) {
                ACWorldQueue acWorldQueue = new ACWorldQueue();
                acWorldQueue.setWid(worldId);
                acWorldQueue.setTurnCount(5);
                acWorldQueue.setTotalCount(8);
                channel.writeAndFlush(acWorldQueue.build(byteBufUtil));
            } else {
                int cookie = new Random().nextInt(65535);
                ACWorldCookie acWorldCookie = new ACWorldCookie();
                acWorldCookie.setCookie(cookie);
                acWorldCookie.setIp("127.0.0.1");
                acWorldCookie.setPort(1239);
                cookieMap.put(channel, cookie);
                channel.writeAndFlush(acWorldCookie.build(byteBufUtil));
            }
        } else {
            cookieMap.remove(channel);
            ACEnterWorldDenied acEnterWorldDenied = new ACEnterWorldDenied();
            acEnterWorldDenied.setReason(14);
            channel.writeAndFlush(acEnterWorldDenied.build(byteBufUtil));
        }
    }

    @Override
    public void cancelEnterWorld(CACancelEnterWorld cancelEnterWorld, Channel channel) {
        cookieMap.remove(channel);
        sendWorldList(channel);
    }

    @Override
    public void requestReconnect(CARequestReconnect requestReconnect, Channel channel) {
        if (cookieMap.containsKey(channel) && cookieMap.get(channel).longValue() == requestReconnect.getCookie()) {
            authService.requestReconnect(requestReconnect, channel);
        } else {
            cookieMap.remove(channel);
            loginService.rejectWarnedAccount(channel, 14, "Invalid cookie");
        }
    }

    @TestData
    private Server getTempServer() {
        Server server = new Server();
        server.setId(1);
        server.setName("AAEmu");
        server.setAvailable(true);
        if (server.isAvailable()) {
            server.setCon(0);
            List<Boolean> rCon = new ArrayList<>();
            rCon.add(false); // warborn
            rCon.add(false); // nuian
            rCon.add(false); // returned
            rCon.add(false); // fairy
            rCon.add(false); // elf
            rCon.add(false); // hariharan
            rCon.add(false); // ferre
            rCon.add(false); // dwarf
            rCon.add(false); // none
            server.setRCon(rCon);
        }
        return server;
    }

    @TestData
    private Character getTempCharacter() {
        Character character = new Character();
        character.setAccountId(1);
        character.setWorldId(1);
        character.setCharId(1);
        character.setName("Shannon");
        character.setCharRace(1);
        character.setCharGender(1);
        character.setGuid("1234567890123456");
        character.setV(0);
        return character;
    }
}
