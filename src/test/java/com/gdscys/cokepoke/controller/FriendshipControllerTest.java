package com.gdscys.cokepoke.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdscys.cokepoke.friendship.controller.FriendshipController;
import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.friendship.dto.FriendshipRequest;
import com.gdscys.cokepoke.friendship.repository.FriendshipRepository;
import com.gdscys.cokepoke.member.domain.Member;
import com.gdscys.cokepoke.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class FriendshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        memberRepository.save(new Member("test1@gmail.com", "test1", "test1", new HashSet<>()));
        memberRepository.save(new Member("test2@gmail.com", "test2", "test2", new HashSet<>()));
        memberRepository.save(new Member("test3@gmail.com", "test3", "test3", new HashSet<>()));
        friendshipRepository.save(new Friendship(memberRepository.findByUsername("test1").get(), memberRepository.findByUsername("test3").get()));
    }

    @Test
    @DisplayName("친구관계 만들기")
    @WithMockUser(username = "test1", password = "test1")
    public void create_friendship() throws Exception {
        String content = objectMapper.writeValueAsString(new FriendshipRequest("test2"));
        mockMvc.perform(post("/friendship/create")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("나 자신과는 친구 관계 못함")
    @WithMockUser(username = "test1", password = "test1")
    public void create_self_friendship() throws Exception {
        String content = objectMapper.writeValueAsString(new FriendshipRequest("test1"));
        mockMvc.perform(post("/friendship/create")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("이미 친구 신청했으면 다시 하지는 못함")
    @WithMockUser(username = "test1", password = "test1")
    public void reattempt_friendship() throws Exception {
        String content = objectMapper.writeValueAsString(new FriendshipRequest("test3"));
        mockMvc.perform(post("/friendship/create")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }










}
