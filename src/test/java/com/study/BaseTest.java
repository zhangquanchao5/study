package com.study;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Created by aiko on 15-1-8.
 */
@ContextConfiguration("classpath:spring-service.xml")
public abstract class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {
}
