package com.cy.store.util;

import com.cy.store.entity.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * Json格式数据进行响应
 */
public class JsonResult<E> implements Serializable {

    //状态码
    private Integer state;
    //描述信息
    private String message;
    //数据
    private E data;

    public JsonResult(Integer state) {
        this.state=state;
    }
    public JsonResult() {
    }

    public JsonResult(Integer state,E data) {
        this.state=state;
        this.data=data;


    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonResult<?> that = (JsonResult<?>) o;
        return Objects.equals(state, that.state) && Objects.equals(message, that.message) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, message, data);
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
