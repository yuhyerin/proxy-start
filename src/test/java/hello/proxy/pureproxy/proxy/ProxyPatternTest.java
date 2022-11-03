package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import hello.proxy.pureproxy.proxy.code.Subject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {
    @Test
    void noProxyTest(){
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest(){
        Subject realSubject = new RealSubject(); // 인터페이스 realSubject = new 구현체();
        Subject cacheProxy = new CacheProxy(realSubject); // 인터페이스 cacheProxy = new 프록시(구현체);
        // -> 프록시 내부에 원래객체(target)의 참조를 지니고 있어야 하기 때문에 인자로 넘긴다.
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy); // 클라이언트 생성 시 프록시를 넘긴다.
        client.execute();
        client.execute();
        client.execute();
    }
}
