package com.gdscys.cokepoke.poke.controller;

import com.gdscys.cokepoke.poke.dto.PokeRequest;
import com.gdscys.cokepoke.poke.dto.PokeResponse;
import com.gdscys.cokepoke.poke.service.PokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static com.gdscys.cokepoke.auth.SecurityUtil.getLoginUsername;
import static java.util.Arrays.stream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/poke")
public class PokeController {
    private final PokeService pokeService;

    @PostMapping("/")
    public ResponseEntity<String> poke(@RequestBody @Valid PokeRequest request) {
        String senderUsername = getLoginUsername();
        pokeService.poke(senderUsername, request.getReceiverUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body("Poked " + request.getReceiverUsername());
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<PokeResponse>> getPokes(@PathVariable String username,
                                                       @RequestParam(defaultValue = "0") int page) {
        String senderUsername = getLoginUsername();
        List<PokeResponse> responses = pokeService.getPokesByUsernames(senderUsername, username, page)
                .stream()
                .map(PokeResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{username}/{pokeId}")
    public ResponseEntity<PokeResponse> getPoke(@PathVariable String username, @PathVariable Long pokeId) {
        String senderUsername = getLoginUsername();
        PokeResponse response = PokeResponse.of(pokeService.getPokeById(pokeId));
        return ResponseEntity.ok(response);
    }
}
