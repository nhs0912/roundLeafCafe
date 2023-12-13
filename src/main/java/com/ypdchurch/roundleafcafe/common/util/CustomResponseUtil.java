package com.ypdchurch.roundleafcafe.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypdchurch.roundleafcafe.common.dto.ResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
@Slf4j
public class CustomResponseUtil {

    public static void unAuthentication(HttpServletResponse response, String message) {
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDTO<?> responseDTO = new ResponseDTO<>(HttpStatus.UNAUTHORIZED, message, null);
            String responseBody = om.writeValueAsString(responseDTO);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println(responseBody);
        } catch (IOException e) {
            log.error("서버 파싱 에러 ");
            throw new RuntimeException(e);
        }
    }
}
