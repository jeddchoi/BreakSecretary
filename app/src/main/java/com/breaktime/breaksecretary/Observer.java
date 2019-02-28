package com.breaktime.breaksecretary;

import com.breaktime.breaksecretary.model.User;

public interface Observer {
    public void update(User.Status_user status);
}
