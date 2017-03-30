package com.ironyard.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by osmanidris on 2/10/17.
 */
@Entity
public class CarComment {
    @Id @GeneratedValue
    private Long id;
    private Integer rating;
    private String comment;
    private Date updatedAt;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private CarUser postedBy;

    public CarComment(){}

    public CarComment(Integer rating, String comment, CarUser postedBy) {
        this.rating = rating;
        this. comment = comment;
        this.postedBy = postedBy;
        this.updatedAt = new Date();
    }

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

    public CarUser getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(CarUser postedBy) {
        this.postedBy = postedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
