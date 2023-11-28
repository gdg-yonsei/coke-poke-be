package com.inshining.poke.domain.entity.follow;

import com.inshining.poke.domain.entity.BaseTimeEntity;
import com.inshining.poke.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "follows")
public class Follow extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="followerId")
    private User followerUser;

    @ManyToOne
    @JoinColumn(name="followingId")
    private User followingUser;

    @Builder
    public Follow(User follower, User following){
        this.followerUser = follower;
        this.followingUser = following;
    }
}
