package com.example.contacts.demos.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.contacts.demos.entity.Contact;
import com.example.contacts.demos.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

/**
 * @author zhouhaoran
 * @date 2023/11/18
 * @project contacts
 */

@Controller
@RequestMapping("/")
public class ContactController {
    @Autowired
    IContactService contactService;

    // 显示通讯录列表页面
    @GetMapping()
    public String list(Model model) {
        List<Contact> contacts = contactService.list();
        model.addAttribute("contacts", contacts);
        return "list";//显示主页
    }

    // 显示添加联系人页面
    @GetMapping("/add")
    public String addForm() {
        return "add";
    }

    // 处理添加联系人请求
    @PostMapping("/add")
    public String add(@RequestParam(value = "image", required = false) MultipartFile imageFile,@ModelAttribute Contact contact) throws IOException {
        if (!imageFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
            String uploadDir = "./src/main/resources/static"; // 根目录路径

            Path uploadPath = Paths.get(uploadDir, "images"); // images 子目录
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = imageFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                String relativePath = "images/" + fileName;
                contact.setImagePath(relativePath); // 将相对文件路径保存到 Contact 对象中的 image 属性

            }
        }

        contactService.save(contact);
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Contact contact = contactService.getById(id);
        model.addAttribute("contact", contact);
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam(value = "image", required = false) MultipartFile imageFile, @ModelAttribute Contact contact) throws IOException {
        if (!imageFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
            String uploadDir = "./src/main/resources/static"; // 根目录路径

            Path uploadPath = Paths.get(uploadDir, "images"); // images 子目录
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = imageFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                String relativePath = "images/" + fileName;
                contact.setImagePath(relativePath); // 将相对文件路径保存到 Contact 对象中的 image 属性
            }
        }

        // 保存联系人信息到数据库
        contactService.updateById(contact);
        return "redirect:/";// 返回到主页
    }

    // 删除联系人
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        contactService.removeById(id);
        return "redirect:/";//返回到主页
    }

    // 按姓名或手机号码进行查询

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<Contact> contacts = contactService.list(new QueryWrapper<Contact>()
                .like("name", keyword)
                .or()
                .like("phone", keyword)
        );
        model.addAttribute("contacts", contacts);

        return "search";
    }

}