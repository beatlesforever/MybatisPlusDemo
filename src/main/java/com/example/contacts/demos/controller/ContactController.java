package com.example.contacts.demos.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.contacts.demos.entity.Contact;
import com.example.contacts.demos.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author zhouhaoran
 * @date 2023/11/18
 * @project contacts
 */

@RestController
@RequestMapping("/")
public class ContactController {
    @Autowired
    IContactService contactService;

    @GetMapping("")
    ModelAndView getView(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("contact");
        return mv;
    }

    @GetMapping(path = "/contact", produces = "application/json")
    List<Contact> list(){
        System.out.println(contactService.list());
        return contactService.list();
    }

    @PostMapping(path = "/contact", consumes = "application/json", produces = "application/json")
    Contact add(@RequestBody Contact contact){
        contactService.save(contact);
        return contact;
    }

    @DeleteMapping(path = "/contact/{id}")
    Boolean delete(@PathVariable Long id) {
        contactService.removeById(id);
        return true;
    }

    @PutMapping(path = "/contact/{id}", consumes = "application/json", produces = "application/json")
    Contact update(@PathVariable Long id, @RequestBody Contact contact) {
        contact.setId(id);
        contactService.updateById(contact);
        return contact;
    }




//    @GetMapping("/search")
//    public String search(@RequestParam String keyword, Model model) {
//        List<Contact> contacts = contactService.list(new QueryWrapper<Contact>()
//                .like("name", keyword)
//                .or()
//                .like("phone", keyword)
//        );
//        model.addAttribute("contacts", contacts);
//
//        return "search";
//    }

}