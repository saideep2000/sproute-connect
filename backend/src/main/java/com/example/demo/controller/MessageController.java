package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sproutbackend/api")
public class MessageController {

  @Autowired
  private MessageService messageService;

  @PostMapping
  public void saveMessage(@RequestBody Message message) {
    messageService.saveMessage(message.getId(), message.getContent());
  }

  @GetMapping("/{id}")
  public String getMessage(@PathVariable String id) {
    return messageService.getMessage(id);
  }

  @GetMapping("/hi")
  public String printMessage() {
    return "hi";
  }
}
