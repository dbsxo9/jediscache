package com.example.jediscache;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepository userRepository;
    private final Jedis jedis;

    @GetMapping("/users/{id}/email")
    public String getUserEmail(@PathVariable Long id){
        String userEmail = "";
        try{
            var userEmailRedisKey = "users:%d:email".formatted(id);
            // 1. request to cache
            userEmail = jedis.get(userEmailRedisKey);
            if(userEmail != null){
                return userEmail;
            }
            // 2. else db
            userEmail = userRepository.findById(id).orElse(User.builder().build()).getEmail(); //orElse 없는 경우

            // 3.cache
            jedis.set(userEmailRedisKey, userEmail);
            //jedis.setex(); TTL값 주기

        }catch (Exception e){
            e.printStackTrace();
        }
        //end
        return userEmail;
    }
}
