package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MappingController {
    /**
     * 기본 요청
     * 둘다 허용 /hello-basic , /hello-basic/
     * HTTP 메서드 모두 허용 GET, POST, PUT, PATCH, HEAD, DELETE
     * 매핑 URL 을 배열로 다중 설정 가능. {"/hello-basic","/hello-go"}
     * @return
     */
    @RequestMapping("/hello-basic")
    public String helloBasic(){
        log.info("helloBasic");
        return "ok";
    }

    /**
     * method 특정 HTTP 메서드 요청만 허용
     * 다른 Method 요청을 하면 HTTP 405 상태코드(Method Not Allowed) 를 반환
     */
    @RequestMapping(value = "/mapping-get-v1",method = RequestMethod.GET)
    public String mappingGetV1(){
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * 편리한 축약 애노테이션
     * @GetMapping
     * @PostMapping
     * @PutMapping ...
     * 내부 코드를 보면 @RequestMapping 과 method 를 지정해서 사용하는 것을 확인할 수 있다.
     * @return
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2(){
        log.info("mappingGetV2");
        return "ok";
    }

    /**
     * PathVariable (경로 변수) 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable String userId
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data){
        log.info("mappingPath userId={}",data);
        return "ok";
    }

    /**
     * PathVariable 다중 사용
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath2(@PathVariable String userId, @PathVariable Long orderId){
        log.info("mappingPath userId={}, orderId={}", userId,orderId);
        return "ok";
    }

    /**
     * 특정 파라미터로 조건 매핑
     * params="mode"
     * params="!mode"
     * params="mode=debug"
     * params={"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam(){
        log.info("mappingParam");
        return "ok";
    }

    /**
     *  특정 헤더로 조건 매핑
     *  headers="mode"
     *  headers="mode=debug"
     */
    @GetMapping(value = "/mapping-header",headers = "mode=debug")
    public String mappingHeader(){
        log.info("mappingHeader");
        return "ok";
    }

    /**
     *  미디어 타입 조건 매핑 (HTTP 요청 Content-Type)
     *  Content-Type : 요청하는 표현 데이터 형식 (미디어 타입)
     *
     *  consumes="application/json"
     *  consumes="text/plain"
     *  consumes="application/*"
     *  consumes={"text/plain","application/*"}
     *  consumes="*\/*
     *  consumes = MediaType.APPLICATION_JSON_VALUE
     *  미디어 타입이 맞지 않으면 HTTP 415(UnSupported Media Type)을 반환
     */
    @PostMapping(value = "/mapping-consume",consumes = "application/json")
    public String mappingConsumes(){
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     *  미디어 타입 조건 매핑 (HTTP 요청 Accept)
     *  Accept : 클라이언트가 선호하는 표현 데이터 형식 (미디어 타입)
     *
     *  produces="text/html"
     *  produces={"text/plain","application/*"}
     *  produces = MediaType.TEXT_PLAIN_VALUE
     *  produces = "text/plain;charset=UTF-8
     *  만약 맞지 않으면 HTTP 406 상태코드 (Not Acceptable)을 반환
     */
    @PostMapping(value = "/mapping-produce",produces = "text/html")
    public String mappingProduces(){
        log.info("mappingProduces");
        return "ok";
    }
}
