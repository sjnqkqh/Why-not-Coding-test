package com.why_not_cote.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.yml")
@Retention(RetentionPolicy.RUNTIME)
@TestExecutionListeners(value = {TestIsolationListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public @interface IsolatedTest {

}
