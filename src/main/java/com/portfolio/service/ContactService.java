package com.portfolio.service;

import com.portfolio.dto.ContactMessageDto;
import com.portfolio.entity.ContactMessage;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactService(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @Transactional
    public ContactMessage saveMessage(ContactMessageDto dto) {
        ContactMessage message = new ContactMessage();
        message.setName(dto.getName().trim());
        message.setEmail(dto.getEmail().trim());
        message.setSubject(dto.getSubject().trim());
        message.setMessage(dto.getMessage().trim());
        message.setStatus(ContactMessage.Status.UNREAD);
        return contactMessageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public List<ContactMessage> findAllMessages() {
        return contactMessageRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public long countUnreadMessages() {
        return contactMessageRepository.countByStatus(ContactMessage.Status.UNREAD);
    }

    @Transactional
    public void markAsRead(Long id) {
        ContactMessage msg = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));
        msg.setStatus(ContactMessage.Status.READ);
        contactMessageRepository.save(msg);
    }

    @Transactional
    public void deleteMessage(Long id) {
        ContactMessage msg = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));
        contactMessageRepository.delete(msg);
    }
}
