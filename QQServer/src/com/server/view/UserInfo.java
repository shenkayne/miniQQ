package com.server.view;

import com.common.UserInfoBean;

import javax.swing.*;

/**
 * @Author Ð¡Ð¡Î÷¹Ï
 * @Date 2022 11 07 20 34
 **/
public class UserInfo extends JPanel {

    public UserInfo(JFrame frame, String title, UserInfoBean user) {
        this.setBorder(BorderFactory.createTitledBorder(title));

    }
}
