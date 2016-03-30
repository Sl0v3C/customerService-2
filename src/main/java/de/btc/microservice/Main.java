package de.btc.microservice;

import de.btc.microservice.core.AddressReferenceService;
import de.btc.microservice.core.ContractService;
import de.btc.microservice.core.CustomerService;
import de.btc.microservice.rest.AddressReferenceRestService;
import de.btc.microservice.rest.ContractRestService;
import de.btc.microservice.rest.dto.AddressReferenceDto;
import de.btc.microservice.rest.dto.ContractDto;
import de.btc.microservice.rest.dto.CustomerDto;
import de.btc.microservice.ui.CustomerModel;
import de.btc.microservice.model.AddressReference;
import de.btc.microservice.model.AddressType;
import de.btc.microservice.model.Contract;
import de.btc.microservice.model.Customer;
import de.btc.microservice.rest.CustomerRestService;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jpa.JPAFraction;

public class Main {

    public static void main(String[] args) throws Exception {
        Container container = new Container();

        container.fraction(new DatasourcesFraction()
                .jdbcDriver("h2", (d) -> {
                    d.driverClassName("org.h2.Driver");
                    d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
                    d.driverModuleName("com.h2database.h2");
                })
                .dataSource("MyDS", (ds) -> {
                    ds.driverName("h2");
                    ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
                    ds.userName("sa");
                    ds.password("sa");
                })
        );

        // Prevent JPA Fraction from installing it's default datasource fraction
        container.fraction(new JPAFraction()
                .inhibitDefaultDatasource()
                .defaultDatasource("jboss/datasources/MyDS")
        );

        container.start();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addResource(CustomerRestService.class);
        deployment.addResource(ContractRestService.class);
        deployment.addResource(AddressReferenceRestService.class);
        deployment.addClass(CustomerService.class);
        deployment.addClass(ContractService.class);
        deployment.addClass(AddressReferenceService.class);
        deployment.addClass(Customer.class);
        deployment.addClass(CustomerDto.class);
        deployment.addClass(Contract.class);
        deployment.addClass(ContractDto.class);
        deployment.addClass(AddressReference.class);
        deployment.addClass(AddressReferenceDto.class);
        deployment.addClass(AddressType.class);
        deployment.addClass(CustomerModel.class);

        deployment.addAsWebResource(new ClassLoaderAsset("index.html", Main.class.getClassLoader()), "index.html");
        deployment.addAsWebResource(new ClassLoaderAsset("index.xhtml", Main.class.getClassLoader()), "index.xhtml");

        deployment.addAsWebInfResource(new ClassLoaderAsset("WEB-INF/web.xml", Main.class.getClassLoader()), "web.xml");
        deployment.addAsWebInfResource(new ClassLoaderAsset("WEB-INF/template.xhtml", Main.class.getClassLoader()), "template.xhtml");

        deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml", Main.class.getClassLoader()), "classes/META-INF/persistence.xml");
        deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/load.sql", Main.class.getClassLoader()), "classes/META-INF/load.sql");

        deployment.addAllDependencies();

        container.start().deploy(deployment);
    }
}
