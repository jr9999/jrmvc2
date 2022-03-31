package com.jr.config;

import com.jr.data.redis.model.Employee;
import com.jr.data.redis.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
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

import org.springframework.data.redis.connection.ReactiveKeyCommands;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.ReactiveStringCommands;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ComponentScan(basePackages= {"com.jr", "com.jr.data.redis"})
@EnableWebMvc
@EnableRedisRepositories(basePackages = "com.jr.data.redis.repo")
public class MyWebMvcConfigurer implements WebMvcConfigurer {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	RedisConnectionFactory factory;

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

	@Bean
	public ReactiveRedisTemplate<String, Student> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
		Jackson2JsonRedisSerializer<Student> serializer = new Jackson2JsonRedisSerializer<>(Student.class);
		RedisSerializationContext.RedisSerializationContextBuilder<String, Student> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
		RedisSerializationContext<String, Student> context = builder.value(serializer)
				.build();
		return new ReactiveRedisTemplate<>(factory, context);
	}

	@Bean
	public ReactiveKeyCommands keyCommands(final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
		return reactiveRedisConnectionFactory.getReactiveConnection()
				.keyCommands();
	}

	@Bean
	public ReactiveStringCommands stringCommands(final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
		return reactiveRedisConnectionFactory.getReactiveConnection()
				.stringCommands();
	}

	/*
	@PreDestroy
	public void cleanRedis() {
		factory.getConnection()
				.flushDb();
	}
	 */
}
