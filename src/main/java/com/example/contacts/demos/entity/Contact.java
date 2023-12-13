package com.example.contacts.demos.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author zhouhaoran
 * @date 2023/11/18
 * @project contacts
 */

@Data
@TableName("contacts")
public class Contact {

    @TableId(type= IdType.AUTO)
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String imageContent; // 存储图片内容的字节数组


}
