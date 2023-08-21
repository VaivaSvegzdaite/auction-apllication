package com.auctionapp.model.login;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageResponseDTOTest {

    @Test
    void givenMessage_whenCreatingMessageResponse_ShouldMessageBeSet() {
        String messageText = "Hello, world!";
        MessageResponseDTO response = new MessageResponseDTO(messageText);
        assertEquals(messageText, response.getMessage());
    }
}