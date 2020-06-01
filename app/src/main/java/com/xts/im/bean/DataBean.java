package com.xts.im.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;


//公众文章作者tab
@Entity
public class DataBean implements Serializable {
    //序列化ID
    private static final long serialVersionUID = -1L;
    /**
     * children : []
     * courseId : 13
     * id : 408
     * name : 鸿洋
     * order : 190000
     * parentChapterId : 407
     * userControlSetTop : false
     * visible : 1
     */

    private int courseId;

    private int id;
    @Id
    private String name;
    private int order;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;
    private boolean select = true;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Generated(hash = 1821523259)
    public DataBean(int courseId, int id, String name, int order,
            int parentChapterId, boolean userControlSetTop, int visible,
            boolean select, int index) {
        this.courseId = courseId;
        this.id = id;
        this.name = name;
        this.order = order;
        this.parentChapterId = parentChapterId;
        this.userControlSetTop = userControlSetTop;
        this.visible = visible;
        this.select = select;
        this.index = index;
    }

    @Generated(hash = 908697775)
    public DataBean() {
    }


    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public boolean getUserControlSetTop() {
        return this.userControlSetTop;
    }

    public boolean getSelect() {
        return this.select;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "courseId=" + courseId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", parentChapterId=" + parentChapterId +
                ", userControlSetTop=" + userControlSetTop +
                ", visible=" + visible +
                ", select=" + select +
                ", index=" + index +
                '}';
    }
}