package com.example.contacts.demos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.contacts.demos.entity.Contact;
import com.example.contacts.demos.mapper.ContactMapper;
import com.example.contacts.demos.service.IContactService;
import org.springframework.stereotype.Service;

/**
 * @author zhouhaoran
 * @date 2023/11/19
 * @project contacts
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements IContactService {
}