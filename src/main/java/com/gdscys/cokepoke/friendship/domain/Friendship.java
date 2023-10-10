package com.gdscys.cokepoke.friendship.domain;

import com.gdscys.cokepoke.member.domain.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity(name = "Friendship")
@Getter
@SequenceGenerator(name = "FRIENDS_SEQ_GEN", sequenceName = "FRIENDS_SEQ", allocationSize = 1)
public class Friendship {

    @Id @GeneratedValue(generator = "FRIENDS_SEQ_GEN")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Member requestMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Member recipientMember;

    @Column(name = "is_accepted", columnDefinition = "boolean default false")
    private boolean isAccepted;

    protected Friendship() {}

    public Friendship(Member requestMember, Member recipientMember) {
        this.requestMember = requestMember;
        this.recipientMember = recipientMember;
    }

    public void accept() {
        this.isAccepted = true;
    }

}
