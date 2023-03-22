package ${dao.packageName};

import org.apache.ibatis.annotations.Param;
import ${entity.fullName};
import java.util.List;

/**
 *  ${table.comment!""}${dao.typeName}
 * 〈功能详细描述〉
 *
 * @author xxxxx
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ${dao.name} {

    /**
    * 通过ID查询单条数据
    *
    * @param ${entity.primary.field} 主键
    * @return 实例对象
    */
    ${entity.name} queryById(${entity.primary.javaType} ${entity.primary.field});

    /**
    * 查询指定行数据
    *
    * @param ${entity.name?uncap_first} 查询条件
    * @param offset        偏移位置
    * @param limit         偏移数量
    * @return 对象列表
    */
    List<${entity.name}> queryAllByLimit(@Param("entity") ${entity.name} ${entity.name?uncap_first}, @Param("offset") int offset, @Param("limit") int limit);

    /**
    * 查询数据
    *
    * @param ${entity.name?uncap_first} 查询条件
    * @return 对象列表
    */
    List<${entity.name}> queryAll(${entity.name} ${entity.name?uncap_first});

    /**
    * 统计总行数
    *
    * @param ${entity.name?uncap_first} 查询条件
    * @return 总行数
    */
    long count(${entity.name} ${entity.name?uncap_first});

    /**
    * 新增数据
    *
    * @param ${entity.name?uncap_first}) 实例对象
    * @return 影响行数
    */
    int insert(${entity.name} ${entity.name?uncap_first});

    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<${entity.name}> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<${entity.name}> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<${entity.name}> 实例对象列表
    * @return 影响行数
    */
    int insertOrUpdateBatch(@Param("entities") List<${entity.name}> entities);

    /**
    * 修改数据
    *
    * @param ${entity.name?uncap_first}) 实例对象
    * @return 影响行数
    */
    int update(${entity.name} ${entity.name?uncap_first});

    /**
    * 通过主键删除数据
    *
    * @param ${entity.primary.field} 主键
    * @return 影响行数
    */
    int deleteById(${entity.primary.javaType} ${entity.primary.field});
}