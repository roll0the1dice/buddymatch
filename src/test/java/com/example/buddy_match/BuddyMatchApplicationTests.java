package com.example.buddy_match;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.buddy_match.service.BuddyUserServiceImpl;

import jakarta.annotation.Resource;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BuddyMatchApplicationTests {

	@Resource
	private RedissonClient redissonClient;

	
	void testWatchDog() {
		RLock lock = redissonClient.getLock("yupao:precachejob:docache:lock");
		try {
			// 只有一个线程能获取到锁
			if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
				// todo 实际要执行的方法
				Thread.sleep(300000L);
				System.out.println("getLock: " + Thread.currentThread().getId());
			}
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} finally {
			// 只能释放自己的锁
			if (lock.isHeldByCurrentThread()) {
				System.out.println("unLock: " + Thread.currentThread().getId());
				lock.unlock();
			}
		}
	}

}
