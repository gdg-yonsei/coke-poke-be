package com.gdscys.cokepoke.poke.domain;


import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.member.domain.Member;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Poke")
@Getter
@SequenceGenerator(name = "POKE_SEQ_GEN", sequenceName = "POKE_SEQ", allocationSize = 1)
public class Poke {

    @Id @GeneratedValue(generator = "POKE_SEQ_GEN")
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String senderUsername;

    @ManyToOne
    @JoinColumn(name="friendship_id")
    private Friendship friendship;

    protected Poke() {}

    public Poke(Friendship friendship) {
        this.friendship = friendship;
    }

}
