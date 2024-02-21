package com.myblog.myblog;

import com.myblog.myblog.entity.Post;
import com.myblog.myblog.repository.PostRepository;
import com.myblog.myblog.service.PostService;
import com.myblog.myblog.service.impl.PostResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class MyblogApplicationTests {

	@Test
	void contextLoads() {
	}
	
}
