package com.gdscys.cokepoke.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.friendship.dto.FriendshipRequest;
import com.gdscys.cokepoke.friendship.repository.FriendshipRepository;
import com.gdscys.cokepoke.friendship.service.FriendshipService;
import com.gdscys.cokepoke.member.domain.Member;
import com.gdscys.cokepoke.member.repository.MemberRepository;
import com.gdscys.cokepoke.poke.dto.PokeRequest;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class PokeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        memberRepository.save(new Member("test1@gmail.com", "test1", "test1", new HashSet<>()));
        memberRepository.save(new Member("test2@gmail.com", "test2", "test2", new HashSet<>()));
        memberRepository.save(new Member("test3@gmail.com", "test3", "test3", new HashSet<>()));

        //test1 & test3 are friends
        friendshipService.createFriendship("test1", "test3");
        friendshipService.createFriendship("test3", "test1");
    }

    @Test
    @DisplayName("포크하기")
    @WithMockUser(username = "test1", password = "test1")
    public void try_poke_friend() throws Exception {
        String content = objectMapper.writeValueAsString(new PokeRequest("test3"));
        mockMvc.perform(post("/poke/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("친구 아닌 사람 포크하기")
    @WithMockUser(username = "test1", password = "test1")
    public void try_poke_stranger() throws Exception {
        String content = objectMapper.writeValueAsString(new PokeRequest("test2"));
        mockMvc.perform(post("/poke/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("24시간 이내에는 한번만 포크 가능")
    @WithMockUser(username = "test1", password = "test1")
    public void try_poke_twice_in_a_day() throws Exception {
        String content = objectMapper.writeValueAsString(new PokeRequest("test3"));
        mockMvc.perform(post("/poke/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
        mockMvc.perform(post("/poke/")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }


}
