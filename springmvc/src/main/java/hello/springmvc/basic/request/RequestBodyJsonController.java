package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper();

    /*
        HttpServletRequest 를 사용해서 HTTP 메시지 바디에서 데이터를 읽어와서, 문자로 변환
        -> 문자로된 JSON 데이터를 Jackson 라이브러리인 objectMapper 를 사용해서 자바 객체로 변환
     */
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}",messageBody);
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}",data.getUsername(),data.getAge());
        response.getWriter().write("ok");
    }

    /*
        @RequestBody 를 이용해서 HTTP 메시지 바디 데이터를 꺼내고 messageBody 에 저장.
        -> 문자로된 JSON 데이터를 objectMapper 를 통해서 자바 객체로 변환

        참고 : @RequestBody 도 HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

        @ResponseBody
        - 모든 메서드에 @ResponseBody 적용
        - 메시지 바디 정보 직접 반환 (view 조회 x)
        - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException{
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={},age={}",data.getUsername(),data.getAge());
        return "ok";
    }

    /*
        @RequestBody 생략 불가능 (@ModelAttribute 가 적용되버림 ...)
        HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용 (content-type: application/json)

        주의 ! : content-type 이 application/json 여야 JSON 을 처리할 수 있는 HTTP 메시지 컨버터가 실행된다.

        @ResponseBody
        - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용
        - Accept : application/json (클라가 선호하는 데이터 형식) 해줘야함.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public HelloData requestBodyJsonV3(@RequestBody HelloData data){
        log.info("username={}, age={}",data.getUsername(),data.getAge());
        return data;
    }

    /*
        위의 경우를 HttpEntity 로 적용
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity){
        HelloData data = httpEntity.getBody();
        log.info("username={},age={}",data.getUsername(),data.getAge());
        return "ok";
    }
}
