/**
 *    Copyright 2015-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.spring.boot.test.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.BootstrapWith;
import org.springframework.transaction.annotation.Transactional;

/**
 * Annotation that can be used in combination with {@code @RunWith(SpringRunner.class)}
 * for a typical mybatis test. Can be used when a test focuses <strong>only</strong> on
 * mybatis-based components.
 * <p>
 * Using this annotation will disable full auto-configuration and instead apply only
 * configuration relevant to mybatis tests.
 * <p>
 * By default, tests annotated with {@code @JdbcTest} will use an embedded in-memory
 * database (replacing any explicit or usually auto-configured DataSource). The
 * {@link AutoConfigureTestDatabase @AutoConfigureTestDatabase} annotation can be used to
 * override these settings.
 * <p>
 * If you are looking to load your full application configuration, but use an embedded
 * database, you should consider {@link SpringBootTest @SpringBootTest} combined with
 * {@link AutoConfigureTestDatabase @AutoConfigureTestDatabase} rather than this
 * annotation.
 *
 * @author wonwoo
 * @see AutoConfigureMybatis
 * @since 1.2.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@TypeExcludeFilters(MybatisTypeExcludeFilter.class)
@Transactional
@AutoConfigureCache
@AutoConfigureMybatis
@AutoConfigureTestDatabase
@ImportAutoConfiguration
public @interface MybatisTest {

  /**
   * Determines if default filtering should be used with
   * {@link SpringBootApplication @SpringBootApplication}. By default no beans are
   * included.
   *
   * @return if default filters should be used
   * @see #includeFilters()
   * @see #excludeFilters()
   */
  boolean useDefaultFilters() default true;

  /**
   * A set of include filters which can be used to add otherwise filtered beans to the
   * application context.
   *
   * @return include filters to apply
   */
  Filter[] includeFilters() default {};

  /**
   * A set of exclude filters which can be used to filter beans that would otherwise be
   * added to the application context.
   *
   * @return exclude filters to apply
   */
  Filter[] excludeFilters() default {};

  /**
   * Auto-configuration exclusions that should be applied for this test.
   * @return auto-configuration exclusions to apply
   */
  @AliasFor(annotation = ImportAutoConfiguration.class, attribute = "exclude")
  Class<?>[] excludeAutoConfiguration() default {};

}
