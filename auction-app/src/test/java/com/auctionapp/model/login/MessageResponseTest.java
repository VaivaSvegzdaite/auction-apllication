package com.auctionapp.model.login;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageResponseTest {

    @Test
    void givenMessage_whenCreatingMessageResponse_ShouldMessageBeSet() {
        String messageText = "Hello, world!";
        MessageResponse response = new MessageResponse(messageText);
        assertEquals(messageText, response.getMessage());
    }
}