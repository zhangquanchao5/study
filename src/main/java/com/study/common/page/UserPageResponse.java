package com.study.common.page;

import com.study.common.apibean.response.CommonResponse;
import com.study.common.apibean.response.UserResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huichao on 2015/8/4.
 */
public class UserPageResponse {

    private Integer count;
    private Integer page;

    private List<UserResponse> users=new ArrayList<UserResponse>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }
}
