package file.dhiren.fileuploadsystem.redis.controller;

import file.dhiren.fileuploadsystem.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;
    @GetMapping("/test")
    public Object testRedis()
    {
        Map<String,Object> user = new HashMap<>();

        user.put("name", "Dhiren");
        user.put("role", "Java Developer");
        user.put("experience", 1.5);

        redisService.save("user1",user, Duration.ofHours(1));


        return redisService.get("user1");


    }
}
