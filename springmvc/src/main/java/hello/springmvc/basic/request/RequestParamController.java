package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    /**
     * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age){
        log.info("username={},age={}",username,age);
        return "ok";
    }

    /**
     * String, int 등이 단순 타입이면 @RequestParam 도 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age){
        log.info("username={}, age={}",username,age);
        return "ok";
    }

    /**
     *  파라미터 필수 여부 - required
     *  주의 !
     *  /request-param -> username 이 없으므로 예외
     *  /request-param?username= -> 빈문자열로 통과
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age){

        log.info("username={}, age={}",username,age);
        return "ok";
    }

    /**
     *  파라미터 기본 값 적용 - defaultValue
     *  빈 문자에도 적용됨
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age){

        log.info("username={}, age={}",username,age);
        return "ok";
    }

    /**
     *  파라미터를 map 으로 조회
     * @RequestParam Map, MultiValueMap
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String,Object> paramMap){
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    /**
     * @ModelAttribute 사용
     * 파라미터 명만 일치한다면 마법처럼 helloData 객체 프로퍼티에 바인딩이 된다.
     *
     * 다음 과정이 자동화 된다.
     * @RequestParam String username
     * @RequestParam int age
     * HelloData data = new HelloData()
     * data.setUsername(username)
     * data.setAge(age)
     *
     * 참고 : model.addAttribute(helloData) 코드도 자동 적용됨.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData){
        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());
        return "ok";
    }

    /**
     * @ModelAttribute 도 생략 가능
     * @RequestParam 도 생략 가능 -> 너무 생략하다보면 혼란이 발생 가능
     *
     * 스프링은 해당 생략시 다음과 같은 규칙 적용
     * String, int, Integer 같은 단순 타입은 @RequestParam
     * 나머지는 @ModelAttribute (argument resolver 로 지정해둔 타입 외 !)
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData){
        log.info("username={}, age={}",helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
