package hello.proxy.app.v2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping // 스프링은 @Controller 또는 @RequestMapping 어노테이션이 있어야 스프링 컨트롤러로 인식한다.
@ResponseBody
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;

    public OrderControllerV2(OrderServiceV2 orderService){
        this.orderService = orderService;
    }

    @GetMapping("/v2/request")
    String request(@RequestParam("itemId") String itemId){
        log.info(itemId);
        orderService.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/v2/no-log")
    String noLog(){
        return "ok";
    }
}
