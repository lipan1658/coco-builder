package ${service.packageName};

import ${service.interfaceFullName};
import ${dao.fullName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${table.comment!""}ServiceImpl
 * 〈功能详细描述〉
 *
 * @author xxxxx
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class ${service.name} implements ${service.interfaceName} {

    @Autowired
    private ${dao.name} ${dao.name?uncap_first};



}