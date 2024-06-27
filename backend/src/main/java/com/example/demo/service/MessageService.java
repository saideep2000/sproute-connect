package com.example.demo.service;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {

  private static final String MESSAGE_KEY_PREFIX = "message:";
  private final RedisTemplate<String, String> redisTemplate;
  private final MessageRepository messageRepository;

  @Autowired
  public MessageService(RedisTemplate<String, String> redisTemplate, MessageRepository messageRepository) {
    this.redisTemplate = redisTemplate;
    this.messageRepository = messageRepository;
  }

  public void saveMessage(String id, String content) {
    // Save to MySQL
    Message message = new Message();
    message.setId(id);
    message.setContent(content);
    messageRepository.save(message);

    // Save to Redis
    redisTemplate.opsForValue().set(MESSAGE_KEY_PREFIX + id, content);
  }

  public String getMessage(String id) {
    // Try to get from Redis
    String content = redisTemplate.opsForValue().get(MESSAGE_KEY_PREFIX + id);
    if (content != null) {
      return content;
    }

    // If not found in Redis, get from MySQL
    Optional<Message> message = messageRepository.findById(id);
    return message.map(Message::getContent).orElse(null);
  }
}
