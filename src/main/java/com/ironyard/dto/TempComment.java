package com.ironyard.dto;

import java.util.Date;

/**
 * Created by osmanidris on 3/18/17.
 */
public class TempComment {
    private Integer rating;
    private String comment;
    private Long userId;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
