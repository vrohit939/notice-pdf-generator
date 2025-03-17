package com.rv.noticepdfgenerator.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.Map;

@Service
public class TemplateService {

    private final TemplateEngine templateEngine;

    public TemplateService(TemplateEngine templateEngine) {
        TemplateEngine engine = new TemplateEngine();
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false); // Disable caching for dynamic templates
        engine.setTemplateResolver(resolver);
        this.templateEngine = engine;
    }

    public String generateHtmlFromTemplate(String templateHtml, Map<String, Object> data) {
        Context context = new Context();
        context.setVariables(data);
        return templateEngine.process(templateHtml, context);
    }

}
