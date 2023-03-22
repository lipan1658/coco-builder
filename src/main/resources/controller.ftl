package ${controller.packageName};

import ${service.interfaceFullName};
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ${table.comment!""}Controller
 * 〈功能详细描述〉
 *
 * @author xxxxx
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestController
public class ${controller.name} {

    @Autowired
    private ${service.interfaceName} ${service.interfaceName?uncap_first};


}