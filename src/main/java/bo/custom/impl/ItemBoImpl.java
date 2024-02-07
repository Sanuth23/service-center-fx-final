package bo.custom.impl;

import bo.custom.ItemBo;
import dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<ItemDto> allItems() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException {
        return null;
    }
}
