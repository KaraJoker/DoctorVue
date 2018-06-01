package com.chero.client.utils;

import com.chero.client.vo.AccessTokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by hxh on 2017/12/18.
 *
 * @author hxh
 */
@Slf4j
@Component
public class CheroRequestUtil {
    /**
     * 从jwt中获取userId
     * @param request
     * @return
     */
    public static String getUserId(HttpServletRequest request) {
        Claims claims =getClaims(request);
        String userId = null;
        if(claims != null) {
            userId = (String)claims.get("userId");
        }
        return userId;
    }
    /**
     * 从jwt中获取userId
     * @param token
     * @return
     */
    public static String getUserId(String token) {
    	Claims claims;
        try {
            claims=Jwts.parser().setSigningKey("JwtChero".getBytes("UTF-8")).parseClaimsJws(token).getBody();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.info("【请求工具分析错误】");
            return null;
        }
        String userId=null;
        if(claims != null) {
            userId = (String)claims.get("userId");
        }
        return userId;
    }
    public static String getMainRoleId(HttpServletRequest request) {
        Claims claims =getClaims(request);
        String mainRoleId = null;
        if(claims != null) {
            mainRoleId = (String)claims.get("mainRoleId");
        }
        return mainRoleId;
    }
    public static String getMainRoleId(String header) {
        Claims claims =getClaims(header);
        String mainRoleId = null;
        if(claims != null) {
            mainRoleId = (String)claims.get("mainRoleId");
        }
        return mainRoleId;
    }

    public static AccessTokenDTO getAccessTokenDTO(HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        Claims claims =getClaims(request);
        if(claims != null) {
            String userId = (String)claims.get("userId");
            String mainRoleId = (String)claims.get("mainRoleId");
            accessTokenDTO.setUserId(userId);
            accessTokenDTO.setMainRoleId(mainRoleId);
        }
        return accessTokenDTO;
    }

    private static Claims getClaims(String header) {
        String token = StringUtils.substringAfter(header,"bearer ");
        if(token == null || "".equals(token)) {
            token = StringUtils.substringAfter(header,"Bearer ");
        }
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey("JwtChero".getBytes("UTF-8"))
                    .parseClaimsJws(token).getBody();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.info("【请求工具分析错误】");
            return null;
        }
        return claims;
    }
    public static Claims getClaims(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return getClaims(header);
    }
}
