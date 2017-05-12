package org.camunda.bpm.extension.cloud.broadcaster;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(BroadcasterConfiguration.class)
@Documented
@Inherited
public @interface EnableCamundaTaskBroadcast {
}
