package ttzv.demo.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import ttzv.demo.ecommerce.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;
    @Value("${allowed.origins}")
    private String[] allowedOrigins;

    @Autowired
    public RestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.DELETE};

        disableHttpMethods(config, theUnsupportedActions, Product.class);
        disableHttpMethods(config, theUnsupportedActions, ProductCategory.class);
        disableHttpMethods(config, theUnsupportedActions, State.class);
        disableHttpMethods(config, theUnsupportedActions, Country.class);
        disableHttpMethods(config, theUnsupportedActions, Order.class);

        exposeIds(config); //need to create getters for entity ids for this to actually work

        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(allowedOrigins);
    }

    private void disableHttpMethods(RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions, Class theClass) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure(((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        List<Class> entityClasses = new ArrayList<>();
        for (EntityType entityType :
                entities) {
            entityClasses.add(entityType.getJavaType());
        }
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(Product.class, ProductCategory.class);
    }


}
