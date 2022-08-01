package com.jr.config;

import com.jr.websession.ConferenceWebSessionHandlerMethodArgumentResolver;
import com.jr.websession.ConferenceWebSessionManager;
import io.lettuce.core.ReadFrom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
/*import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;*/

import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
*/

//import org.springframework.data.redis.connection.RedisConfiguration;
/*
import com.jr.config.RedisConfiguration;

import org.springframework.context.annotation.PropertySource;
*/

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;


//@PropertySource({"WEB-INF/config/application.properties"})
@Configuration
@ComponentScan(basePackages="com.jr")
@EnableWebMvc
@EnableRedisHttpSession
public class MyWebMvcConfigurer 
	extends AbstractSecurityWebApplicationInitializer 
	implements WebMvcConfigurer {

	private static final Log logger = LogFactory.getLog(MyWebMvcConfigurer.class);

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
		return manager;
	}

	@Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {

		logger.info("Beginning LettuceConnectionFactory configuration");

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
            .readFrom(ReadFrom.REPLICA_PREFERRED)
            .build();

		String address = "127.0.0.1";
		Integer port = 6379;
		String primaryEndpoint = address;
		String readerEndpoint = address;
		RedisConfiguration redisConfiguration = new RedisConfiguration();
		redisConfiguration.setPort(port);
		redisConfiguration.setPrimaryEndpoint(primaryEndpoint);
		redisConfiguration.setReaderEndpoint(readerEndpoint);

        RedisStaticMasterReplicaConfiguration redisStaticMasterReplicaConfiguration =
            new RedisStaticMasterReplicaConfiguration(redisConfiguration.getPrimaryEndpoint(), redisConfiguration.getPort());
        redisStaticMasterReplicaConfiguration.addNode(redisConfiguration.getReaderEndpoint(), redisConfiguration.getPort());

        return new LettuceConnectionFactory(redisStaticMasterReplicaConfiguration, clientConfig);
    }

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    public RedisMessageListenerContainer springSessionRedisMessageListenerContainer() {
        return null;
    }

	/*
	@Bean
    public JedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory();
    }
	*/

	/*
	@Bean
    public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(springTemplateEngine());
		return resolver;
	}
	 */

	@Bean
	public ViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		return bean;
	}

	/*
	@Bean
	public SpringTemplateEngine springTemplateEngine() {
	  SpringTemplateEngine engine = new SpringTemplateEngine();
	  engine.setEnableSpringELCompiler(true);
	  engine.setTemplateResolver(templateResolver());
	  return engine;
	}

	@Bean
    public SpringResourceTemplateResolver templateResolver() {
	  SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
	  resolver.setApplicationContext(this.applicationContext);
	  resolver.setPrefix("/WEB-INF/views/");
	  resolver.setSuffix(".html");
	  resolver.setTemplateMode(TemplateMode.HTML);
	  return resolver;
	}
	 */

	@Bean
	public BeanNameViewResolver beanNameViewResolver(){
		return new BeanNameViewResolver();
	}

	@Bean
	public View sample() {
		return new JstlView("/WEB-INF/views/sample.jsp");
	}

	@Bean
	public View addStudent() {
		return new JstlView("/WEB-INF/views/addStudent.jsp");
	}

	@Bean
	public View addStudentAjax() {
		return new JstlView("/WEB-INF/views/addStudentAjax.jsp");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public MultipartResolver multipartResolver(){
		return new CommonsMultipartResolver();
	}

	/*
	@Bean
	public ConferenceWebSessionManager ConferenceWebSessionManager() {
		return new ConferenceWebSessionManager();
	}
	 */

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new ConferenceWebSessionHandlerMethodArgumentResolver());
	}
	
}
