package com.gdscys.cokepoke.friendship.domain;

import com.gdscys.cokepoke.member.domain.Member;
import com.gdscys.cokepoke.poke.domain.Poke;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Friendship")
@Getter
@SequenceGenerator(name = "FRIENDS_SEQ_GEN", sequenceName = "FRIENDS_SEQ", allocationSize = 1)
public class Friendship {

    @Id @GeneratedValue(generator = "FRIENDS_SEQ_GEN")
    private Long id;

    @ManyToOne
    @JoinColumn(name="from_member_id")
    private Member from;

    @ManyToOne
    @JoinColumn(name="to_member_id")
    private Member to;

    @Column(name = "is_accepted", columnDefinition = "boolean default false")
    private boolean isAccepted;

    @OneToMany(mappedBy = "friendship")
    private List<Poke> pokes;

    protected Friendship() {}

    public Friendship(Member from, Member to) {
        this.from = from;
        this.to = to;
        this.isAccepted = false;
        this.pokes = new ArrayList<>();
    }

    public void accept() {
        this.isAccepted = true;
    }

    public void addPoke(Poke poke) {
        this.pokes.add(poke);
    }

}
