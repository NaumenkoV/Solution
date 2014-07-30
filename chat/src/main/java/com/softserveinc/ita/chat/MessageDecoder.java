package com.softserveinc.ita.chat;

import com.google.gson.*;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getClass());
    @Override
    public Message decode(String jsonMessage) throws DecodeException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Message message = gson.fromJson(jsonMessage, Message.class);
        return message;

    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            // Check if incoming message is valid JSON

            JsonObject jsonobject = new JsonParser().parse(jsonMessage).getAsJsonObject().getAsJsonObject("jsonobject");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig ec) {
        logger.info("MessageDecoder -init method called");
    }

    @Override
    public void destroy() {
        logger.info("MessageDecoder - destroy method called");
    }

}
