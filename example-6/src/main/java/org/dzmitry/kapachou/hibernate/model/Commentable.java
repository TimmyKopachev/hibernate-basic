package org.dzmitry.kapachou.hibernate.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public abstract class Commentable extends IdEntity {

    @OneToMany(
            mappedBy = "commentable",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setCommentable(this);
    }

}
