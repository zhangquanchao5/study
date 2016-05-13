package com.study.common.page;

/**
 * Created by huichao on 2015/8/4.
 */
public class UserPageRequest extends BasePageRequest {

    private Integer id;

    private String username;

    private Integer start;

    private Integer type;

    private String ids;

    private String mobile;

    /**
     * Gets mobile.
     *
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets mobile.
     *
     * @param mobile the mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Gets ids.
     *
     * @return the ids
     */
    public String getIds() {
        return ids;
    }

    /**
     * Sets ids.
     *
     * @param ids the ids
     */
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public Integer getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(Integer start) {
        this.start = start;
    }
}
