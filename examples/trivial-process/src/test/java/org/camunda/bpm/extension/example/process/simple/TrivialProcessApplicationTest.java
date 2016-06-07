package org.camunda.bpm.extension.example.process.simple;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.extension.reactor.plugin.ReactorProcessEnginePlugin;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore("strange error: Absent Code attribute in method that is not native or abstract in class file javax/persistence/Persistence")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TrivialProcessApplication.class)
@Transactional
public class TrivialProcessApplicationTest {

  @Autowired
  private ProcessEngine processEngine;

  @Autowired
  private ReactorProcessEnginePlugin plugin;

  @Test
  public void contains_plugin() throws Exception {
    assertThat(((ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration()).getProcessEnginePlugins()).contains(plugin);
  }
}
