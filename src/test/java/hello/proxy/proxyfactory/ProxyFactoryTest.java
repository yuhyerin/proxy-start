package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target); // 여기서 target을 인자로 받음
        proxyFactory.addAdvice(new TimeAdvice()); // Advice 등록
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy(); // proxy 생성 완료
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass()); // com.sun.proxy.$Proxy13

        proxy.save();

        // AopUtils : 프록시 팩토리를 통해 생성된 프록시만 확인가능한 유틸임
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("구체클래스만 있으면 CGLIB 사용")
    void concreteProxy(){
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target); // 여기서 target을 인자로 받음
        proxyFactory.addAdvice(new TimeAdvice()); // Advice 등록
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy(); // proxy 생성 완료
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass()); // hello.proxy.common.service.ConcreteService$$EnhancerBySpringCGLIB$$8328efc5

        proxy.call();

        // AopUtils : 프록시 팩토리를 통해 생성된 프록시만 확인가능한 유틸임
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용함.")
    void proxyTargetClass(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target); // 여기서 target을 인자로 받음
        proxyFactory.setProxyTargetClass(true); // -> 무조건 CGLIB를 기반으로 만들어라.
        proxyFactory.addAdvice(new TimeAdvice()); // Advice 등록
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy(); // proxy 생성 완료
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass()); // hello.proxy.common.service.ServiceImpl$$EnhancerBySpringCGLIB$$bf3a4aee

        proxy.save();

        // AopUtils : 프록시 팩토리를 통해 생성된 프록시만 확인가능한 유틸임
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }
}
