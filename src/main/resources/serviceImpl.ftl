package ${service.packageName};

import ${service.interfaceFullName};
import ${dao.fullName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${table.comment!""}ServiceImpl
 *
 * @author ${author}
 */
@Service
public class ${service.name} implements ${service.interfaceName} {

    @Autowired
    private ${dao.name} ${dao.name?uncap_first};



}