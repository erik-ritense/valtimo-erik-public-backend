package com.ritense.valtimo.demo

import com.ritense.valtimo.contract.security.config.HttpConfigurerConfigurationException
import com.ritense.valtimo.contract.security.config.HttpSecurityConfigurer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import javax.ws.rs.HttpMethod

class DemoSecurityConfig: HttpSecurityConfigurer {
    override fun configure(http: HttpSecurity) {
        try {
            http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/parallel").anonymous()
        } catch (e: Exception) {
            throw HttpConfigurerConfigurationException(e)
        }
    }
}