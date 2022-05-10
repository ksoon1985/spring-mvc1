package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    /*
        스프링 MVC 의 기본적인 ModelAndView 를 반환하는 경우
        - new 할 때 view 값을 넣어준다.
        - addObject 로 Model 값을 넣어준다.
     */
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1(){
        ModelAndView mv = new ModelAndView("response/hello").addObject("data", "hello!");
        return mv;
    }

    /*
        String 을 반환하는 경우
        @ResponseBody 가 있으면 -> 뷰 리졸버를 실행하지 않고, HTTP 메시지 바디에 직접 response/hello 라는 문자가 입력됨.
        @ResponseBody 가 없으면 -> 뷰 리졸버를 실행(response/hello) 되어 뷰를 찾고 렌더링 됨.
     */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model){
        model.addAttribute("data","hello!!");
        return "response/hello";
    }

    /*
        @Controller 를 사용하고 HttpServletResponse, OutputStream(Writer) 같은 HTTP 메시지 바디를 처리하는
        파라미터가 없으면 요청 URL 을 참고하여 논리 뷰 이름으로 사용
        요청 URL : /response/hello -> 실행 : templates/response/hello.html

        참고 : 이 방식은 명시성이 너무 떨어지고, 이렇게 딱 맞는 경우도 많이 없어서 사용을 거의 안 한다.
     */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model){
        model.addAttribute("data","hello!!!");
    }
}
