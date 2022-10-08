package com.mitocode.repo;

import com.mitocode.model.Menu;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMenuRepo extends IGenericRepo<Menu, Integer>{

    @Query(value= "select m.* from menu_role mr \n" +
            "inner join user_role ur on ur.id_role = mr.id_role \n" +
            "inner join menu m on m.id_menu = mr.id_menu \n" +
            "inner join user_data u on u.id_user = ur.id_user\n" +
            "where u.username = :username order by ordermenu", nativeQuery = true)
    List<Menu> getMenusByUsername(String username);
}
