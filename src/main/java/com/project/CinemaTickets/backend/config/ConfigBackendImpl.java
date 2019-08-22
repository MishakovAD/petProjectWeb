package com.project.CinemaTickets.backend.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class ConfigBackendImpl implements ConfigBackend {

    private Properties property;
    @Override
    @PostConstruct
    public void initPropertyFile() throws IOException {
        property = new Properties();
        FileInputStream fis = new FileInputStream("src/main/java/com/project/CinemaTickets/backend/config/config.properties");
        property.load(fis);
    }

    @Override
    public String getRuCaptchaUserKey() {
        return property.getProperty("RuCaptcha.userKey");
    }

    @Override
    public boolean isRuCaptchaEnable() {
        return Boolean.parseBoolean(property.getProperty("RuCaptcha.ruCaptchaEnable"));
    }

    @Override
    public String getDbUser() {
        return property.getProperty("DAO.user");
    }

    @Override
    public String getDbPassword() {
        return property.getProperty("DAO.password");
    }

    @Override
    public String getDbUrl() {
        return property.getProperty("DAO.url");
    }
}
