package ${controller.packageName};

import ${service.interfaceFullName};
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ${table.comment!""}Controller
 *
 * @author ${author}
 */
@RestController
public class ${controller.name} {

    @Autowired
    private ${service.interfaceName} ${service.interfaceName?uncap_first};


}