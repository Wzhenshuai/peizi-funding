package com.icaopan.sys.dao;

import com.icaopan.sys.model.Dict;
import com.icaopan.util.page.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictMapper {

    public void insert(Dict dict);

    public List<Dict> findList(Dict dict);

    public List<Dict> findListByPage(Page page);

    public List<String> findTypeList();

    public void update(Dict dict);

    public void delete(Integer id);

}
