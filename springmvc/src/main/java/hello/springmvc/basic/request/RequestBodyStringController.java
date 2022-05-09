package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    /*
        HTTP 메시지 바디의 데이터를 InputStream 을 사용해서 직접 읽을 수 있다.
     */
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}",messageBody);
        response.getWriter().write("ok");
    }

    /*
        스프링 MVC 는 다음 파라미터를 지원함.
        InputStream(Reader) : HTTP 요청 메시지 바디의 내용을 직접 조회
        OutputStream(Writer) : HTTP 응답 메시지 바디에 직접 결과 출력
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException{
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}",messageBody);
        responseWriter.write("ok");
    }

    /*
        HttpEntity : HTTP header, body 정보를 편리하게 조회
        요청
        - 메시지 바디 정보를 직접 조회 (@RequestParam x , @ModelAttribute x)
        - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
        응답
        - 메시지 바디 정보 직접 반환 (view 조회 x)
        - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

        HttpEntity 를 상속받은 객체
        - RequestEntity -> HttpMethod, url 정보 추가, 요청에서 사용
        - ResponseEntity -> HTTP 상태 코드 설정 가능, 응답에서 사용

        참고 : 스프링 MVC 내부에서 HTTP 메시지 바디를 읽어서 문자나 객체를 변환해서 전달하는데
        이때 메시지 컨버터(HttpMessageConverter) 라는 기능을 사용한다.

     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity){
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}",messageBody);
        return new HttpEntity<>("ok");
    }

    /*
        @RequestBody
        - 메시지 바디 정보를 직접 조회 (@RequestParam x , @ModelAttribute x)
        - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

        @ResponseBody
        - 메시지 바디 정보 직접 반환 (view 조회 x)
        - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody){
        log.info("messageBody={}",messageBody);
        return "ok";
    }
}
