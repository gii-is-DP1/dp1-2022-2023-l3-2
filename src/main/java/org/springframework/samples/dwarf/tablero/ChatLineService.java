package org.springframework.samples.dwarf.tablero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatLineService {

    private ChatLineRepository chatLineRepository;

    @Autowired
    public ChatLineService(ChatLineRepository chatLineRepository) {
        this.chatLineRepository = chatLineRepository;
    }

    public void saveChatLine(ChatLine chat) {
        chatLineRepository.save(chat);
    }
}
